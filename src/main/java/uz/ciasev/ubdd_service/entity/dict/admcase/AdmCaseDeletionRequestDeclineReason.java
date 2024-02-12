package uz.ciasev.ubdd_service.entity.dict.admcase;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.AdmCaseDeletionDeclineReasonCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_adm_case_deletion_request_decline_reason")
@NoArgsConstructor
@JsonDeserialize(using = AdmCaseDeletionDeclineReasonCacheDeserializer.class)
public class AdmCaseDeletionRequestDeclineReason extends AbstractEmiDict {
}
