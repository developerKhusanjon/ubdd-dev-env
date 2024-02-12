package uz.ciasev.ubdd_service.entity.dict.statistic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.statistic.StatisticReportTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_statistic_report_type")
@NoArgsConstructor
@JsonDeserialize(using = StatisticReportTypeCacheDeserializer.class)
public class StatisticReportType extends AbstractEmiDict {
}
