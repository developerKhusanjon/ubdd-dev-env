package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Getter
public abstract class AbstractResolutionResponseDTO {

    private final Long userId;
    private final Long admCaseId;
    private final String considerInfo;
    private final Long organId;
    private final Long departmentId;
    private final Long regionId;
    private final Long districtId;
    private final LocalDateTime resolutionTime;
    private final boolean isSimplified;
    private final Long claimId;

    private Long reasonCancellationId;
    private Long organCancellationId;
    private LocalFileUrl fileCancellationUrl;
    private LocalDateTime cancellationDate;
    private String signature;

    public AbstractResolutionResponseDTO(Resolution resolution, CancellationResolution cancellationResolution) {
        this.userId = resolution.getUserId();
        this.admCaseId = resolution.getAdmCaseId();
        this.considerInfo = resolution.getConsiderInfo();
        this.organId = resolution.getOrganId();
        this.departmentId = resolution.getDepartmentId();
        this.regionId = resolution.getRegionId();
        this.districtId = resolution.getDistrictId();
        this.resolutionTime = resolution.getResolutionTime();
        this.isSimplified = resolution.isSimplified();
        this.claimId = resolution.getClaimId();

        if (cancellationResolution != null) {
            this.reasonCancellationId = cancellationResolution.getReasonCancellationId();
            this.organCancellationId = cancellationResolution.getOrganCancellationId();
            this.fileCancellationUrl = LocalFileUrl.ofNullable(cancellationResolution.getFileUri());
            this.cancellationDate = cancellationResolution.getCancellationDate();
            this.signature = cancellationResolution.getSignature();
        }
    }
}

