package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

import java.util.List;

@Data
public class PublicApiWebhookEventDataCourtSeparationDTO {

    private Long separatedToAdmCaseId;

    private List<PublicApiWebhookEventDataCourtSeparatedViolatorsDTO> separationViolators;

}
