package uz.ciasev.ubdd_service.mvd_core.experiencesapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ExperienceDto {

    private Integer employmentStatus;   // Род занятий
    private String companyName;         // Название организации
    private String workplaceAddress;    // Адрес организации
    private String companyInn;          // ИНН организации
    private String positionName;        // Должность
    private String startDate;           // Дата приема на работу
    private String structureName;       //
    private String endDate;             // Дата увольнения с работы-

    @JsonIgnore
    private String contractDate;
    @JsonIgnore
    private String contractNumber;
    @JsonIgnore
    private String kodp;
    @JsonIgnore
    private String kodpType;
    @JsonIgnore
    private String orderDate;
    @JsonIgnore
    private String orderNumber;
    @JsonIgnore
    private String personPin;
    @JsonIgnore
    private String soatoCode;
    @JsonIgnore
    private int transactionId;

}
