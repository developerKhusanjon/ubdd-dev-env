package uz.ciasev.ubdd_service.entity.history;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "h_digital_signature_registration")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class DigitalSignatureRegistration extends Registration {

    private String action;

    private Long forUserId;

    private Long certificateId;

    private String certificateSerial;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, String> details;

}
