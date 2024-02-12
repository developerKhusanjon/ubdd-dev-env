package uz.ciasev.ubdd_service.exception.event;

import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;

public class AdmEventUnexpectedDataTypeError extends ImplementationException {

    private AdmEventUnexpectedDataTypeError(AdmEventType eventType, String message) {
        super(
                ErrorCode.ADM_EVENT_UNEXPECTED_DATA_TYPE_ERROR,
                String.format("Error for cast data for event %s: %s",
                        eventType,
                        message
                )
        );
    }

    protected AdmEventUnexpectedDataTypeError(AdmEventType eventType, String need, String get) {
        super(
                ErrorCode.ADM_EVENT_UNEXPECTED_DATA_TYPE_ERROR,
                String.format("EventSubscriber for event %s expect data type %s but got %s",
                        eventType,
                        need,
                        get
                )
        );
    }

    public AdmEventUnexpectedDataTypeError(AdmEventType eventType, Class need, Object data) {
        this(
                eventType,
                need.getSimpleName(),
                data != null ? data.getClass().getSimpleName() : "null"
        );
    }

    public AdmEventUnexpectedDataTypeError(AdmEventType eventType, Exception e) {
        this(
                eventType,
                e.getMessage()
        );
    }

}
