package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

@Data
public class PublicApiWebhookEventDataCourtDecisionDTO {

    // violatorId
    private Long id;

    private Long decisionId;

    private Long terminationReasonId;

    private PublicApiWebhookEventDataCourtDecisionPunishmentDTO mainPunishment;

    private PublicApiWebhookEventDataCourtDecisionPunishmentDTO additionalPunishment;
}
