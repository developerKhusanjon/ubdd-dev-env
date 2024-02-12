package uz.ciasev.ubdd_service.event.subscribers;

import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;


public abstract class OrganResolutionCreateSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.ORGAN_RESOLUTION_CREATE.equals(type);
    }

    @Override
    public void accept(Object data) {
//        if (!(data instanceof Pair)) {
//            throw new AdmEventUnexpectedDataTypeError(AdmEventType.ORGAN_RESOLUTION_CREATE, Pair.class, data);
//        }
//
//        Pair pairTypedData = (Pair) data;
//
//        Object first = pairTypedData.getFirst();
//        Object second = pairTypedData.getSecond();
//
//        if (!(first instanceof Decision && second instanceof List)) {
//            throw new AdmEventUnexpectedDataTypeOfPairError(AdmEventType.ORGAN_RESOLUTION_CREATE, Decision.class, List.class, first, second);
//        }
//
//        List<Compensation> secondTypedList;
//        try {
//            secondTypedList = (List<Compensation>) second;
//        } catch (Exception e) {
//            throw new AdmEventUnexpectedDataTypeError(AdmEventType.ORGAN_RESOLUTION_CREATE, e);
//        }
//
//        apply((Decision) first, secondTypedList);


        if (!(data instanceof CreatedResolutionDTO)) {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.ORGAN_RESOLUTION_CREATE, CreatedResolutionDTO.class, data);
        }

        CreatedResolutionDTO typedData = (CreatedResolutionDTO) data;

        apply(typedData);

    }

    public abstract void apply(CreatedResolutionDTO createdResolution);
}
