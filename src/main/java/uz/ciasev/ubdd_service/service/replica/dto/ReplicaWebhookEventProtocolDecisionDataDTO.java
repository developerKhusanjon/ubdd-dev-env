package uz.ciasev.ubdd_service.service.replica.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.replica.ReplicaWebhookProtocolDecisionProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReplicaWebhookEventProtocolDecisionDataDTO {
    private final Long protocolId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime createdTime;
    private final Long regionId;
    private final Long districtId;
    private final Long mtpId;
    private final Long organId;
    private final String protocolArticlePart;
    private final String inspectorPinpp;
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
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime sendDateTime;
    private final String mainPunishmentType;
    private final String mainPunishmentAmount;
    private final Long resolutionOrganId;
    private final Long admCaseOrganId;
    private final String admCaseOrgan;
    private final String resolutionOrgan;
    private final String resolutionConsiderInfo;
    private final String event_id;
    private final String regionValue;
    private final String districtValue;
    private final String organValue;
    private final String mtpValue;

    public ReplicaWebhookEventProtocolDecisionDataDTO(ReplicaWebhookProtocolDecisionProjection p) {
        this.protocolId = p.getProtocolId();
        this.createdTime = p.getCreatedTime();
        this.regionId = p.getRegionId();
        this.districtId = p.getDistrictId();
        this.mtpId = p.getMtpId();
        this.organId = p.getOrganId();
        this.protocolArticlePart = p.getProtocolArticlePart();
        this.inspectorPinpp = p.getFormattedInspectorPinpp();
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
        this.mainPunishmentType = p.getMainPunishmentType();
        this.mainPunishmentAmount = p.getMainPunishmentAmount();
        this.resolutionOrganId = p.getResolutionOrganId();
        this.admCaseOrganId = p.getAdmCaseOrganId();
        this.admCaseOrgan = p.getAdmCaseOrgan();
        this.resolutionOrgan = p.getResolutionOrgan();
        this.resolutionConsiderInfo = p.getResolutionConsiderInfo();
        this.event_id = p.getDistrictId() + "" + p.getProtocolId();
        this.regionValue = p.getRegionValue();
        this.districtValue = p.getDistrictValue();
        this.organValue = p.getOrganValue();
        this.mtpValue = p.getMtpValue();
        this.sendDateTime = LocalDateTime.now();
    }
}