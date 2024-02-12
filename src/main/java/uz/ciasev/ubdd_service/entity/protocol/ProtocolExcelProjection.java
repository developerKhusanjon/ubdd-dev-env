package uz.ciasev.ubdd_service.entity.protocol;

import uz.ciasev.ubdd_service.service.excel.ExcelColumn;
import uz.ciasev.ubdd_service.service.excel.ExcelFile;
import uz.ciasev.ubdd_service.utils.FioUtils;
import uz.ciasev.ubdd_service.utils.MoneyFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ExcelFile(name = "Протоколы")
public interface ProtocolExcelProjection {

    Long getId();

    LocalDateTime getCreatedTime();

    String getSeries();

    String getNumber();

    @ExcelColumn(name = "Protokol seriya - raqami ", order = 1)
    default String getSeriasNumber() {
        return getSeries() + getNumber();
    }

    @ExcelColumn(name = "Tuzilgan sanasi", order = 2)
    LocalDateTime getRegistrationTime();

    @ExcelColumn(name = "Protocol tuzgan FIO", order = 3)
    String getUserFio();

    @ExcelColumn(name = "Tuzilgan organ", order = 4)
    String getOrganNameLat();

    @ExcelColumn(name = " Organ bo'linmasi", order = 5)
    String getDepartmentNameLat();

    @ExcelColumn(name = "Tuzilish viloyati", order = 6)
    String getRegistrationRegionNameLat();

    @ExcelColumn(name = "Tuzilish tumani", order = 7)
    String getRegistrationDistrictNameLat();

    @ExcelColumn(name = "Mahalla", order = 8)
    String getMtpNameLat();

    @ExcelColumn(name = "Huquqbuzarlik sanasi", order = 9)
    LocalDateTime getViolationTime();

    @ExcelColumn(name = "Ishning holati", order = 10)
    String getAdmCaseStatusNameLat();

    @ExcelColumn(name = "Asosiy modda", order = 11)
    String getArticlePartNameLat();

    @ExcelColumn(name = "Huquqbuzarlik turi", order = 12)
    String getArticleViolationTypeNameLat();

    @ExcelColumn(name = "Huquqbuzarlikning FIO", order = 13)
    default String getViolatorFio() {
        return FioUtils.buildFullFio(
                this.getViolatorFirstNameLat(),
                this.getViolatorSecondNameLat(),
                this.getViolatorLastNameLat());
    }

    String getViolatorFirstNameLat();

    String getViolatorSecondNameLat();

    String getViolatorLastNameLat();

    @ExcelColumn(name = "Tug'ilgan sana", order = 14)
    LocalDate getViolatorBirthDate();

    String getViolatorDocumentSeries();

    String getViolatorDocumentNumber();

    @ExcelColumn(name = "Hujjat seriya - raqami", order = 15)
    default String getViolatorDocument() {
        return String.format("%s-%s", getViolatorDocumentSeries(), getViolatorDocumentNumber());
    }

    @ExcelColumn(name = "Jinsi", order = 16)
    String getViolatorNationalityNameLat();

    @ExcelColumn(name = "Millati", order = 17)
    String getViolatorGenderNameLat();

    @ExcelColumn(name = "Yashash manzili", order = 18)
    String getActualAddressText();

    @ExcelColumn(name = "Qaror chiqargan organ", order = 19)
    String getResolutionOrganCodeLat();

    @ExcelColumn(name = "Tugatish sababi", order = 20)
    String getTerminationReasonNameLat();

    @ExcelColumn(name = "Asosiy chora", order = 21)
    String getPunishmentTypeNameLat();

    Long getPenaltyAmount();

    Long getPenaltyPaidAmount();

    Long getGovCompensationAmount();

    Long getGovCompensationPaidAmount();

    @ExcelColumn(name = "Qo'llangan jarima", order = 22)
    default Double getPenaltyAmountCurrency() {
        return MoneyFormatter.coinToCurrency(getPenaltyAmount());
    }

    @ExcelColumn(name = "Undirilgan jarima", order = 23)
    default Double getPenaltyPaidAmountCurrency() {
        return MoneyFormatter.coinToCurrency(getPenaltyPaidAmount());
    }

    @ExcelColumn(name = "Aniqlangan zarar (so`m)", order = 24)
    default Double getGovCompensationAmountCurrency() {
        return MoneyFormatter.coinToCurrency(getGovCompensationAmount());
    }

    @ExcelColumn(name = "Undirilgan zarar (so`m)", order = 25)
    default Double getGovCompensationPaidAmountCurrency() {
        return MoneyFormatter.coinToCurrency(getGovCompensationPaidAmount());
    }

    @ExcelColumn(name = "Oxirgi to'lov sanasi", order = 26)
    LocalDateTime getPenaltyLastPayTime();

    @ExcelColumn(name = "Qaror holati", order = 27)
    String getDecisionStatusNameLat();

    @ExcelColumn(name = "Avtomobilning davlat raqami", order = 28)
    String getVehicleNumber();

    @ExcelColumn(name = "Avtomobilning markasi", order = 29)
    String getVehicleBrand();
}
