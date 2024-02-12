package uz.ciasev.ubdd_service.entity.history;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.utils.converter.DictAdminHistoricActionAliasConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "h_dictionary_admin_action_registration")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class DictionaryAdminActionRegistration extends Registration {


    @Convert(converter = DictAdminHistoricActionAliasConverter.class)
    @Column(name = "action_id")
    private DictAdminHistoricAction action;

    private String entityType;

    private Long entityId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> detail;

}
