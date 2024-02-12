package uz.ciasev.ubdd_service.entity.history;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "h_edit_user_registration")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class UserEditRegistration extends Registration {

    private String action;
    private Long onUserId;

    private String fromUsername;
    private Long fromPerson;
    private Long fromOrganId;
    private Long fromRegionId;
    private Long fromDistrictId;
    private Long fromDepartmentId;
    private Long fromStatusId;
    private Boolean fromIsActive;
    private Boolean fromIsConsider;

    private String toUsername;
    private Long toPerson;
    private Long toOrganId;
    private Long toRegionId;
    private Long toDistrictId;
    private Long toDepartmentId;
    private Long toStatusId;
    private Boolean toIsActive;
    private Boolean toIsConsider;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Long> roles;

}
