package uz.ciasev.ubdd_service.entity.history;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "h_adm_case_merge_separation_registration")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class AdmCaseMergeSeparationRegistration extends Registration {

    @Enumerated(value = EnumType.STRING)
    private AdmCaseRegistrationType type;

    private Long fromAdmCaseId;

    private Long toAdmCaseId;

    private Long protocolId;
}
