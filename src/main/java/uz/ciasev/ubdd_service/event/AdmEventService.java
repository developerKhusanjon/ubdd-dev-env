package uz.ciasev.ubdd_service.event;

public interface AdmEventService {

    void fireEvent(AdmEventType type, Object data);
}
