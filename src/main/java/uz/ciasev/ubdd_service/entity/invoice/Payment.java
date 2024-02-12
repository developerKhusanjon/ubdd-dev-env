package uz.ciasev.ubdd_service.entity.invoice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq")
    @SequenceGenerator(name = "payment_id_seq", sequenceName = "payment_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonProperty(access = READ_ONLY)
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    private Boolean isUpdated = false;

    @Column(name = "invoice_id", insertable = false, updatable = false)
    private Long invoiceId;

    private String number;

    private String bid;

    private String blankNumber;

    private LocalDate blankDate;

    private LocalDateTime paymentTime;

    private Long amount;

    private String fromBankCode;

    private String fromBankAccount;

    private String fromBankName;

    private String fromInn;

    private String toBankCode;

    private String toBankAccount;

    private String toBankName;

    private String toInn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
