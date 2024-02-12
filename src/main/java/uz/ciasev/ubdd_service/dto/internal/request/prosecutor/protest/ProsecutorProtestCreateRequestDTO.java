package uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProsecutorProtestCreateRequestDTO extends AbstractProsecutorProtestRequestDTO {

    @NotNull(message = ErrorCode.RESOLUTION_REQUIRED)
    private Long resolutionId;

    @NotEmpty(message = ErrorCode.DOCUMENTS_REQUIRED)
    private List<@ValidFileUri(message = ErrorCode.URI_INVALID) String> documents;

    public ProsecutorProtest buildProtest() {
        return super.applyTo(new ProsecutorProtest());
    }
}
