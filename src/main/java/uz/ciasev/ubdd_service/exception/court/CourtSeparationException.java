package uz.ciasev.ubdd_service.exception.court;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtCaseSeparationRequestDTO;

public class CourtSeparationException extends CourtGeneralException {

    private static final String CASE_AND_CLAIM_IDS_FIELDS = "Not enough fields for separation : ";

    public CourtSeparationException(ThirdCourtCaseSeparationRequestDTO data) {
        super(CASE_AND_CLAIM_IDS_FIELDS + data.toString());
    }
}
