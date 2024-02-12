package uz.ciasev.ubdd_service.dto.internal.response.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification;
import uz.ciasev.ubdd_service.entity.notification.manual.ManualNotification;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.utils.ConvertUtils;
import uz.ciasev.ubdd_service.utils.types.ApiUrl;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DecisionNotificationListDTO {

    @JsonIgnore
    private final Long id;

    private final MibNotificationTypeAlias type;

    private final String notificationType;

    private final String messageId;

    // Для смс и почты это значение из двух разных спарвочников
    private final Long deliveryStatusId;

    private final LocalDateTime sendDate;

    private final LocalDate receiveDate;

    private final ApiUrl contentPdfUrl;

    private final String text;

    private final String address;

    @JsonProperty("id")
    public String getUniqueId() {
        return ConvertUtils.getUniqueId(type.name(), id);
    }

    public DecisionNotificationListDTO(SmsNotification smsNotification) {
        this.id = smsNotification.getId();
        this.type = MibNotificationTypeAlias.SMS;
        this.notificationType = smsNotification.getNotificationTypeAlias().name();
        this.messageId = smsNotification.getMessageId();
        this.deliveryStatusId = smsNotification.getDeliveryStatusId();
        this.sendDate = smsNotification.getSendTime();
        this.receiveDate = smsNotification.getReceiveDate();
        this.contentPdfUrl = ApiUrl.getSmsPdf(smsNotification);
        this.address = smsNotification.getPhoneNumber();
        this.text = smsNotification.getMessage();
    }

    public DecisionNotificationListDTO(MailNotification mailNotification) {
        this.id = mailNotification.getId();
        this.type = MibNotificationTypeAlias.MAIL;
        this.notificationType = mailNotification.getNotificationTypeAlias().name();
        this.messageId = mailNotification.getMessageNumber();
        this.deliveryStatusId = mailNotification.getDeliveryStatusId();
        this.sendDate = mailNotification.getSendTime();
        this.receiveDate = mailNotification.getReceiveDate();
        this.contentPdfUrl = ApiUrl.getSentMailContent(mailNotification);
        this.address = mailNotification.getAddress();
        this.text = null;
    }

    public DecisionNotificationListDTO(ManualNotification manualNotification) {
        this.id = manualNotification.getId();
        this.type = MibNotificationTypeAlias.MANUAL;
        this.notificationType = manualNotification.getNotificationTypeAlias().name();
        this.messageId = manualNotification.getNumber();
        this.deliveryStatusId = null;
        this.sendDate = manualNotification.getSendDate().atStartOfDay();
        this.receiveDate = manualNotification.getReceiveDate();
        this.contentPdfUrl = new LocalFileUrl(manualNotification.getFileUri());
        this.address = "";
        this.text = manualNotification.getText();
    }
}
