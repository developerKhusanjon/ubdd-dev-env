package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class JuridicDetailResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final String inn;
    private final String organizationName;
    private final String organizationHeadName;
    private final String registrationNumber;
    private final LocalDate registrationDate;
    private final String phone;
    private final String emailAddress;
    private final String bankAccount;
    private final String okedCode;
    private final String okedName;
    private final Long bankId;

    private AddressResponseDTO factAddress;
    private AddressResponseDTO jurAddress;

    public JuridicDetailResponseDTO(Juridic juridic,
                                    AddressResponseDTO factAddress,
                                    AddressResponseDTO jurAddress) {
        this.id = juridic.getId();
        this.createdTime = juridic.getCreatedTime();
        this.editedTime = juridic.getEditedTime();
        this.inn = juridic.getInn();
        this.organizationName = juridic.getOrganizationName();
        this.organizationHeadName = juridic.getOrganizationHeadName();
        this.registrationNumber = juridic.getRegistrationNumber();
        this.registrationDate = juridic.getRegistrationDate();
        this.phone = juridic.getPhone();
        this.emailAddress = juridic.getEmailAddress();
        this.bankAccount = juridic.getBankAccount();
        this.okedCode = juridic.getOkedCode();
        this.okedName = juridic.getOkedName();
        this.bankId = juridic.getBankId();

        this.factAddress = factAddress;
        this.jurAddress = jurAddress;
    }
}
