package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

public abstract class ProtocolConsiderStatusSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.PROTOCOL_CONSIDER.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof AdmCase) {
            apply((AdmCase) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.PROTOCOL_CONSIDER, AdmCase.class, data);
        }
    }

    public abstract void apply(AdmCase admCase);
}
