package uz.ciasev.ubdd_service.dto.internal.request.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionType;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReportQueryType3 extends ReportQueryType1 {

    List<AdmStatus> admStatuses;

    DecisionType decisionType;
}
