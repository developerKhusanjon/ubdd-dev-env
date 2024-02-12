package uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotBlank;

@Data
public class ProsecutorProtestDocumentCreateRequestDTO {

    @NotBlank(message = ErrorCode.URI_REQUIRED)
    @ValidFileUri(message = ErrorCode.URI_INVALID)
    private String uri;
}
