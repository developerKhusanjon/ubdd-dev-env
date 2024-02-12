package uz.ciasev.ubdd_service.entity.user;

import uz.ciasev.ubdd_service.service.excel.ExcelColumn;
import uz.ciasev.ubdd_service.service.excel.ExcelFile;

import java.util.Optional;

@ExcelFile(name = "Users")
public interface UserListExcelProjection {

    String getLastName();

    String getFirstName();

    String getSecondName();

    @ExcelColumn(name = "F.I.O", order = 1)
    default String getFIO() {
        return String.format(
                "%s %s %s",
                Optional.ofNullable(getLastName()).orElse(""),
                Optional.ofNullable(getFirstName()).orElse(""),
                Optional.ofNullable(getSecondName()).orElse("")
                );
    }

    @ExcelColumn(name = "Login", order = 2)
    String getUsername();

    @ExcelColumn(name = "Jeton raqami", order = 3)
    String getWorkCertificate();

    @ExcelColumn(name = "Telefon raqami", order = 4)
    String getMobile();

    @ExcelColumn(name = "Shahar telefon raqami", order = 12)
    String getLandline();

    String getDocumentSeries();

    String getDocumentNumber();

    @ExcelColumn(name = "Pasport seriyasi va raqami", order = 11)
    default String getPassport() {
        return String.format(
                "%s-%s",
                getDocumentSeries(),
                getDocumentNumber()
        );
    }

    @ExcelColumn(name = "Organ", order = 5)
    String getOrganName();

    @ExcelColumn(name = "Bo‘linma", order = 6)
    String getOrganDepartmentName();

    @ExcelColumn(name = "Viloyat", order = 7)
    String getRegionName();

    @ExcelColumn(name = "Viloyat bo‘linmasi", order = 13)
    String getBranchRegionName();

    @ExcelColumn(name = "Tuman", order = 8)
    String getDistrictName();

    @ExcelColumn(name = "Rollar", order = 9)
    String getRoles();

    @ExcelColumn(name = "Holat", order = 10)
    String getUserStatusName();

    @ExcelColumn(name = "Lavozim", order = 14)
    String getPositionName();

    @ExcelColumn(name = "Unvon", order = 15)
    String getRankName();
}
