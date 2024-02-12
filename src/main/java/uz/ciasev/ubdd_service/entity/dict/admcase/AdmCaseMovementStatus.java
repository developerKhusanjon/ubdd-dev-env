package uz.ciasev.ubdd_service.entity.dict.admcase;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendStatusDict;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_adm_case_movement_status")
@NoArgsConstructor
public class AdmCaseMovementStatus extends AbstractBackendStatusDict<AdmCaseMovementStatusAlias> {
}
