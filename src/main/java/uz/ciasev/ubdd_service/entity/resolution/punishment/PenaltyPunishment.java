package uz.ciasev.ubdd_service.entity.resolution.punishment;

import lombok.*;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.settings.BankAccount;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "penalty_punishment")
@NoArgsConstructor
@AllArgsConstructor
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class PenaltyPunishment extends AbstractPunishmentDetail implements PunishmentDetail, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "penalty_punishment_id_seq")
    @SequenceGenerator(name = "penalty_punishment_id_seq", sequenceName = "penalty_punishment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Setter
    private Long amount;

    @Getter
    @Setter
    @Column(name = "discount_for_date")
    private LocalDate discount70ForDate;

    @Getter
    @Setter
    @Column(name = "discount_amount")
    private Long discount70Amount;

    @Getter
    @Setter
    @Column(name = "discount50_for_date")
    private LocalDate discount50ForDate;

    @Getter
    @Setter
    @Column(name = "discount50_amount")
    private Long discount50Amount;

    @Getter
    @Setter
    @Column(name = "is_discount")
    private Boolean isDiscount70 = false;

    @Getter
    @Setter
    private Boolean isDiscount50 = false;

    @Getter
    @Setter
    private Long paidAmount = 0L;

    @Getter
    @Setter
    private LocalDateTime lastPayTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BankAccount account;

    @OneToMany(mappedBy = "penaltyPunishment", fetch = FetchType.LAZY)
    private List<Invoice> invoices;

    public Long getAccountId() {
        if (account == null) return null;
        return account.getId();
    }

    public void setDiscount(DiscountVersion discountVersion) {
        isDiscount50 = discountVersion.isDiscount50();
        isDiscount70 = discountVersion.isDiscount70();
    }

    public DiscountVersion getDiscount() {
        if (isDiscount50) return DiscountVersion.V2;
        if (isDiscount70) return DiscountVersion.V1;
        return DiscountVersion.NO;
    }

    public Long getCurrentAmount() {
        return getAmountForDate(LocalDate.now());
    }

    public Long getAmountForDate(LocalDate date) {
        if (!isAnyDiscount()) {
            return this.amount;
        }

        if (isDiscount50Alive(date)) {
            return discount50Amount;
        }

        if (isDiscount70Alive(date)) {
            return discount70Amount;
        }

        return amount;
    }

    public Integer getDiscountPercent() {
        if (!isAnyDiscount()) {
            return 0;
        }

        if (isDiscount50Alive(LocalDate.now())) {
            return 50;
        }

        if (isDiscount70Alive(LocalDate.now())) {
            return 30;
        }

        return 0;
    }

    public Integer getCurrentDiscountDaysLeft() {
        if (!isAnyDiscount()) {
            return null;
        }

        LocalDate forDate = getCurrentDiscountForDate();


        if (forDate.isBefore(LocalDate.now())) {
            return 0;
        }

        Period discountPeriodLeft = Period.between(LocalDate.now(), forDate);
        return discountPeriodLeft.getDays();
    }

    public boolean isAnyDiscount() {
        return isDiscount50 || isDiscount70;
    }

    public boolean isAnyDiscountAlive() {
        return isAnyDiscountAlive(LocalDate.now());
    }

    public boolean isAnyDiscountAlive(LocalDate date) {
        if (!isAnyDiscount()) {
            return false;
        }

        return isDiscount70Alive(date) || isDiscount50Alive(date);
    }

    public LocalDate getCurrentDiscountForDate() {
        if (!isAnyDiscount()) {
            return null;
        }

        if (isDiscount50Alive(LocalDate.now())) {
            return discount50ForDate;
        }

        if (isDiscount70Alive(LocalDate.now())) {
            return discount70ForDate;
        }

        return null;
    }

    public Long getCurrentDiscountAmount() {
        if (!isAnyDiscount()) {
            return null;
        }

        if (isDiscount50Alive(LocalDate.now())) {
            return discount50Amount;
        }

        if (isDiscount70Alive(LocalDate.now())) {
            return discount70Amount;
        }

        return null;
    }

    public LocalDate getFirstDiscountForDate() {
        if (!isAnyDiscount()) {
            return null;
        }

        if (isDiscount50) {
            return discount50ForDate;
        }

        if (isDiscount70) {
            return discount70ForDate;
        }

        return null;
    }

    public Long getFirstDiscountAmount() {
        if (!isAnyDiscount()) {
            return null;
        }

        if (isDiscount50) {
            return discount50Amount;
        }

        if (isDiscount70) {
            return discount70Amount;
        }

        return null;
    }

    public boolean isDiscount50Alive(LocalDate date) {
        if (!isDiscount50) {
            return false;
        }

        return !date.isAfter(this.discount50ForDate);
    }

    public boolean isDiscount70Alive(LocalDate date) {
        if (!isDiscount70) {
            return false;
        }

        return !date.isAfter(this.discount70ForDate);
    }

    @Override
    public LocalDate getExecutionDate() {
        return Optional.ofNullable(lastPayTime).map(LocalDateTime::toLocalDate).orElse(null);
    }

    @Override
    public AdmStatusAlias calculateStatusAlias() {
        int compareResult = paidAmount.compareTo(amount);
        if (compareResult >= 0) {
//        if (isExecuted()) {
            return AdmStatusAlias.EXECUTED;
        }

        if (paidAmount != 0L) {
            return AdmStatusAlias.IN_EXECUTION_PROCESS;
        }

        return AdmStatusAlias.DECISION_MADE;
    }

    @AllArgsConstructor
    @Getter
    public static enum DiscountVersion {
        NO(false, false, 100), // без скидки
        V1(false, true, 70), // скидка 70% 15 дней
        V2(true, true, 50); // скидка 50% 15 дней, 30% следующие 15 дней

        private final boolean isDiscount50;
        private final boolean isDiscount70;
        private final int firstAmountPercent;


        public double getFirstAmountPercent() {
            return firstAmountPercent;
        }
    }
}
