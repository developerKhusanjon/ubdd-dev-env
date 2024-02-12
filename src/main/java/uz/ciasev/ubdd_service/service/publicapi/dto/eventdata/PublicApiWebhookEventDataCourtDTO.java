package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PublicApiWebhookEventDataCourtDTO {

    private Long caseId;

    private Long mergedToAdmCaseId;

    private List<PublicApiWebhookEventDataCourtMergedViolatorsDTO> mergedViolators;

    private List<PublicApiWebhookEventDataCourtSeparationDTO> caseSeparation;

    private Long regionId;

    private Long districtId;

    private Long courtConsideringBasisId;

    private Long courtStatusId;

    private LocalDate declinedDate;

    private List<Long> declinedReasons;

    private String caseNumber;

    private LocalDateTime hearingDate;

    private Long returnReasonId;

    private List<PublicApiWebhookEventDataCourtDecisionDTO> violators;

}
