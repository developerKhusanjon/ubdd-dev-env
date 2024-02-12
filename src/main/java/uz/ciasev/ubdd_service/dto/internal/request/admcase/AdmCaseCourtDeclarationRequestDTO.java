package uz.ciasev.ubdd_service.dto.internal.request.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationPlaceType;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class AdmCaseCourtDeclarationRequestDTO implements RegionDistrictRequest {

    @NotNull(message = "COURT_REGION_REQUIRED")
    @ActiveOnly(message = "COURT_REGION_DEACTIVATED")
    private Region courtRegion;

    @NotNull(message = "COURT_DISTRICT_REQUIRED")
    @ActiveOnly(message = "COURT_DISTRICT_DEACTIVATED")
    private District courtDistrict;

    @NotNull(message = "COURT_CONSIDERING_BASIS_REQUIRED")
    @ActiveOnly(message = "COURT_CONSIDERING_BASIS_DEACTIVATED")
    private CourtConsideringBasis courtConsideringBasis;

    @ActiveOnly(message = "COURT_CONSIDERING_ADDITION_DEACTIVATED")
    private CourtConsideringAddition courtConsideringAddition;

    @NotNull(message = "VIOLATION_PLACE_TYPE_REQUIRED")
    @ActiveOnly(message = "VIOLATION_PLACE_TYPE_DEACTIVATED")
    private ViolationPlaceType violationPlaceType;

    @Size(max = 200, message = "MAX_VIOLATION_PLACE_ADDRESS_LENGTH")
    @NotNull(message = "VIOLATION_PLACE_ADDRESS_REQUIRED")
    private String violationPlaceAddress;

    @Size(max = 250, message = "MAX_ADM_CASE_FABULA_LENGTH")
    @NotNull(message = "FABULA_REQUIRED")
    private String fabula;

    public AdmCase applyTo(AdmCase admCase) {
        admCase.setCourtOutDate(LocalDate.now());
        admCase.setCourtRegion(this.courtRegion);
        admCase.setCourtDistrict(this.courtDistrict);
        admCase.setCourtConsideringBasis(this.courtConsideringBasis);
        admCase.setCourtConsideringAddition(this.courtConsideringAddition);
        admCase.setViolationPlaceType(this.violationPlaceType);
        admCase.setViolationPlaceAddress(this.violationPlaceAddress);
        admCase.setFabula(this.fabula);

        return admCase;
    }

    @Override
    public Region getRegion() {
        return this.courtRegion;
    }

    @Override
    public District getDistrict() {
        return this.courtDistrict;
    }
}
