package uz.ciasev.ubdd_service.entity.dict.admcase;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendStatusDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.AdmCaseDeletionReasonCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_adm_case_deletion_request_status")
@NoArgsConstructor
@JsonDeserialize(using = AdmCaseDeletionReasonCacheDeserializer.class)
public class AdmCaseDeletionRequestStatus extends AbstractBackendStatusDict<AdmCaseDeletionRequestStatusAlias> {
}
