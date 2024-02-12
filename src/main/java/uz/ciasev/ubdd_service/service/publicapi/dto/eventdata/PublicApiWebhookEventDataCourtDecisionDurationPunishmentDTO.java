package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicApiWebhookEventDataCourtDecisionDurationPunishmentDTO {

    private Integer years;

    private Integer months;

    private Integer days;
}
