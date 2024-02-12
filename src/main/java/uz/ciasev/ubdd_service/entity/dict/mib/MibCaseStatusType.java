package uz.ciasev.ubdd_service.entity.dict.mib;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendStatusDict;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_mib_case_status_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MibCaseStatusType extends AbstractBackendStatusDict<MibCaseStatusAlias> {
}
