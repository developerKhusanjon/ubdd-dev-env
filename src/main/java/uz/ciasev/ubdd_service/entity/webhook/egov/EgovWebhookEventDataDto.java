package uz.ciasev.ubdd_service.entity.webhook.egov;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookAddressProjection;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookArticlesProjection;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookProtocolDecisionProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EgovWebhookEventDataDto {

    private Long protocolId;
    private Long regionId;
    private Long districtId;
    private LocalDateTime violationTime;
    private LocalDateTime editedTime;
    private String vehicleNumber;
    private Long punishmentAmount;
    private Long paidPunishmentAmount;
    private LocalDate discount50Date;
    private Long discount50PunishmentAmount;
    private LocalDate discount70Date;
    private Long discount70PunishmentAmount;
    private Long statusId;
    private String violationPlaceAddress;

    private List<EgovWebhookArticleProjection> articles = new ArrayList<>();

    public EgovWebhookEventDataDto(EgovWebhookProjection projection) {
        this.setProtocolId(projection.getProtocolId());
        this.setRegionId(projection.getRegionId());
        this.setDistrictId(projection.getDistrictId());
        this.setViolationTime(projection.getViolationTime());
        this.setEditedTime(projection.getEditedTime());
        this.setVehicleNumber(projection.getVehicleNumber());
        this.setPunishmentAmount(projection.getPunishmentAmount());
        this.setPaidPunishmentAmount(projection.getPaidPunishmentAmount());
        this.setDiscount50Date(projection.getDiscount50Date());
        this.setDiscount50PunishmentAmount(projection.getDiscount50PunishmentAmount());
        this.setDiscount70Date(projection.getDiscount70Date());
        this.setDiscount70PunishmentAmount(projection.getDiscount70PunishmentAmount());
        this.setStatusId(projection.getStatusId());
        this.setViolationPlaceAddress(projection.getViolationPlaceAddress());
    }

    public void addArticle(EgovWebhookArticleProjection projection) {
        articles.add(projection);
    }

}