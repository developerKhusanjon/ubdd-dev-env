package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtCaseMovementRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;

public interface CourtThirdMethodMovementService {

    void makeRegistration(ThirdCourtResolutionRequestDTO resolution);

    void makeRevision(ThirdCourtResolutionRequestDTO resolution);

    void makeSeparation(ThirdCourtResolutionRequestDTO resolution);

    void makeTransfer(ThirdCourtResolutionRequestDTO resolution);

    void makeMerge(ThirdCourtResolutionRequestDTO resolution);

    boolean hasRevision(ThirdCourtCaseMovementRequestDTO resolution);

    boolean hasSeparation(ThirdCourtCaseMovementRequestDTO resolution);

    boolean hasTransfer(ThirdCourtCaseMovementRequestDTO requestDTO);

    boolean hasMerge(ThirdCourtCaseMovementRequestDTO requestDTO);

    boolean hasDuplicateMovements(ThirdCourtResolutionRequestDTO resolution);

    void updateViolators(ThirdCourtResolutionRequestDTO resolution);
}
