package uz.ciasev.ubdd_service.service.violation_event.build;

import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;

public interface ViolationEventViolatorBuildingService {

    ViolatorCreateRequestDTO build(UbddTexPassDTOI texPassData);

}
