package uz.ciasev.ubdd_service.service.violation_event.decision;

import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;

public interface ViolationEventResolvingValidationService {

    void checkNotResolved(Long eventId);

    void checkCompletenessOfDataToMakeDecision(ViolationEventApiDTO event);

    void checkCompletenessOfDataToMakeDecision(UbddTexPassDTOI texPassData);
}
