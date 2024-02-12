package uz.ciasev.ubdd_service.entity.settings;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.utils.deserializer.RoleDeserializer;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bank_account")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@JsonDeserialize(using = RoleDeserializer.class)
public class BankAccount {

    public static final BankAccount EMPTY_ACCOUNT = new BankAccount(-1L);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_id_seq")
    @SequenceGenerator(name = "bank_account_id_seq", sequenceName = "bank_account_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "billing_id", updatable = false, nullable = false)
    private Long billingId;

    public BankAccount(Long id) {
        this.id = id;
    }

}
