package uz.ciasev.ubdd_service.entity.webhook.egov;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EgovWebhookProjection {

    Long getProtocolId();

    Long getRegionId();

    Long getDistrictId();

    LocalDateTime getViolationTime();

    LocalDateTime getEditedTime();

    String getVehicleNumber();

    Long getPunishmentAmount();

    Long getPaidPunishmentAmount();

    LocalDate getDiscount50Date();

    Long getDiscount50PunishmentAmount();

    LocalDate getDiscount70Date();

    Long getDiscount70PunishmentAmount();

    Long getStatusId();

    String getViolationPlaceAddress();

}
