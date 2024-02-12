package uz.ciasev.ubdd_service.entity.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "h_violator_rebind_registration")
@Data
@SuperBuilder()
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class ViolatorRebindRegistration extends Registration {

    private Long protocolId;

    private String fromPinpp;

    private String toPinpp;

    private String fromDocument;

    private String toDocument;
}
