package uz.ciasev.ubdd_service.entity.dict.evidence;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.court.CourtAbstractDictEntity;
import uz.ciasev.ubdd_service.utils.deserializer.dict.evidence.EvidenceResultCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_evidence_result")
@NoArgsConstructor
@JsonDeserialize(using = EvidenceResultCacheDeserializer.class)
public class EvidenceResult extends CourtAbstractDictEntity {
}
