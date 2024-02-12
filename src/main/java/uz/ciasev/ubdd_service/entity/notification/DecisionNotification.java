package uz.ciasev.ubdd_service.entity.notification;

import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;

import java.time.LocalDate;

public interface DecisionNotification {

    Long getId();

    MibNotificationTypeAlias getChannel();

    String getText();

    String getNumber();

    LocalDate getSendDate();

    LocalDate getReceiveDate();


//    public DecisionNotification(SmsNotification smsNotification) {
//        this.id = smsNotification.getId();
//        this.type = MibNotificationTypeAlias.SMS;
//        this.text = smsNotification.getMessage();
//        this.number = smsNotification.getPhoneNumber();
//        this.sendDate = Optional.ofNullable(smsNotification.getSendTime()).map(LocalDateTime::toLocalDate).orElse(null);
//        this.receiveDate = Optional.ofNullable(smsNotification.getReceiveTime()).map(LocalDateTime::toLocalDate).orElse(null);
//    }
//
//    public DecisionNotification(MailNotification mailNotification) {
//        this.id = mailNotification.getId();
//        this.type = MibNotificationTypeAlias.MAIL;
//        this.text = "Оповещение почтой";
//        this.number = mailNotification.getMessageNumber();
//        this.sendDate = Optional.ofNullable(mailNotification.getSendTime()).map(LocalDateTime::toLocalDate).orElse(null);
//        this.receiveDate = mailNotification.getPerformDate();
//    }
}
