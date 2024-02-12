package uz.ciasev.ubdd_service.entity.notification.sms;

import uz.ciasev.ubdd_service.service.excel.ExcelColumn;
import uz.ciasev.ubdd_service.service.excel.ExcelFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ExcelFile(name = "SMS qidirish")
public interface SmsFullListExcelProjection {

    @ExcelColumn(name = "Ma’muriy ish №", order = 1)
    String getAdmCaseNumber();

    @ExcelColumn(name = "Yuborish organi", order = 2)
    String getOrganName();

    String getRegionName();

    String getDistrictName();

    @ExcelColumn(name = "Yuborilgan viloyat / tuman", order = 3)
    default String getGeography() {
        return String.format(
                "%s / %s",
                Optional.ofNullable(getRegionName()).orElse("-"),
                Optional.ofNullable(getDistrictName()).orElse("-"));
    }

    String getViolatorFirstNameLat();

    String getViolatorSecondNameLat();

    String getViolatorLastNameLat();

    LocalDate getViolatorBirthDate();

    @ExcelColumn(name = "Huquqbuzar", order = 4)
    default String getViolatorBiometricData() {
        return String.format(
                "%s %s %s, %s",
                getViolatorLastNameLat(),
                getViolatorFirstNameLat(),
                Optional.ofNullable(getViolatorSecondNameLat()).orElse(""),
                getViolatorBirthDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    @ExcelColumn(name = "Telefon raqami", order = 5)
    String getPhoneNumber();

    @ExcelColumn(name = "SMS turi", order = 6)
    String getNotificationTypeNameLat();

    LocalDateTime getSendTime();

    @ExcelColumn(name = "Yuborish vaqti", order = 7)
    default String getMessageDispatchTime() {
        LocalDateTime time = getSendTime();

        if (time == null) {
            return "-";
        }
        return time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm)"));
    }

    @ExcelColumn(name = "SMS statusi", order = 8)
    String getDeliveryStatus();

    LocalDateTime getStatusUpdateTime();

    @ExcelColumn(name = "Status yangilash vaqti", order = 9)
    default String getUpdateTime() {
        LocalDateTime time = getStatusUpdateTime();

        if (time == null) {
            return "-";
        }
        return time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm)"));
    }

    @ExcelColumn(name = "SMS ID", order = 10)
    String getMessageId();
}
