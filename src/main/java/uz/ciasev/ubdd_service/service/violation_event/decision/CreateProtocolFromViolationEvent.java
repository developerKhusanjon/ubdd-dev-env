package uz.ciasev.ubdd_service.service.violation_event.decision;

import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;
import uz.ciasev.ubdd_service.entity.user.User;

interface CreateProtocolFromViolationEvent {

    Pair<Protocol, UbddTexPassData> create(User user, ViolationEventApiDTO event, AdmCase admCase, UbddTexPassDTOI texPassData);
}
