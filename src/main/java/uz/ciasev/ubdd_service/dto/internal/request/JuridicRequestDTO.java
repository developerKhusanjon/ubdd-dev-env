package uz.ciasev.ubdd_service.dto.internal.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.dict.Bank;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.UzbAddress;
import uz.ciasev.ubdd_service.utils.validator.ValidAddress;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuridicRequestDTO {

    @NotNull(message = ErrorCode.INN_REQUIRED)
    @Pattern(regexp = "^\\d{9}$", message = ErrorCode.JURIDIC_INN_FORMAT_INVALID)
    @NotBlank(message = ErrorCode.INN_REQUIRED)
    private String inn;

    @NotNull(message = ErrorCode.ORGANIZATION_NAME_REQUIRED)
    @NotBlank(message = ErrorCode.ORGANIZATION_NAME_REQUIRED)
    private String organizationName;

    @Size(max = 225, message = ErrorCode.ORGANIZATION_HEAD_NAME_MIN_MAX_LENGTH)
    private String organizationHeadName;

    @Size(max = 50, message = ErrorCode.JURIDIC_EMAIL_ADDRESS_MIN_MAX_LENGTH)
    private String emailAddress;

    @Size(max = 50, message = ErrorCode.REGISTRATION_NUMBER_SIZE_MIN_MAX_LENGTH)
    private String registrationNumber;

    private LocalDate registrationDate;

    @Size(max = 25, message = ErrorCode.JURIDIC_PHONE_MIN_MAX_LENGTH)
    private String phone;

    @ActiveOnly(message = ErrorCode.BANK_DEACTIVATED)
    @JsonProperty(value = "bankId")
    private Bank bank;

    @Pattern(regexp = "^\\d{20}$", message = ErrorCode.JURIDIC_BANK_ACCOUNT_FORMAT_INVALID)
    private String bankAccount;

    @Size(min = 3, max = 5, message = ErrorCode.OKED_SIZE_MIN_MAX_LENGTH)
    private String okedCode;

    @Size(max = 200, message = ErrorCode.OKED_NAME_MAX_LENGTH)
    private String okedName;

    @Valid
    @ValidAddress(message = ErrorCode.JURIDIC_FACT_ADDRESS_INVALID)
    @UzbAddress(message = ErrorCode.JURIDIC_FACT_ADDRESS_REGION_REQUIRED)
    private AddressRequestDTO factAddress;

    @Valid
    @ValidAddress(message = ErrorCode.JURIDIC_JUR_ADDRESS_INVALID)
    @UzbAddress(message = ErrorCode.JURIDIC_JUR_ADDRESS_REGION_REQUIRED)
    private AddressRequestDTO jurAddress;

    public Juridic buildJuridic() {

        Juridic juridic = new Juridic();

        applyTo(juridic);

        return juridic;
    }

    public Juridic applyTo(Juridic juridic) {

        juridic.setInn(this.inn);
        juridic.setOrganizationName(this.organizationName);
        juridic.setOrganizationHeadName(this.organizationHeadName);
        juridic.setRegistrationNumber(this.registrationNumber);
        juridic.setRegistrationDate(this.registrationDate);
        juridic.setPhone(this.phone);
        juridic.setEmailAddress(this.emailAddress);
        juridic.setBank(this.bank);
        juridic.setBankAccount(this.bankAccount);
        juridic.setOkedCode(this.okedCode);
        juridic.setOkedName(this.okedName);

        return juridic;
    }
}
