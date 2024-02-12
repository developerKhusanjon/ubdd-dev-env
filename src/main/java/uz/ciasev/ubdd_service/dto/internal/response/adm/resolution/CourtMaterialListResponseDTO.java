package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourtMaterialListResponseDTO {

    private Long id;
    private Long claimId;
    private Long statusId;

    private Long materialTypeId;
    private Boolean isGranted;
    private Long rejectBaseId;
    private Long courtStatusId;
    private String registrationNumber;
    private LocalDate registrationDate;
    private Long regionId;
    private Long districtId;
    private Long instance;
    private String judgeInfo;
    private String caseNumber;
    private LocalDateTime hearingDate;
    private Boolean isVccUsed;
    private Boolean isProtest;
    private Long courtReturnReasonId;
    private Long resolutionId;


    public CourtMaterialListResponseDTO(CourtMaterial courtMaterial, CourtMaterialFields courtFields) {
        this.id = courtMaterial.getId();
        this.claimId = courtMaterial.getClaimId();
        this.statusId = courtMaterial.getStatusId();

        if (courtFields != null) {
            this.materialTypeId = courtFields.getMaterialTypeId();
            this.isGranted = courtFields.getIsGranted();
            this.rejectBaseId = courtFields.getRejectBaseId();
            this.courtStatusId = courtFields.getCourtStatusId();
            this.registrationNumber = courtFields.getRegistrationNumber();
            this.registrationDate = courtFields.getRegistrationDate();
            this.regionId = courtFields.getRegionId();
            this.districtId = courtFields.getDistrictId();
            this.instance = courtFields.getInstance();
            this.judgeInfo = courtFields.getJudgeInfo();
            this.caseNumber = courtFields.getCaseNumber();
            this.hearingDate = courtFields.getHearingDate();
            this.isVccUsed = courtFields.getIsVccUsed();
            this.isProtest = courtFields.getIsProtest();
            this.courtReturnReasonId = courtFields.getCourtReturnReasonId();
            this.resolutionId = courtFields.getResolutionId();
        }

    }
}
