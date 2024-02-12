package uz.ciasev.ubdd_service.entity.protocol;

import uz.ciasev.ubdd_service.service.excel.ExcelColumn;
import uz.ciasev.ubdd_service.service.excel.ExcelFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ExcelFile(name = "Протоколы МАБ")
public interface ProtocolUbddListExcelProjection {

    Long getId();

    @ExcelColumn(name = "Holat", order = 1)
    String getStatusLat();

    @ExcelColumn(name = "Аvto raqami", order = 2)
    String getVehicleNumber();

    String getViolatorDocumentSeries();

    String getViolatorDocumentNumber();

    @ExcelColumn(name = "Hujjati seriya va raqam", order = 3)
    default String getViolatorDocument() {
        return String.format("%s-%s", getViolatorDocumentSeries(), getViolatorDocumentNumber());
    }

    String getOldSeries();

    String getOldNumber();

    @ExcelColumn(name = "Seriya va raqam", order = 4)
    default String getOldProtocolSeries() {
        if (Boolean.TRUE.equals(getIsTablet())) return "PLANSHET";
        return String.format("%s-%s", getOldSeries(), getOldNumber());
    }

    @ExcelColumn(name = "Tuz-n sana", order = 5)
    LocalDateTime getRegistrationTime();

    String getSeries();

    String getNumber();

    @ExcelColumn(name = "EMI seriya va raqami", order = 6)
    default String getProtocolSeries() {
        return String.format("%s-%s", getSeries(), getNumber());
    }

    @ExcelColumn(name = "Huquqbuzarlik sodir etilgan sana", order = 7)
    LocalDateTime getViolationTime();

    @ExcelColumn(name = "Tur", order = 8)
    String getArticleLat();

    @ExcelColumn(name = "Modda", order = 9)
    String getArticlePartLat();

    @ExcelColumn(name = "Marka", order = 10)
    String getVehicleBrand();

    @ExcelColumn(name = "Rang", order = 11)
    String getVehicleColor();

    @ExcelColumn(name = "Ism", order = 13)
    String getViolatorFirstName();

    @ExcelColumn(name = "Familiyasi", order = 12)
    String getViolatorLastName();

    @ExcelColumn(name = "Otasini ismi", order = 14)
    String getViolatorSecondName();

    @ExcelColumn(name = "Tug‘ilgan sana", order = 15)
    LocalDate getViolatorBirthDate();

    @ExcelColumn(name = "Huquqbuzarning manzili", order = 16)
    String getViolatorAddressFullText();

    String getViolatorPinpp();

    Boolean getViolatorIsRealPinpp();

    @ExcelColumn(name = "PINFL", order = 17)
    default String getViolatorTruePinpp() {
        if (Boolean.TRUE.equals(getViolatorIsRealPinpp())) return getViolatorPinpp();
        return "-";
    }

    @ExcelColumn(name = "Telefon raqami", order = 18)
    String getViolatorMobile();

    @ExcelColumn(name = "Tuzgan viloyat", order = 19)
    String getRegisteredRegionLat();

    @ExcelColumn(name = "Qabul qilgan viloyat", order = 20)
    String getConsideredRegionLat();

    @ExcelColumn(name = "Tuzgan", order = 21)
    String getRegisteredUserFio();

    @ExcelColumn(name = "Koʼrib chiqilmoqda", order = 22)
    String getConsideredUserFio();

    Boolean getIsTablet();

}
