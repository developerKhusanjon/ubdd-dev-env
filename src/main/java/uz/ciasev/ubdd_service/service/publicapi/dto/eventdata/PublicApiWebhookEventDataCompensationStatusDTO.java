package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

@Data
public class PublicApiWebhookEventDataCompensationStatusDTO {

//    private Long admCaseId;
    private Long violatorId;
    private Long decisionId;
    private Long compensationId;
    private Long statusId;
}
