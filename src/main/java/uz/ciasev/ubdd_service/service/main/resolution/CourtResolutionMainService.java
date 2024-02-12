package uz.ciasev.ubdd_service.service.main.resolution;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import javax.validation.Valid;
import java.util.Optional;

@Validated
public interface CourtResolutionMainService {

    void revisionCaseInCourt(Long caseId, Long claimId);

    void prepareCaseForEditing(Long caseId, Long claimId);

    void returnCaseFromCourt(Long caseId, Long claimId);

    void otherCourtInstanceReturnFromCourt(Long caseId, Long claimId);

    void otherCourtInstanceRetrialCase(Long caseId, Long claimId);

    @Deprecated
    Optional<Resolution> courtCancelResolutionByAdmCaseAndSetNewAdmStatus(Long caseId, Long claimId, AdmStatusAlias admStatusAlias, boolean hasCancellationResolution, boolean isEditing);


    Resolution createCourtResolution(Long admCaseId, @Valid CourtResolutionRequestDTO courtResolution);
}
