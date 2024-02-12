package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Getter
public class CancellationResolutionListResponseDTO {

    private final Long reasonCancellationId;
    private final Long organCancellationId;
    private final LocalFileUrl fileCancellationUrl;
    private final LocalDateTime cancellationDate;
    private final String signature;

    public CancellationResolutionListResponseDTO(CancellationResolution cancellationResolution) {
        this.reasonCancellationId = cancellationResolution.getReasonCancellationId();
        this.organCancellationId = cancellationResolution.getOrganCancellationId();
        this.fileCancellationUrl = LocalFileUrl.ofNullable(cancellationResolution.getFileUri());
        this.cancellationDate = cancellationResolution.getCancellationDate();
        this.signature = cancellationResolution.getSignature();
    }
}
