package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;

public abstract class CourtResolutionSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.COURT_RESOLUTION_CREATE.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof CreatedResolutionDTO) {
            apply((CreatedResolutionDTO) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.COURT_RESOLUTION_CREATE, CreatedResolutionDTO.class, data);
        }
    }

    public abstract void apply(CreatedResolutionDTO createdResolution);
}
