package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseMovement;

import java.time.LocalDateTime;

@Data
public class AdmCaseMovementListResponseDTO {
    private final Long id;
    private final Long admCaseId;
    private final Long fromOrganId;
    private final Long fromDepartmentId;
    private final Long fromRegionId;
    private final Long fromDistrictId;
    private final Long toOrganId;
    private final Long toDepartmentId;
    private final Long toRegionId;
    private final Long toDistrictId;
    private final LocalDateTime sendTime;
    private final Long statusId;
    private final LocalDateTime declineTime;
    private final LocalDateTime cancelTime;
    private final Long declineReasonId;
    private final Boolean isDecline;
    private final String declineComment;
    private final Boolean isCancelPossible;

    public AdmCaseMovementListResponseDTO(AdmCaseMovement admCaseMovement, Boolean isCancelPossible) {
        this.id = admCaseMovement.getId();
        this.admCaseId = admCaseMovement.getAdmCaseId();
        this.fromOrganId = admCaseMovement.getFromOrganId();
        this.fromDepartmentId = admCaseMovement.getFromDepartmentId();
        this.fromRegionId = admCaseMovement.getFromRegionId();
        this.fromDistrictId = admCaseMovement.getFromDistrictId();
        this.toOrganId = admCaseMovement.getToOrganId();
        this.toDepartmentId = admCaseMovement.getToDepartmentId();
        this.toRegionId = admCaseMovement.getToRegionId();
        this.toDistrictId = admCaseMovement.getToDistrictId();
        this.sendTime = admCaseMovement.getSendTime();
        this.statusId = admCaseMovement.getStatusId();
        this.declineTime = admCaseMovement.getDeclineTime();
        this.cancelTime = admCaseMovement.getCancelTime();
        this.declineReasonId = admCaseMovement.getDeclineReasonId();
        this.isDecline = admCaseMovement.getIsDecline();
        this.declineComment = admCaseMovement.getDeclineComment();
        this.isCancelPossible = isCancelPossible;
    }
}
