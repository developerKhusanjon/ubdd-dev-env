package uz.ciasev.ubdd_service.event.subscribers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

@Service
@RequiredArgsConstructor
public abstract class RegisterMaterialInCourtSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.REGISTERED_MATERIAL_IN_COURT.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof CourtMaterial) {
            apply((CourtMaterial) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.REGISTERED_MATERIAL_IN_COURT, CourtMaterial.class, data);
        }
    }

    public abstract void apply(CourtMaterial material);

}
