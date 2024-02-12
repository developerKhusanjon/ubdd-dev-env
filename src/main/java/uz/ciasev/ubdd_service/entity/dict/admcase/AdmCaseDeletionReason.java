package uz.ciasev.ubdd_service.entity.dict.admcase;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.AdmCaseDeletionReasonCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_adm_case_deletion_reason")
@NoArgsConstructor
@JsonDeserialize(using = AdmCaseDeletionReasonCacheDeserializer.class)
public class AdmCaseDeletionReason extends AbstractEmiDict {
}
