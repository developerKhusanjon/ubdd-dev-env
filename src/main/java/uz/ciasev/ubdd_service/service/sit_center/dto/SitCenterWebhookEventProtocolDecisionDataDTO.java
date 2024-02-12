package uz.ciasev.ubdd_service.service.sit_center.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.sit_center.SitCenterWebhookProtocolDecisionProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class SitCenterWebhookEventProtocolDecisionDataDTO {
    private final Long protocolId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime createdTime;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime updatedTime;
    private final Long regionId;
    private final Long districtId;
    private final Long mtpId;
    private final Long organId;
    private final String protocolArticlePart;
    private final String inspectorPinpp;
    private final Double latitude;
    private final Double longitude;
    private final String series;
    private final String number;
    private final Long status;
    private final String statusName;
    private final String lastName;
    private final String firstName;
    private final String secondName;
    private final String documentSeries;
    private final String documentNumber;
    private final String pinpp;
    @JsonFormat(pattern = "dd-MM-yyyy 00:00:00")
    private final LocalDate birthDate;
    private final Long decisionTypeId;
    private final String decisionTypeName;
    @JsonFormat(pattern = "dd-MM-yyyy 00:00:00")
    private final LocalDate executionDate;
    private final String mainPunishmentType;
    private final String mainPunishmentAmount;
    private final Long resolutionOrganId;
    private final Long admCaseOrganId;
    private final String admCaseOrgan;
    private final String resolutionOrgan;
    private final String resolutionConsiderInfo;

    private final Long protocolArticlePartId;
    private final Long victimId;
    private final String victimPinpp;
    private final Long mainPunishmentAmountSumm;
    private final Long mainPunishmentPaidAmount;
    private final LocalDateTime mainPunishmentLastPayTime;
    private final LocalDate discountForDate70;
    private final Long discountAmount70;
    private final LocalDate discountForDate50;
    private final Long discountAmount50;
    private final Long damageAmount;
    private final String damageTypeName;
    private final Long damageTypeId;
    private final Long compensationAmount;
    private final Long compensationPaidAmount;

    private final Long terminationReasonId;
    private final Long punishmentTypeId;

    public SitCenterWebhookEventProtocolDecisionDataDTO(SitCenterWebhookProtocolDecisionProjection p) {
        this.protocolId = p.getProtocolId();
        this.createdTime = p.getCreatedTime();
        this.updatedTime=p.getUpdatedTime();
        this.regionId = p.getRegionId();
        this.districtId = p.getDistrictId();
        this.mtpId = p.getMtpId();
        this.organId = p.getOrganId();
        this.protocolArticlePart = p.getProtocolArticlePart();
        this.inspectorPinpp = p.getFormattedInspectorPinpp();
        this.latitude = p.getLatitude();
        this.longitude = p.getLongitude();
        this.series = p.getSeries();
        this.number = p.getNumber();
        this.status = p.getStatus();
        this.statusName = p.getStatusName();
        this.lastName = p.getLastName();
        this.firstName = p.getFirstName();
        this.secondName = p.getSecondName();
        this.documentSeries = p.getDocumentSeries();
        this.documentNumber = p.getDocumentNumber();
        this.pinpp = p.getFormattedPinpp();
        this.birthDate = p.getBirthDate();
        this.decisionTypeId = p.getDecisionTypeId();
        this.decisionTypeName = p.getDecisionTypeName();
        this.executionDate = p.getExecutionDate();
        this.mainPunishmentType = p.getMainPunishmentType();
        this.mainPunishmentAmount = p.getMainPunishmentAmount();
        this.resolutionOrganId = p.getResolutionOrganId();
        this.admCaseOrganId = p.getAdmCaseOrganId();
        this.admCaseOrgan = p.getAdmCaseOrgan();
        this.resolutionOrgan = p.getResolutionOrgan();
        this.resolutionConsiderInfo = p.getResolutionConsiderInfo();

        this.protocolArticlePartId = p.getProtocolArticlePartId();
        this.victimId = p.getVictimId();
        this.victimPinpp = p.getVictimPinpp();
        this.mainPunishmentAmountSumm = p.getMainPunishmentAmountSumm();
        this.mainPunishmentPaidAmount = p.getMainPunishmentPaidAmount();
        this.mainPunishmentLastPayTime = p.getMainPunishmentLastPayTime();
        this.discountForDate70 = p.getDiscountForDate70();
        this.discountAmount70 = p.getDiscountAmount70();
        this.discountForDate50 = p.getDiscountForDate50();
        this.discountAmount50 = p.getDiscountAmount50();
        this.damageAmount = p.getDamageAmount();
        this.damageTypeName = p.getDamageTypeName();
        this.damageTypeId = p.getDamageTypeId();
        this.compensationAmount = p.getCompensationAmount();
        this.compensationPaidAmount = p.getCompensationPaidAmount();

        this.terminationReasonId = p.getTerminationReasonId();
        this.punishmentTypeId = p.getPunishmentTypeId();
    }
}
