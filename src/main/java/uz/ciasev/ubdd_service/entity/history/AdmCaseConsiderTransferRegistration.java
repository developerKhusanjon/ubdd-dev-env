package uz.ciasev.ubdd_service.entity.history;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "h_adm_case_consider_transfer_registration")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdmCaseConsiderTransferRegistration extends Registration {

    private Long admCaseId;

    @Enumerated(value = EnumType.STRING)
    private AdmStatusAlias fromStatus;

    @Enumerated(value = EnumType.STRING)
    private AdmStatusAlias status;

    @Enumerated(value = EnumType.STRING)
    private AdmCaseRegistrationType type;

    private Long considerUserId;

    private Long statusId;

    private Long organId;

    private Long departmentId;

    private Long regionId;

    private Long districtId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AdmCaseConsiderTransferRegistration that = (AdmCaseConsiderTransferRegistration) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
