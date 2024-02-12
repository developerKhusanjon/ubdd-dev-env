package uz.ciasev.ubdd_service.entity.dict.statistic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.statistic.StatisticReasonViolationCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_statistic_reason_violation")
@NoArgsConstructor
@JsonDeserialize(using = StatisticReasonViolationCacheDeserializer.class)
public class StatisticReasonViolation extends AbstractEmiDict {
}
