package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.court.CourtFinalResultCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "court_final_result")
@NoArgsConstructor
@JsonDeserialize(using = CourtFinalResultCacheDeserializer.class)
public class CourtFinalResult extends CourtAliasedAbstractDictEntity<CourtFinalResultAlias> {
}
