package uz.ciasev.ubdd_service.entity.history;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.converter.OrganAccountSettingsHistoricActionAliasConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "h_organ_account_settings_action_registration")
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class OrganAccountSettingsActionRegistration extends Registration {


    @Convert(converter = OrganAccountSettingsHistoricActionAliasConverter.class)
    @Column(name = "action_id")
    @Getter
    private OrganAccountSettingsHistoricAction action;


    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Getter
    private Map<String, Object> detail;

    public OrganAccountSettingsActionRegistration(User user, OrganAccountSettingsHistoricAction action, Map<String, Object> detail) {
        this.action = action;
        this.detail = detail;
        this.setCreatedTime(LocalDateTime.now());
        this.setUserId(user.getId());
    }

}
