package uz.ciasev.ubdd_service.service.violation_event.build;

import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.entity.Person;

public interface ViolationEventFabulaBuildingService {

    String build(ViolationEventApiDTO violationEvent, ViolatorCreateRequestDTO violatorRequest, Person person, UbddTexPassDTOI techPassData);
}
