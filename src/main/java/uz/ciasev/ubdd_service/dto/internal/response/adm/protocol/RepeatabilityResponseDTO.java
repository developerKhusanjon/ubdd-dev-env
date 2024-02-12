package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;


import lombok.Data;
import uz.ciasev.ubdd_service.entity.protocol.Repeatability;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RepeatabilityResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final Long protocolId;
    private final Long fromProtocolId;
    private final String protocolSeries;
    private final String protocolNumber;
    private final Long regionId;
    private final Long districtId;
    private final LocalDateTime violationTime;
    private final String violatorFio;
    private final LocalDate violatorBirthDate;
    private final Long articleId;
    private final Long articlePartId;
    private final Long articleViolationTypeId;
    private final Long fromDecisionId;
    private final String decisionSeries;
    private final String decisionNumber;
    private final LocalDateTime resolutionTime;
    private final Long decisionTypeId;
    private final Long punishmentTypeId;
    private final String punishmentAmount;
    private final Long decisionStatusId;

    public RepeatabilityResponseDTO(Repeatability repeatability) {
        this.id = repeatability.getId();
        this.createdTime = repeatability.getCreatedTime();
        this.editedTime = repeatability.getEditedTime();
        this.userId = repeatability.getUserId();
        this.protocolId = repeatability.getProtocolId();
        this.fromProtocolId = repeatability.getFromProtocolId();
        this.protocolSeries = repeatability.getProtocolSeries();
        this.protocolNumber = repeatability.getProtocolNumber();
        this.regionId = repeatability.getRegionId();
        this.districtId = repeatability.getDistrictId();
        this.violationTime = repeatability.getViolationTime();
        this.violatorFio = repeatability.getViolatorFio();
        this.violatorBirthDate = repeatability.getViolatorBirthDate();
        this.articleId = repeatability.getArticleId();
        this.articlePartId = repeatability.getArticlePartId();
        this.articleViolationTypeId = repeatability.getArticleViolationTypeId();
        this.fromDecisionId = repeatability.getFromDecisionId();
        this.decisionSeries = repeatability.getDecisionSeries();
        this.decisionNumber = repeatability.getDecisionNumber();
        this.resolutionTime = repeatability.getResolutionTime();
        this.decisionTypeId = repeatability.getDecisionTypeId();
        this.punishmentTypeId = repeatability.getPunishmentTypeId();
        this.punishmentAmount = repeatability.getPunishmentAmount();
        this.decisionStatusId = repeatability.getDecisionStatusId();
    }
}
