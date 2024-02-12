package uz.ciasev.ubdd_service.entity.protocol;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.Bank;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "juridic")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Juridic implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "juridic_id_seq")
    @SequenceGenerator(name = "juridic_id_seq", sequenceName = "juridic_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Getter
    @Setter
    private String inn;

    @Getter
    @Setter
    private String organizationName;

    @Getter
    @Setter
    private String organizationHeadName;

    @Getter
    @Setter
    private String registrationNumber;

    @Getter
    @Setter
    private LocalDate registrationDate;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private String emailAddress;

    @Getter
    @Setter
    private String bankAccount;

    @Getter
    @Setter
    private String okedCode;

    @Getter
    @Setter
    private String okedName;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fact_address_id")
    private Address factAddress;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jur_address_id")
    private Address jurAddress;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "fact_address_id", insertable = false, updatable = false)
    private Long factAddressId;

    @Column(name = "jur_address_id", insertable = false, updatable = false)
    private Long jurAddressId;

    @Column(name = "bank_id", insertable = false, updatable = false)
    private Long bankId;

    public Long getFactAddressId() {
        if (factAddress == null) return null;
        return factAddress.getId();
    }

    public Long getJurAddressId() {
        if (jurAddress == null) return null;
        return jurAddress.getId();
    }

    public Long getBankId() {
        if (bank == null) return null;
        return bank.getId();
    }
}