package uz.ciasev.ubdd_service.exception.event;

import uz.ciasev.ubdd_service.event.AdmEventType;

public class AdmEventUnexpectedDataTypeOfPairError extends AdmEventUnexpectedDataTypeError {

    public AdmEventUnexpectedDataTypeOfPairError(AdmEventType eventType, Class firstNeedType, Class secondNeedType, Object first, Object second) {
        super(
                eventType,
                String.format("Pair<%s,%s>",
                        firstNeedType.getSimpleName(),
                        secondNeedType.getSimpleName()),
                String.format("Pair<%s,%s>",
                        first != null ? first.getClass().getSimpleName() : "null",
                        second != null ? second.getClass().getSimpleName() : "null")
        );
    }
}
