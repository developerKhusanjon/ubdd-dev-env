package uz.ciasev.ubdd_service.dto.internal.request.prosecutor.opinion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinion;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProsecutorOpinionCreateRequestDTO extends AbstractProsecutorOpinionRequestDTO {

    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long admCaseId;

    @NotEmpty(message = ErrorCode.DOCUMENTS_REQUIRED)
    private List<@ValidFileUri(message = ErrorCode.URI_INVALID) String> documents;

    public ProsecutorOpinion buildOpinion() {
        return super.buildOpinion(new ProsecutorOpinion());
    }
}
