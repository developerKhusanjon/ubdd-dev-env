package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PublicApiWebhookEventDataCourtDecisionPunishmentDTO {

    private Long punishmentId;

    private Long penaltyAmount;

    private PublicApiWebhookEventDataCourtDecisionDurationPunishmentDTO licenseRevocation;

    private PublicApiWebhookEventDataCourtDecisionDurationPunishmentDTO deportation;

    private Integer arrestDays;

    private Long confiscationAmount;

    private LocalDate deportationDate;

    private Long withdrawalAmount;

    private Integer medicalDays;
}
