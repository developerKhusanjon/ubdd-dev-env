package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

public abstract class ProtocolCreateSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.PROTOCOL_CREATE.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof Protocol) {
            apply((Protocol) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.PROTOCOL_CREATE, Protocol.class, data);
        }
    }

    public abstract void apply(Protocol protocol);
}
