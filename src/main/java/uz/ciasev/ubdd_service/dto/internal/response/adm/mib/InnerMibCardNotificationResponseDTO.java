package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;

import java.time.LocalDate;

@Getter
public class InnerMibCardNotificationResponseDTO {

    private final LocalDate notificationSentDate;
    private final LocalDate notificationReceiveDate;
    private final String notificationNumber;
    private final String notificationText;

    public InnerMibCardNotificationResponseDTO() {
        this.notificationSentDate = null;
        this.notificationReceiveDate = null;
        this.notificationNumber = null;
        this.notificationText = null;
    }

    public InnerMibCardNotificationResponseDTO(DecisionNotification decisionNotification) {
        this.notificationSentDate = decisionNotification.getSendDate();
        this.notificationReceiveDate = decisionNotification.getReceiveDate();
        this.notificationNumber = decisionNotification.getNumber();
        this.notificationText = decisionNotification.getText();
    }
}
