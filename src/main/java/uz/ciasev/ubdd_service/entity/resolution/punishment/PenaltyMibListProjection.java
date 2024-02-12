package uz.ciasev.ubdd_service.entity.resolution.punishment;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PenaltyMibListProjection {

     Long getId();

     String getDecisionSeries();
     String getDecisionNumber();

     Long getDecisionId();

     Long getPunishmentStatusId();

     LocalDateTime getResolutionTime();

     Long getRegionId();
     Long getDistrictId();

     String getResolutionConsiderInfo();

     String getViolatorFirstNameLat();
     String getViolatorSecondNameLat();
     String getViolatorLastNameLat();

     LocalDate getViolatorBirthDate();

     String getPunishmentAmountText();

     LocalDate getArrestInDate();
     LocalDate getArrestOutDate();

     Long getArrestPlaceTypeId();
}