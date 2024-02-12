package uz.ciasev.ubdd_service.mvd_core.api.tax.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaxCompanyTaxDTO {

    private String inn;
    private String organizationName;
    private String organizationHeadName;
    private String emailAddress;
    private String registrationNumber;
    private LocalDate registrationDate;
    private String phone;
    private Long bankId;
    private String bankAccount;
    private String okedCode;
    private String okedName;
    private TaxAddressDTO address;
}
