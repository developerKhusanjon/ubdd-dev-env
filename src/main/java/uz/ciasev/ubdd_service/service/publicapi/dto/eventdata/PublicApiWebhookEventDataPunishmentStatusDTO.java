package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

@Data
public class PublicApiWebhookEventDataPunishmentStatusDTO {

//    private Long admCaseId;
    private Long violatorId;
    private Long decisionId;
    private Long punishmentId;
    private Boolean isMain;
    private Long statusId;
    private Integer changeReasonId;
}
