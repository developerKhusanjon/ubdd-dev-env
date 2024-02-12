package uz.ciasev.ubdd_service.entity.resolution.decision;

import uz.ciasev.ubdd_service.service.excel.ExcelColumn;
import uz.ciasev.ubdd_service.service.excel.ExcelFile;
import uz.ciasev.ubdd_service.utils.FioUtils;
import uz.ciasev.ubdd_service.utils.MoneyFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ExcelFile(name = "Qaror")
public interface DecisionExcelProjection {

     Long getId();

     String getSeries();

     String getNumber();

     Long getPenaltyAmount();

     Long getPenaltyPaidAmount();

     String getViolatorFirstNameLat();

     String getViolatorSecondNameLat();

     String getViolatorLastNameLat();

     Long getGovCompensationAmount();

     Long getGovCompensationPaidAmount();

     @ExcelColumn(name = "Qaror raqami", order = 1)
     default String getSeriesAndNumber() {
          return getSeries() + getNumber();
     }

     @ExcelColumn(name = "Qaror sanasi", order = 2)
     LocalDateTime getResolutionTime();

     @ExcelColumn(name = "Qaror chiqargan inspektorning FIO", order = 3)
     String getConsiderInfo();

     @ExcelColumn(name = "Qaror holati", order = 4)
     String getStatusNameLat();

     @ExcelColumn(name = "Qaror chiqargan organ", order = 5)
     String getOrganNameLat();

     @ExcelColumn(name = "Organ bo'linmasi", order = 6)
     String getDepartmentNameLat();

     @ExcelColumn(name = "Qaror chiqarilish viloyati", order = 7)
     String getRegionNameLat();

     @ExcelColumn(name = "Qaror chiqarilish tumani", order = 8)
     String getDistrictNameLat();

     @ExcelColumn(name = "Huquqbuzarlikning FIO", order = 9)
     default String getViolatorFio() {
          return FioUtils.buildFullFio(
                  this.getViolatorFirstNameLat(),
                  this.getViolatorSecondNameLat(),
                  this.getViolatorLastNameLat());
     }

     @ExcelColumn(name = "Tug'ilgan sana", order = 10)
     LocalDate getViolatorBirthDate();

     @ExcelColumn(name = "Yashash manzili", order = 11)
     String getActualAddressText();

     @ExcelColumn(name = "Tugatish sababi", order = 12)
     String getTerminationReasonNameLat();

     @ExcelColumn(name = "Qaror moddasi", order = 13)
     String getArticlePartNameLat();

     @ExcelColumn(name = "Asosiy chora", order = 14)
     String getMainPunishmentTypeNameLat();

     @ExcelColumn(name = "Qo'llangan jarima", order = 15)
     default Double getPenaltyAmountCurrency() {
          return MoneyFormatter.coinToCurrency(getPenaltyAmount());
     }

     @ExcelColumn(name = "Undirilgan jarima", order = 16)
     default Double getPenaltyPaidAmountCurrency() {
          return MoneyFormatter.coinToCurrency(getPenaltyPaidAmount());
     }

     @ExcelColumn(name = "Aniqlangan zarar (so`m)", order = 17)
     default Double getGovCompensationAmountCurrency() {
          return MoneyFormatter.coinToCurrency(getGovCompensationAmount());
     }

     @ExcelColumn(name = "Undirilgan zarar (so`m)", order = 18)
     default Double getGovCompensationPaidAmountCurrency() {
          return MoneyFormatter.coinToCurrency(getGovCompensationPaidAmount());
     }

     @ExcelColumn(name = "Oxirgi to'lov sanasi", order = 19)
     LocalDateTime getPenaltyLastPayTime();

     @ExcelColumn(name = "Qo'shimcha chora", order = 20)
     String getAdditionPunishmentTypeNameLat();

     @ExcelColumn(name = "Mibga yubor raqam", order = 21)
     String getMibOutNumber();

     @ExcelColumn(name = "Mibga bir marta yubor sana", order = 22)
     LocalDateTime getMibFirstSendTime();

     @ExcelColumn(name = "Mibga yubor sana", order = 23)
     LocalDateTime getMibSendTime();

     @ExcelColumn(name = "Mibga yubor. viloyat", order = 24)
     String getMibRegion();

     @ExcelColumn(name = "Mibga yubor. tuman", order = 25)
     String getMibDistrict();

     @ExcelColumn(name = "Mibga yubo status", order = 26)
     String getMibSendStatus();

     @ExcelColumn(name = "Mib ijro status", order = 27)
     String getMibCaseStatus();

     @ExcelColumn(name = "Mibga qaytar sana", order = 28)
     LocalDateTime getMibReturnTime();

     @ExcelColumn(name = "Текст сообщения об ошибке", order = 29)
     String getMibSendMessage();
}