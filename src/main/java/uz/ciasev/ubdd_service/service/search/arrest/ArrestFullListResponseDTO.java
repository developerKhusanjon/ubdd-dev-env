package uz.ciasev.ubdd_service.service.search.arrest;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestFullListProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ArrestFullListResponseDTO {

    private Long id;

    private String decisionSeries;
    private String decisionNumber;

    private Long decisionId;

    private Long punishmentStatusId;

    private LocalDateTime resolutionTime;

    private Long regionId;
    private Long districtId;

    private String resolutionConsiderInfo;

    private String violatorFirstNameLat;
    private String violatorSecondNameLat;
    private String violatorLastNameLat;

    private LocalDate violatorBirthDate;

    private String punishmentAmountText;

    private LocalDate arrestInDate;
    private LocalDate arrestOutDate;

    private Long arrestPlaceTypeId;

    public ArrestFullListResponseDTO(ArrestFullListProjection projection) {

        this.id = projection.getId();

        this.decisionSeries = projection.getDecisionSeries();
        this.decisionNumber = projection.getDecisionNumber();

        this.decisionId = projection.getDecisionId();

        this.punishmentStatusId = projection.getPunishmentStatusId();

        this.resolutionTime = projection.getResolutionTime();

        this.regionId = projection.getRegionId();
        this.districtId = projection.getDistrictId();

        this.resolutionConsiderInfo = projection.getResolutionConsiderInfo();

        this.violatorFirstNameLat = projection.getViolatorFirstNameLat();
        this.violatorSecondNameLat = projection.getViolatorSecondNameLat();
        this.violatorLastNameLat = projection.getViolatorLastNameLat();

        this.violatorBirthDate = projection.getViolatorBirthDate();

        this.punishmentAmountText = projection.getPunishmentAmountText();

        this.arrestInDate = projection.getArrestInDate();
        this.arrestOutDate = projection.getArrestOutDate();

        this.arrestPlaceTypeId = projection.getArrestPlaceTypeId();
    }
}
