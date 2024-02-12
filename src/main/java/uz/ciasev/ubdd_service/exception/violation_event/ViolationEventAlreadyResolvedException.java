package uz.ciasev.ubdd_service.exception.violation_event;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventResult;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class ViolationEventAlreadyResolvedException extends ApplicationException {

    public ViolationEventAlreadyResolvedException(ViolationEventResult existResult) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.VIOLATION_EVENT_ALREADY_HAS_RESULT,
                "Violation event already has result"
//                String.format(
//                        "User %s already make decision with type %s on this violation type",
//                )
        );
    }
}
