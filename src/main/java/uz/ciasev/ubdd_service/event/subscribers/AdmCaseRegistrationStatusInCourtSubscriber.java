package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;


public abstract class AdmCaseRegistrationStatusInCourtSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.ADM_CASE_REGISTRATION_STATUS_IN_COURT.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (data instanceof CourtCaseChancelleryData) {
            apply((CourtCaseChancelleryData) data);
        } else {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.ADM_CASE_REGISTRATION_STATUS_IN_COURT, CourtCaseChancelleryData.class, data);
        }
    }

    public abstract void apply(CourtCaseChancelleryData courtCaseFields);
}
