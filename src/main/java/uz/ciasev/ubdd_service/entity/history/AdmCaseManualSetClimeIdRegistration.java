package uz.ciasev.ubdd_service.entity.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "h_adm_case_manual_set_registration")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class AdmCaseManualSetClimeIdRegistration extends Registration {

    private Long admCaseId;

    private Long climeId;
}
