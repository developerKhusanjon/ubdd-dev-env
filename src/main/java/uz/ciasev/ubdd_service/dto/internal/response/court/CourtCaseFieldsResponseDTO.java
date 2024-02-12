package uz.ciasev.ubdd_service.dto.internal.response.court;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
import uz.ciasev.ubdd_service.entity.violator.ViolatorCourtReturnReasonProjection;
import uz.ciasev.ubdd_service.utils.types.Ids;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class CourtCaseFieldsResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime editedTime;

    private Long caseId;
    private Long claimId;

    private String regNumber;
    private LocalDate regDate;

    private LocalDate declinedDate;
    private Ids declinedReasons;

    private Long statusId;

    private Long instance;
    private LocalDateTime hearingDate;
    private boolean isProtest;
    private boolean useVcc;
    private boolean isArticle33;
    private boolean isArticle34;
    private String caseNumber;
    private String judge;

    private Long caseMergeId;
    private Long caseReviewId;
    private Long caseSeparationClaimId;

    private Long returnReason;

    private Long regionId;
    private Long districtId;

    private Boolean isPaused;

    private Long cassationAdditionalResult;
    private Long cancelingReason;
    private Long changingReason;

    private List<ViolatorReturnReasonDTO> returnReasonPerViolators;

    public CourtCaseFieldsResponseDTO(CourtCaseFields courtCaseFields, Optional<CourtCaseChancelleryData> chancelleryDataOpt, List<ViolatorCourtReturnReasonProjection> violatorsReturnReason) {
        this.id = courtCaseFields.getId();
        this.createdTime = courtCaseFields.getCreatedTime();
        this.editedTime = courtCaseFields.getEditedTime();
        this.caseId = courtCaseFields.getCaseId();
        this.claimId = courtCaseFields.getClaimId();
        this.useVcc = courtCaseFields.isUseVcc();
        this.instance = courtCaseFields.getInstance();
        this.hearingDate = courtCaseFields.getHearingDate();
        this.isProtest = courtCaseFields.isProtest();
        this.caseNumber = courtCaseFields.getCaseNumber();
        this.judge = courtCaseFields.getJudge();
        this.caseMergeId = courtCaseFields.getCaseMergeId();
        this.caseReviewId = courtCaseFields.getCaseReviewId();
        this.returnReason = courtCaseFields.getReturnReason();
        this.regionId = courtCaseFields.getRegionId();
        this.districtId = courtCaseFields.getDistrictId();
        this.isPaused = courtCaseFields.getIsPaused();

        this.statusId = courtCaseFields.getStatus() == null
                ? chancelleryDataOpt.map(CourtCaseChancelleryData::getStatusId).orElse(null)
                : courtCaseFields.getStatus().getId();

        chancelleryDataOpt.ifPresent(chancelleryData -> {
            this.regNumber = chancelleryData.getRegistrationNumber();
            this.regDate = chancelleryData.getRegistrationDate();
            this.declinedDate = chancelleryData.getDeclinedDate();
            this.declinedReasons = new Ids(chancelleryData.getDeclinedReasons());
        });

        this.returnReasonPerViolators = violatorsReturnReason.stream().map(ViolatorReturnReasonDTO::new).collect(Collectors.toList());
    }
}
