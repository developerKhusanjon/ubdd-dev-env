package uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.resolution.CriminalCaseTransferResultType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.NotInFuture;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class TransferToCriminalCaseRequestDTO implements RegionDistrictRequest {

    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long admCaseId;

    // null будет означать, что инспектор хочет использовать следственный отдел своего органа (organ.criminalInvestigatingDepartmentOrgan)
    @ActiveOnly(message = ErrorCode.ORGAN_DEACTIVATED)
    @JsonProperty(value = "organId")
    private Organ organ;

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    @ActiveOnly(message = ErrorCode.REGION_DEACTIVATED)
    @JsonProperty(value = "regionId")
    private Region region;

    @NotNull(message = ErrorCode.DISTRICT_REQUIRED)
    @ActiveOnly(message = ErrorCode.DISTRICT_DEACTIVATED)
    @JsonProperty(value = "districtId")
    private District district;

    @NotNull(message = ErrorCode.CONSIDER_INFO_REQUIRED)
    @Size(min = 6, max = 75, message = ErrorCode.CONSIDER_INFO_MIN_MAX_SIZE)
    private String considerInfo;

    @NotNull(message = ErrorCode.TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.TYPE_DEACTIVATED)
    @JsonProperty(value = "transferResultTypeId")
    private CriminalCaseTransferResultType transferResultType;

    @NotInFuture(message = ErrorCode.RESOLUTION_DATE_IN_FUTURE)
    @NotNull(message = ErrorCode.RESOLUTION_TIME_REQUIRED)
    private LocalDate resolutionDate;

    @NotNull(message = ErrorCode.RESOLUTION_NUMBER_REQUIRED)
    @Size(min = 6, max = 32, message = ErrorCode.RESOLUTION_NUMBER_MIN_MAX_LENGTH)
    private String resolutionNumber;


    @NotNull(message = ErrorCode.FILE_URI_REQUIRED)
    @ValidFileUri(message = ErrorCode.FILE_URI_INVALID, allow = FileFormatAlias.PDF)
    private String fileUri;

}
