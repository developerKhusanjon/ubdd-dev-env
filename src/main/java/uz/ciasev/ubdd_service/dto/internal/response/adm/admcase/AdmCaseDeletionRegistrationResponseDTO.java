package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRegistration;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Data
public class AdmCaseDeletionRegistrationResponseDTO {

    private Long id;
    private LocalDateTime time;
    private Long admCaseId;
    private Long userId;
    private Long reasonId;
    private LocalFileUrl documentBaseUrl;
    private String signature;
    private Long recoveredUserId;
    private LocalDateTime recoveredTime;

    public AdmCaseDeletionRegistrationResponseDTO(AdmCaseDeletionRegistration entity) {
        this.id = entity.getId();
        this.time = entity.getCreatedTime();
        this.admCaseId = entity.getAdmCaseId();
        this.userId = entity.getUserId();
        this.reasonId = entity.getReasonId();
        this.documentBaseUrl = LocalFileUrl.ofNullable(entity.getDocumentBaseUri());
        this.signature = entity.getSignature();
        this.recoveredUserId = entity.getRecoveredUserId();
        this.recoveredTime = entity.getRecoveredTime();

    }
}
