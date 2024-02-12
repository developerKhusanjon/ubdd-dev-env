package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.requirement;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.entity.AdmEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PdfRequirementDTO extends PdfModel {

    private String number;

    //  Дата выполнения операции
    private String createdDate;
    //  Время выполнения операции
    private String createdTime;
    //  Выполневший пользователь
    private Long userId;

    //  Год выполнения операции
    private String year;
    //  Месяц выполнения операции
    private String month;
    //  День выполнения операции
    private String stampDate;

    //  Фамилия пробиваемого человека
    private String lastName;
    //  Имя пробиваемого человека
    private String firstName;
    //  Отчество пробиваемого человека
    private String secondName;
    //  ФИО пробиваемого человека на латинице
    private String nameLat;
    //  Дата рождения пробиваемого человека
    private String birthYear;
    private String birthMonth;
    private String birthDate;
    //  Место проживания пробиваемого человека
    private String residentAddress;

    private String checkReasons = "Запрос органа";
    //  Название органа выполняющего пользователя
    private String headOfOrgan;
    //  Данные выполняющего пользователя
    private String executor;
    //  Почтовый индекс органа выполняющего пользователя
    private String post;
    //  Аддрес органа выполняющего пользователя
    private String address;

    private String titleQr;
    private String signQr;
    private String stampQr;

    List<PdfRequirementProtocolDTO> response = new ArrayList<>();

    public PdfRequirementDTO(AdmEntity admEntity) {
        super(admEntity);
    }
}
