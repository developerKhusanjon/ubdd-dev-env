package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.entity.wanted.WantedProtocol;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

import java.util.List;

public abstract class WantedPersonDetectedSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.WANTED_PERSON_DETECTED.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (!(data instanceof List)) {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.WANTED_PERSON_DETECTED, List.class, data);
        }

        List listTypedData = (List) data;

        try {
            apply((List<WantedProtocol>) listTypedData);
        } catch (Exception e) {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.WANTED_PERSON_DETECTED, e);
        }
    }

    public abstract void apply(List<WantedProtocol> wantedList);
}
