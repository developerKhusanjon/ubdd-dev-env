package uz.ciasev.ubdd_service.service.report;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.request.report.ReportQueryType3;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionType;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

@Getter
public class ReportContextT3 extends ReportContextT1 {

    private final List<AdmStatus> admStatuses;

    private final DecisionType decisionType;

    public ReportContextT3(User user, ReportQueryType3 params) {
        super(user, params);
        this.admStatuses = normalizeInput(params.getAdmStatuses());
        this.decisionType = params.getDecisionType();
    }
}
