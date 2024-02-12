package uz.ciasev.ubdd_service.service.search.sms;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsFullListProjection;
import uz.ciasev.ubdd_service.utils.types.ApiUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SmsFullListResponseDTO {

    private String messageId;
    private String message;
    private LocalDateTime sendDate;
    private LocalDateTime receiveDate;
    private String phoneNumber;
    private String notificationType;
    private Long deliveryStatusId;
    private String violatorFirstNameLat;
    private String violatorSecondNameLat;
    private String violatorLastNameLat;
    private LocalDate violatorBirthDate;
    private String admCaseNumber;
    private Long admCaseOrganId;
    private Long admCaseRegionId;
    private Long admCaseDistrictId;
    private Long admCaseId;
    private final ApiUrl contentPdfUrl;

    public SmsFullListResponseDTO(SmsFullListProjection projection) {

        this.messageId = projection.getMessageId();
        this.message = projection.getMessage();
        this.sendDate = projection.getSendTime();
        this.receiveDate = projection.getReceiveTime();
        this.phoneNumber = projection.getPhoneNumber();
        this.notificationType = projection.getNotificationType();
        this.deliveryStatusId = projection.getDeliveryStatusId();
        this.violatorFirstNameLat = projection.getViolatorFirstNameLat();
        this.violatorSecondNameLat = projection.getViolatorSecondNameLat();
        this.violatorLastNameLat = projection.getViolatorLastNameLat();
        this.violatorBirthDate = projection.getViolatorBirthDate();
        this.admCaseNumber = projection.getAdmCaseNumber();
        this.admCaseOrganId = projection.getAdmCaseOrganId();
        this.admCaseRegionId = projection.getAdmCaseRegionId();
        this.admCaseDistrictId = projection.getAdmCaseDistrictId();
        this.admCaseId = projection.getAdmCaseId();
        this.contentPdfUrl = ApiUrl.getSmsPdf(projection);
    }
}
