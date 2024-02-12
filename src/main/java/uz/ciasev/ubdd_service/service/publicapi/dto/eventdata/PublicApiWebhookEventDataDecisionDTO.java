package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

@Data
public class PublicApiWebhookEventDataDecisionDTO {

//    private Long admCaseId;
    private Long violatorId;
    private Long decisionId;
}
