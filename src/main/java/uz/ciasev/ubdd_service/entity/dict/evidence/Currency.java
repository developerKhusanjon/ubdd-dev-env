package uz.ciasev.ubdd_service.entity.dict.evidence;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.court.CourtAbstractDictEntity;
import uz.ciasev.ubdd_service.utils.deserializer.dict.evidence.CurrencyCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_currency")
@NoArgsConstructor
@JsonDeserialize(using = CurrencyCacheDeserializer.class)
public class Currency extends CourtAbstractDictEntity {
}
