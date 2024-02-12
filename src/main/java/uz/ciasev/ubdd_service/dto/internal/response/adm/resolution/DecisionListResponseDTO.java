package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.dto.internal.response.InnerPersonResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.utils.types.ApiUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DecisionListResponseDTO {

    @JsonUnwrapped
    private final InnerResolutionResponseDTO resolution;

    @JsonUnwrapped
    private final InnerPersonResponseDTO person;

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final boolean isActive;
    private final Long violatorId;
    private final String series;
    private final String number;
    private final LocalDate executionFromDate;
    private final Long decisionTypeId;
    private final Long terminationReasonId;
    private final boolean isArticle33;
    private final boolean isArticle34;
    private final Long statusId;
    private final LocalDate executedDate;
    private final ApiUrl pdfUrl;
    private final Long articleId;
    private final Long articlePartId;
    private final Long articleViolationTypeId;
    private final Boolean isJuridic;
    private final Boolean isArchive;
    private final Long criminalCaseTransferResultTypeId;

    private final PunishmentListResponseDTO mainPunishment;
    private final PunishmentListResponseDTO additionPunishment;

    private CompensationListResponseDTO govCompensation;


    public DecisionListResponseDTO(Decision decision, Violator violator, Person person, Resolution resolution, CancellationResolution cancellation, PunishmentListResponseDTO mainPunishment, PunishmentListResponseDTO additionPunishment) {
        this.resolution = new InnerResolutionResponseDTO(resolution, cancellation);
        this.person = new InnerPersonResponseDTO(person);

        this.id = decision.getId();
        this.createdTime = decision.getCreatedTime();
        this.editedTime = decision.getEditedTime();
        this.isActive = decision.isActive();
        this.violatorId = decision.getViolatorId();
        this.series = decision.getSeries();
        this.number = decision.getNumber();
        this.executionFromDate = decision.getExecutionFromDate();
        this.decisionTypeId = decision.getDecisionTypeId();
        this.terminationReasonId = decision.getTerminationReasonId();
        this.isArticle33 = decision.isArticle33();
        this.isArticle34 = decision.isArticle34();
        this.pdfUrl = ApiUrl.getDecisionInstance(decision);
        this.statusId = decision.getStatus().getId();
        this.executedDate = decision.getExecutedDate();
        this.articleId = decision.getArticleId();
        this.articlePartId = decision.getArticlePartId();
        this.articleViolationTypeId = decision.getArticleViolationTypeId();
        this.isJuridic = decision.getIsJuridic();
        this.criminalCaseTransferResultTypeId = decision.getCriminalCaseTransferResultTypeId();
        this.isArchive = violator.getIsArchived();

        this.mainPunishment = mainPunishment;
        this.additionPunishment = additionPunishment;
    }
}

