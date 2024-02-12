package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;

public abstract class ReturnAdmCaseFromOrganSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.RETURN_ADM_CASE_FROM_ORGAN.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof AdmCase) {
            apply((AdmCase) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.RETURN_ADM_CASE_FROM_ORGAN, AdmCase.class, data);
        }
    }

    public abstract void apply(AdmCase admCase);

}
