package uz.ciasev.ubdd_service.service.ombudsman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.ombudsman.OmbudsmanWebhookProtocolDecisionProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OmbudsmanWebhookEventProtocolDecisionDataDTO {
    private String protocolSeriesNumber;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime createdTime;
    private final Long regionId;
    private final String regionValue;
    private final Long districtId;
    private final String districtValue;
    private final Long organId;
    private final String organValue;
    private final String protocolArticlePart;
    private final String inspectorPinpp;
    private final String inspectorFio;

    private final Long status;
    private final String statusName;
    private final String violatorLastName;
    private final String violatorFirstName;
    private final String violatorSecondName;
    @JsonFormat(pattern = "dd-MM-yyyy 00:00:00")
    private final LocalDate birthDate;
    private final String mainPunishmentType;
    private final String mainPunishmentAmount;
    private final Long resolutionOrganId;
    private final String resolutionOrgan;
    private final String resolutionConsiderInfo;
    private final Long fromOrganId;
    private final Long toOrganId;

    public OmbudsmanWebhookEventProtocolDecisionDataDTO(OmbudsmanWebhookProtocolDecisionProjection p) {
        this.protocolSeriesNumber = p.getSeries().concat(p.getNumber());
        this.createdTime = p.getCreatedTime();
        this.regionId = p.getRegionId();
        this.districtId = p.getDistrictId();
        this.organId = p.getOrganId();
        this.protocolArticlePart = p.getProtocolArticlePart();
        this.inspectorPinpp = p.getFormattedInspectorPinpp();
        this.inspectorFio = p.getInspectorFio();
        this.violatorFirstName = p.getFirstName();
        this.violatorSecondName = p.getSecondName();
        this.violatorLastName = p.getLastName();
        this.status = p.getStatus();
        this.statusName = p.getStatusName();
        this.birthDate = p.getBirthDate();
        this.mainPunishmentType = p.getMainPunishmentType();
        this.mainPunishmentAmount = p.getMainPunishmentAmount();
        this.resolutionOrganId = p.getResolutionOrganId();
        this.resolutionOrgan = p.getResolutionOrgan();
        this.resolutionConsiderInfo = p.getResolutionConsiderInfo();
        this.regionValue = p.getRegionValue();
        this.districtValue = p.getDistrictValue();
        this.organValue = p.getOrganValue();
        this.fromOrganId = p.getFromOrganId();
        this.toOrganId = p.getToOrganId();
    }
}