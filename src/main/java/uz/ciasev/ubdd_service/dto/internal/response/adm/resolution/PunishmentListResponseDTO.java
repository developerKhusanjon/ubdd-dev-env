package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.adm.InvoiceResponseDTO;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.punishment.*;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class PunishmentListResponseDTO {

    private final Long id;
    private final Long decisionId;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final boolean isActive;
    private final boolean isMain;
    private final Long punishmentTypeId;
    private final LocalDate executionDate;
    private final Long executionUserId;
    private final String executionOrganName;
    private final Long statusId;
    private final String amountText;
    private final String executionUri;

    private final PenaltyResponseDTO penalty;
    private final ArrestResponseDTO arrest;
    private final ConfiscationResponseDTO confiscation;
    private final CommunityWorkResponseDTO communityWork;
    private final DeportationResponseDTO deportation;
    private final LicenseRevocationResponseDTO licenseRevocation;
    private final MedicalResponseDTO medical;
    private final WithdrawalResponseDTO withdrawal;

    public PunishmentListResponseDTO(Punishment punishment, @Nullable Invoice invoice) {
        this.id = punishment.getId();
        this.decisionId = punishment.getDecisionId();
        this.createdTime = punishment.getCreatedTime();
        this.editedTime = punishment.getEditedTime();
        this.isActive = punishment.isActive();
        this.isMain = punishment.isMain();
        this.punishmentTypeId = punishment.getPunishmentTypeId();
        this.executionDate = punishment.getExecutionDate();
        this.executionUserId = punishment.getExecutionUserId();
        this.executionOrganName = punishment.getExecutionOrganName();
        this.statusId = punishment.getStatus().getId();
        this.amountText = punishment.getAmountText();
        this.executionUri = punishment.getExecutionUri();

        this.penalty = Optional.ofNullable(punishment.getPenalty()).map(p -> new PenaltyResponseDTO(p, invoice)).orElse(null);
        this.withdrawal = Optional.ofNullable(punishment.getWithdrawal()).map(WithdrawalResponseDTO::new).orElse(null);
        this.confiscation = Optional.ofNullable(punishment.getConfiscation()).map(ConfiscationResponseDTO::new).orElse(null);
        this.licenseRevocation = Optional.ofNullable(punishment.getLicenseRevocation()).map(LicenseRevocationResponseDTO::new).orElse(null);
        this.arrest = Optional.ofNullable(punishment.getArrest()).map(ArrestResponseDTO::new).orElse(null);
        this.deportation = Optional.ofNullable(punishment.getDeportation()).map(DeportationResponseDTO::new).orElse(null);
        this.communityWork = Optional.ofNullable(punishment.getCommunityWork()).map(CommunityWorkResponseDTO::new).orElse(null);
        this.medical = Optional.ofNullable(punishment.getMedical()).map(MedicalResponseDTO::new).orElse(null);

    }


    @Data
    public static class PenaltyResponseDTO {

        private final Long id;
        private final Long amount;
        private final Long paidAmount;
        @Deprecated private final Boolean isDiscount;
        private final Boolean isDiscount50;
        private final Boolean isDiscount70;
        private final LocalDateTime lastPayTime;
        private final String invoiceSerial;
        @Deprecated private final LocalDate discountForDate;
        @Deprecated private final Long discountAmount;
        private final LocalDate discount50ForDate;
        private final Long discount50Amount;
        private final LocalDate discount70ForDate;
        private final Long discount70Amount;
        private final InvoiceResponseDTO invoice;

        public PenaltyResponseDTO(PenaltyPunishment punishmentDetail, Invoice invoice) {

            this.id = punishmentDetail.getId();
            this.amount = punishmentDetail.getAmount();
            this.paidAmount = punishmentDetail.getPaidAmount();
            this.lastPayTime = punishmentDetail.getLastPayTime();

            this.isDiscount = punishmentDetail.getIsDiscount70();
            this.discountForDate = punishmentDetail.getDiscount70ForDate();
            this.discountAmount = punishmentDetail.getDiscount70Amount();

            this.isDiscount70 = punishmentDetail.getIsDiscount70();
            this.discount70ForDate = punishmentDetail.getDiscount70ForDate();
            this.discount70Amount = punishmentDetail.getDiscount70Amount();

            this.isDiscount50 = punishmentDetail.getIsDiscount50();
            this.discount50ForDate = punishmentDetail.getDiscount50ForDate();
            this.discount50Amount = punishmentDetail.getDiscount50Amount();

            if (invoice != null) {
                this.invoiceSerial = invoice.getInvoiceSerial();
                this.invoice = new InvoiceResponseDTO(invoice);
            } else {
                this.invoiceSerial = null;
                this.invoice = null;
            }
        }
    }

    @Data
    public static class ArrestResponseDTO {

        private final Integer days;
        private final LocalDate inDate;
        private final String inState;
        private final LocalDate outDate;
        private final String outState;
        private final Long arrestPlaceTypeId;

        public ArrestResponseDTO(ArrestPunishment punishmentDetail) {
            this.days = punishmentDetail.getDays();
            this.inDate = punishmentDetail.getInDate();
            this.inState = punishmentDetail.getInState();
            this.outDate = punishmentDetail.getOutDate();
            this.outState = punishmentDetail.getOutState();
            this.arrestPlaceTypeId = punishmentDetail.getArrestPlaceTypeId();
        }
    }

    @Data
    public static class CommunityWorkResponseDTO {

        private final Integer hours;

        public CommunityWorkResponseDTO(CommunityWorkPunishment punishmentDetail) {
            this.hours = punishmentDetail.getHours();
        }
    }

    @Data
    public static class ConfiscationResponseDTO {

        private final Long amount;

        public ConfiscationResponseDTO(ConfiscationPunishment punishmentDetail) {
            this.amount = punishmentDetail.getAmount();
        }
    }

    @Data
    public static class DeportationResponseDTO {


        private final Integer years;
        private final Integer months;
        private final Integer days;
        private final LocalDate deportationDate;
        private final Long deportationTerminalId;

        public DeportationResponseDTO(DeportationPunishment punishmentDetail) {
            this.years = punishmentDetail.getYears();
            this.months = punishmentDetail.getMonths();
            this.days = punishmentDetail.getDays();
            this.deportationDate = punishmentDetail.getDeportationDate();
            this.deportationTerminalId = punishmentDetail.getDeportationTerminalId();
        }
    }

    @Data
    public static class LicenseRevocationResponseDTO {

        private final Integer years;
        private final Integer months;
        private final Integer days;

        public LicenseRevocationResponseDTO(LicenseRevocationPunishment punishmentDetail) {
            this.years = punishmentDetail.getYears();
            this.months = punishmentDetail.getMonths();
            this.days = punishmentDetail.getDays();
        }
    }

    @Data
    public static class MedicalResponseDTO {

        private final Integer days;

        public MedicalResponseDTO(MedicalPunishment punishmentDetail) {
            this.days = punishmentDetail.getDays();
        }
    }

    @Data
    public static class WithdrawalResponseDTO {

        private final Long amount;
        private final LocalDate saleDate;
        private final Long saleAmount;
        private final String saleItems;
        private final LocalDate repaymentDate;

        public WithdrawalResponseDTO(WithdrawalPunishment punishmentDetail) {
            this.amount = punishmentDetail.getAmount();
            this.saleDate = punishmentDetail.getSaleDate();
            this.saleAmount = punishmentDetail.getSaleAmount();
            this.saleItems = punishmentDetail.getSaleItems();
            this.repaymentDate = punishmentDetail.getRepaymentDate();
        }
    }
}

