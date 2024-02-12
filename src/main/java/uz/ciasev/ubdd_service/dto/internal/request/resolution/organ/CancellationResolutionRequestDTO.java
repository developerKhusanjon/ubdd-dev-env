package uz.ciasev.ubdd_service.dto.internal.request.resolution.organ;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.OrganCancellation;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellation;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CancellationResolutionRequestDTO {

    @NotNull(message = ErrorCode.CANCELLATION_REASON_REQUIRED)
    @ActiveOnly(message = ErrorCode.CANCELLATION_REASON_DEACTIVATED)
    @JsonProperty(value = "reasonCancellationId")
    private ReasonCancellation reasonCancellation;

    @NotNull(message = ErrorCode.ORGAN_CANCELLATION_REQUIRED)
    @ActiveOnly(message = ErrorCode.ORGAN_CANCELLATION_DEACTIVATED)
    @JsonProperty(value = "organCancellationId")
    private OrganCancellation organCancellation;

    @NotNull(message = ErrorCode.CANCELLATION_TIME_REQUIRED)
    private LocalDateTime cancellationTime;

    @ValidFileUri(allow = FileFormatAlias.PDF, message = ErrorCode.URI_INVALID)
    @NotNull(message = ErrorCode.CANCELLATION_FILE_REQUIRED)
    private String fileUri;

    private String signature;
}
