package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.UbddInsuranceAccident;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.UbddInsuranceDriver;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class UbddInsuranceDataRequestDTO {

//    @NotNull(message = ErrorCode.INSURANCE_APPLICANT_NAME_REQUIRED)
//    @NotBlank(message = ErrorCode.INSURANCE_APPLICANT_NAME_REQUIRED)
    @Size(max = 255, message = ErrorCode.INSURANCE_APPLICANT_NAME_MAX_SIZE)
    private String applicantName;

    @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
    private String applicantType;

    @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
    private String vehicleNumber;

    @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
    private String vehicleBrand;

    @Valid
    private List<UbddInsuranceDriver> drivers;

    @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
    private String insuranceOrganization;

    @NotNull(message = ErrorCode.INSURANCE_POLICY_SERIES_REQUIRED)
    @NotBlank(message = ErrorCode.INSURANCE_POLICY_SERIES_REQUIRED)
    @Size(max = 255, message = ErrorCode.INSURANCE_POLICY_SERIES_MAX_SIZE)
    private String policySeries;

    @NotNull(message = ErrorCode.INSURANCE_POLICY_NUMBER_REQUIRED)
    @NotBlank(message = ErrorCode.INSURANCE_POLICY_NUMBER_REQUIRED)
    @Size(max = 255, message = ErrorCode.INSURANCE_POLICY_NUMBER_MAX_SIZE)
    private String policyNumber;

//    @NotNull(message = ErrorCode.INSURANCE_POLICY_TYPE_REQUIRED)
//    @JsonProperty("policyTypeId")
//    private InsurancePolicyType policyType;
    @JsonProperty("policyType")
    @Size(max = 100, message = ErrorCode.INSURANCE_POLICY_TYPE_MAX_SIZE)
    private String policyType;

    @NotNull(message = ErrorCode.INSURANCE_FROM_DATE_REQUIRED)
    private LocalDate fromDate;

    @NotNull(message = ErrorCode.INSURANCE_TO_DATE_REQUIRED)
    private LocalDate toDate;

    private Long insurancePremium;

    private Long insuranceSum;

    @Valid
    private List<UbddInsuranceAccident> accidents;
}