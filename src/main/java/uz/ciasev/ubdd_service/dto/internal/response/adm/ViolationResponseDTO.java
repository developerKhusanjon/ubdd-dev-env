package uz.ciasev.ubdd_service.dto.internal.response.adm;


import lombok.Data;
import uz.ciasev.ubdd_service.entity.protocol.ViolationListView;
import uz.ciasev.ubdd_service.utils.FioUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ViolationResponseDTO {

    private final Long protocolId;
    private final String protocolSeries;
    private final String protocolNumber;
    private final Long regionId;
    private final Long districtId;
    private final LocalDateTime violationTime;
    private final LocalDateTime registrationTime;
    private final Long articleId;
    private final Long articlePartId;
    private final Long articleViolationTypeId;
    private final String vehicleNumber;
    private final String violatorFio;
    private final LocalDate violatorBirthDate;
    private final Long admCaseStatusId;
    private final Long decisionId;
    private final Long decisionStatusId;
    private final String decisionSeries;
    private final String decisionNumber;
    private final LocalDateTime resolutionTime;
    private final Long decisionArticleId;
    private final Long decisionArticlePartId;
    private final Long decisionArticleViolationTypeId;
    private final Long decisionTypeId;
    private final Long terminationReasonId;
    private final Long punishmentTypeId;
    private final String punishmentAmount;
    private final Long additionPunishmentTypeId;
    private final String additionPunishmentAmount;

    public ViolationResponseDTO(ViolationListView tuple) {
        this.protocolId = tuple.getId();
        this.protocolSeries = tuple.getProtocolSeries();
        this.protocolNumber = tuple.getProtocolNumber();
        this.regionId = tuple.getProtocolRegionId();
        this.districtId = tuple.getProtocolDistrictId();
        this.violationTime = tuple.getViolationTime();
        this.registrationTime = tuple.getRegistrationTime();
        this.violatorFio = FioUtils.buildFullFio(tuple.getViolatorFirstName(), tuple.getViolatorSecondName(), tuple.getViolatorLastName());
        this.violatorBirthDate = tuple.getViolatorBirthDate();
        this.articleId = tuple.getProtocolArticleId();
        this.articlePartId = tuple.getProtocolArticlePartId();
        this.articleViolationTypeId = tuple.getProtocolArticleViolationTypeId();
        this.decisionId = tuple.getDecisionId();
        this.decisionNumber = tuple.getDecisionNumber();
        this.decisionSeries = tuple.getDecisionSeries();
        this.resolutionTime = tuple.getResolutionTime();
        this.decisionTypeId = tuple.getDecisionTypeId();
        this.terminationReasonId = tuple.getTerminationReasonId();
        this.punishmentTypeId = tuple.getMainPunishmentTypeId();
        this.decisionStatusId = tuple.getDecisionStatusId();
        this.punishmentAmount = tuple.getMainPunishmentAmountText();

        this.vehicleNumber = tuple.getVehicleNumber();
        this.admCaseStatusId = tuple.getAdmCaseStatusId();
        this.decisionArticleId = tuple.getDecisionArticleId();
        this.decisionArticlePartId = tuple.getDecisionArticlePartId();
        this.decisionArticleViolationTypeId = tuple.getDecisionArticleViolationTypeId();
        this.additionPunishmentTypeId = tuple.getAdditionPunishmentTypeId();
        this.additionPunishmentAmount = tuple.getAdditionPunishmentAmountText();
    }
}