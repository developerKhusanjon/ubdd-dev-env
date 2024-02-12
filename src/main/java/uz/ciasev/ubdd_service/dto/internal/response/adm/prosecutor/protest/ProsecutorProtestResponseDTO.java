package uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.protest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ProsecutorProtestResponseDTO extends AbstractProsecutorProtestResponseDTO {
    private final Long id;
    private final Long resolutionId;
    private final boolean isAccepted;
    private final Long cancellationResolutionId;

    public ProsecutorProtestResponseDTO(ProsecutorProtest protest) {
        super(protest);

        this.id = protest.getId();
        this.resolutionId = protest.getResolutionId();
        this.isAccepted = protest.isAccepted();
        this.cancellationResolutionId = protest.getCancellationResolutionId();
    }
}
