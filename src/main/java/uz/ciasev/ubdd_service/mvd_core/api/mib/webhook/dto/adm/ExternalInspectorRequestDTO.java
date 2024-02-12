package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ExternalInspectorRequestDTO {

    @Size(max = 50, message = ErrorCode.MAX_FIRST_NAME_LENGTH)
    @NotNull(message = ErrorCode.FULL_NAME_REQUIRED)
    private String fullName;

//    @Size(max = 50, message = ErrorCode.MAX_FIRST_NAME_LENGTH)
//    @NotNull(message = ErrorCode.FIRST_NAME_REQUIRED)
//    private String firstName;
//
//    @Size(max = 50, message = ErrorCode.MAX_SECOND_NAME_LENGTH)
//    private String secondName;
//
//    @Size(max = 50, message = ErrorCode.MAX_LAST_NAME_LENGTH)
//    @NotNull(message = ErrorCode.LAST_NAME_REQUIRED)
//    private String lastName;

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    @JsonProperty(value = "regionId")
    private Region region;

    @JsonProperty(value = "districtId")
    private District district;

    @NotNull(message = ErrorCode.POSITION_REQUIRED)
    @JsonProperty(value = "positionId")
    private Position position;

    @NotNull(message = ErrorCode.RANK_REQUIRED)
    @JsonProperty(value = "rankId")
    private Rank rank;

    @NotNull(message = ErrorCode.WORK_CERTIFICATE_REQUIRED)
    private String workCertificate;
}
