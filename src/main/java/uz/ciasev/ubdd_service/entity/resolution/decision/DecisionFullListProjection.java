package uz.ciasev.ubdd_service.entity.resolution.decision;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DecisionFullListProjection {

     Long getId();
     Long getDecisionTypeId();

     String getSeries();
     String getNumber();

     Long getStatusId();
     Boolean getIsActive();

     String getViolatorFirstNameLat();
     String getViolatorSecondNameLat();
     String getViolatorLastNameLat();
     Long getViolatorNationalityId();
     Long getViolatorGenderId();

     Long getArticlePartId();
     Long getArticleViolationTypeId();

     Long getOrganId();
     Long getDepartmentId();
     Long getRegionId();
     Long getDistrictId();

     LocalDateTime getResolutionTime();

     Long getMainPunishmentTypeId();
     Long getAdditionalPunishmentTypeId();

     String getMainPunishmentAmountText();
     String getAdditionalPunishmentAmountText();

     LocalDate getViolatorBirthDate();
     String getViolatorPostAddressText();
     String getViolatorActualAddressText();

     Boolean getIsMibForceExecution();
     Long getMibTotalRecoveredAmount();

     Boolean getPenaltyIsDiscount70Flag();
     Boolean getPenaltyIsDiscount50Flag();

     default boolean penaltyIsDiscount70() {
         return Boolean.TRUE.equals(getPenaltyIsDiscount70Flag());
     }

     default boolean penaltyIsDiscount50() {
         return Boolean.TRUE.equals(getPenaltyIsDiscount50Flag());
     }

     String getPenaltyInvoiceSerial();
     Long getPenaltyDiscount70Amount();
     LocalDate getPenaltyDiscount70ForDate();
     Long getPenaltyDiscount50Amount();
     LocalDate getPenaltyDiscount50ForDate();
     Long getPenaltyPaidAmount();
     LocalDateTime getPenaltyLastPayTime();
     Long getGovCompensationAmount();
     Long getGovCompensationPaidAmount();
}