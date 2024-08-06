package uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.EvidenceDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.ResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialFieldsRegistrationRequest;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialFieldsRequest;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;
import uz.ciasev.ubdd_service.utils.validator.ValidManualCourtMaterial;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@ValidManualCourtMaterial
public class ManualCourtMaterialDTO implements RegionDistrictRequest, CourtMaterialFieldsRegistrationRequest, CourtMaterialFieldsRequest, ResolutionRequestDTO {

    @JsonIgnore
    private CourtMaterialType materialType;
    @JsonIgnore
    private CourtStatus courtStatus;

    @JsonIgnore
    private ManualMaterialDecisionRequestDTO decision;

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    @ActiveOnly(message = ErrorCode.REGION_DEACTIVATED)
    @JsonProperty(value = "regionId")
    private Region region;

    @ActiveOnly(message = ErrorCode.DISTRICT_DEACTIVATED)
    @JsonProperty(value = "districtId")
    private District district;

    @NotNull(message = ErrorCode.REGISTRATION_NUMBER_REQUIRED)
    @NotBlank(message = ErrorCode.REGISTRATION_NUMBER_REQUIRED)
    private String registrationNumber;

    @NotNull(message = ErrorCode.REGISTRATION_DATE_REQUIRED)
    private LocalDate registrationDate; // не меньше чем дата вынесения решения.

    @NotNull(message = ErrorCode.INSTANCE_REQUIRED)
    @Min(value = 1, message = ErrorCode.INSTANCE_INVALID)
    @Max(value = 3, message = ErrorCode.INSTANCE_INVALID)
    private Long instance;

    @NotNull(message = ErrorCode.JUDGE_INFO_REQUIRED)
    @NotBlank(message = ErrorCode.JUDGE_INFO_REQUIRED)
    private String judgeInfo;

    @NotNull(message = ErrorCode.COURT_CASE_NUMBER_REQUIRED)
    @NotBlank(message = ErrorCode.COURT_CASE_NUMBER_REQUIRED)
    private String caseNumber;

    @NotNull(message = ErrorCode.HEARING_DATE_REQUIRED)
    private LocalDateTime hearingTime; // не меньше чем дата регистрации

    @NotNull(message = ErrorCode.URI_REQUIRED)
    @ValidFileUri(allow = FileFormatAlias.PDF, message = ErrorCode.URI_INVALID)
    private String uri;

    private Boolean isArticle33;

    @Override
    public CourtStatus getCourtStatus() {
        return this.courtStatus;
    }

    @Override
    public Boolean getIsProtest() {
        return null;
    }

    @Override
    public Boolean getIsVccUsed() {
        return null;
    }

    @Override
    public LocalDateTime getResolutionTime() {
        return this.hearingTime;
    }


    public Boolean getIsArticle33() {
        return Optional.ofNullable(isArticle33).orElse(false);
    }

    @Override
    public List<ManualMaterialDecisionRequestDTO> getDecisions() {
        decision.setExecutionFromDate(this.getHearingTime().toLocalDate());
        decision.setIsArticle33(getIsArticle33());
        return List.of(decision);
    }

    @Override
    public ResolutionCreateRequest buildResolution() {
        ResolutionCreateRequest resolution = new ResolutionCreateRequest();
        resolution.setResolutionTime(this.hearingTime);
        resolution.setCourtDecisionUri(this.uri);

        return resolution;
    }
}
