package uz.ciasev.ubdd_service.exception.court;

import java.util.List;

public class CourtViolatorNotFoundException extends CourtGeneralException {

    private static final String DEFENDANT_NOT_FOUND_WITH_VIOLATOR_ID = "Defendants not found with violatorIds : ";

    public CourtViolatorNotFoundException(List<Long> violatorIds) {
        super(DEFENDANT_NOT_FOUND_WITH_VIOLATOR_ID + violatorIds);
    }
}
