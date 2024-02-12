package uz.ciasev.ubdd_service.event.subscribers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

@Service
@RequiredArgsConstructor
public abstract class MibPreSendSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.MIB_PRE_SEND.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof Decision) {
            apply((Decision) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.MIB_PRE_SEND, Decision.class, data);
        }
    }

    public abstract void apply(Decision decision);
}
