package uz.ciasev.ubdd_service.mvd_core.api.court.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationPlaceType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UbddCourtRequest implements Serializable {

    private LocalDate courtOutDate;

    @NotNull(message = "COURT_REGION_REQUIRED")
    @ActiveOnly(message = "COURT_REGION_DEACTIVATED")
    private Region courtRegion;

    @NotNull(message = "COURT_DISTRICT_REQUIRED")
    @ActiveOnly(message = "COURT_DISTRICT_DEACTIVATED")
    private District courtDistrict;

    @NotNull(message = ErrorCode.COURT_CONSIDERING_BASIS_REQUIRED)
    @JsonProperty(value = "courtConsideringBasisId")
    private CourtConsideringBasis courtConsideringBasis;

    @ActiveOnly(message = "COURT_CONSIDERING_ADDITION_DEACTIVATED")
    private CourtConsideringAddition courtConsideringAddition;

    @ActiveOnly(message = "VIOLATION_PLACE_TYPE_DEACTIVATED")
    private ViolationPlaceType violationPlaceType;

    @Size(max = 200, message = "MAX_VIOLATION_PLACE_ADDRESS_LENGTH")
    @NotNull(message = "VIOLATION_PLACE_ADDRESS_REQUIRED")
    private String violationPlaceAddress;

    @NotNull(message = "CLAIM_ID_REQUIRED")
    private Long claimId;

    @NotNull(message = "IS_308_REQUIRED")
    private Boolean is308;

    @NotNull(message = "CASE_ID_REQUIRED")
    private Long caseId;

}
