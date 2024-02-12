package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseListProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AdmCaseListResponseDTO {

    private final Long id;
    private final String number;
    private final LocalDate openedDate;
    private final LocalDateTime createdTime;
    private final Long admCaseStatusId;
    private final Long consideredOrganId;
    private final Long consideredDepartmentId;
    private final Long consideredDistrictId;
    private final Long consideredRegionId;
    private final Long considerUserId;
    private final LocalDateTime consideredTime;
    private final String considerInfo;

    private Long courtStatusId;
    private final LocalDateTime courtHearingDate;
    private final String judge;

    private final Long countProtocols;
    private final String violators;
    private final String articleParts;

//    public AdmCaseListResponseDTO(AdmCase admCase) {
//        this.id = admCase.getId();
//        this.number = admCase.getNumber();
//        this.openedDate = admCase.getOpenedDate();
//        this.createdTime = admCase.getCreatedTime();
//        this.admCaseStatusId = admCase.getStatusId();
//        this.consideredOrganId = admCase.getOrganId();
//        this.consideredDepartmentId = admCase.getDepartmentId();
//        this.consideredDistrictId = admCase.getDistrictId();
//        this.consideredRegionId = admCase.getRegionId();
//        this.considerUserId = admCase.getConsiderUserId();
//        this.consideredTime = admCase.getConsideredTime();
//        this.considerInfo = admCase.getConsiderInfo();
//        this.countProtocols = null;
//        this.violators = null;
//        this.courtStatusId = admCase.getCourtStatusId();
//    }

    public AdmCaseListResponseDTO(AdmCaseListProjection admCase) {
        this.id = admCase.getId();
        this.number = admCase.getNumber();
        this.openedDate = admCase.getOpenedDate();
        this.createdTime = admCase.getCreatedTime();
        this.admCaseStatusId = admCase.getStatusId();
        this.consideredOrganId = admCase.getOrganId();
        this.consideredDepartmentId = admCase.getDepartmentId();
        this.consideredDistrictId = admCase.getDistrictId();
        this.consideredRegionId = admCase.getRegionId();
        this.consideredTime = admCase.getConsideredTime();
        this.considerUserId = admCase.getConsiderUserId();
        this.considerInfo = admCase.getConsiderInfo();

        this.courtStatusId = admCase.getCourtStatusId();
        this.courtHearingDate = admCase.getCourtHearingDate();
        this.judge = admCase.getJudge();

        this.violators = admCase.getViolators();
        this.articleParts = admCase.getArticleParts();
        this.countProtocols = admCase.getProtocolCount();
    }
}
