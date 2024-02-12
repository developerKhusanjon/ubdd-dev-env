package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.event.AdmEventType;

public interface AdmEventSubscriber {

    boolean canAccept(AdmEventType type, Object data);
    void accept(Object data);
}
