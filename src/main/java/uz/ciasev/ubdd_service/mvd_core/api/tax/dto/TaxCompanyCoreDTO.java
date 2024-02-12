package uz.ciasev.ubdd_service.mvd_core.api.tax.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaxCompanyCoreDTO {

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
    private TaxAddressDTO jurAddress;

    public TaxCompanyCoreDTO(TaxCompanyTaxDTO taxDTO) {

        this.inn = taxDTO.getInn();
        this.organizationName = taxDTO.getOrganizationName();
        this.organizationHeadName = taxDTO.getOrganizationHeadName();
        this.emailAddress = taxDTO.getEmailAddress();
        this.registrationNumber = taxDTO.getRegistrationNumber();
        this.registrationDate = taxDTO.getRegistrationDate();
        this.phone = taxDTO.getPhone();
        this.bankId = taxDTO.getBankId();
        this.bankAccount = taxDTO.getBankAccount();
        this.okedCode = taxDTO.getOkedCode();
        this.okedName = taxDTO.getOkedName();
        this.jurAddress = taxDTO.getAddress();
    }
}
