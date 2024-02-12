package uz.ciasev.ubdd_service.event.subscribers;

import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeError;
import uz.ciasev.ubdd_service.exception.event.AdmEventUnexpectedDataTypeOfPairError;

public abstract class PunishmentStatusChangeSubscriber implements AdmEventSubscriber {

    @Override
    public boolean canAccept(AdmEventType type, Object data) {
        return AdmEventType.PUNISHMENT_STATUS_CHANGE.equals(type);
    }

    @Override
    public void accept(Object data) {
        if (!(data instanceof Pair)) {
            throw new AdmEventUnexpectedDataTypeError(AdmEventType.PUNISHMENT_STATUS_CHANGE, Pair.class, data);
        }

        Pair pairTypedData = (Pair) data;

        Object first = pairTypedData.getFirst();
        Object second = pairTypedData.getSecond();

        if (first instanceof Punishment && second instanceof ExecutorType) {
            apply((Punishment) first, (ExecutorType) second);
        } else {
            throw new AdmEventUnexpectedDataTypeOfPairError(AdmEventType.PUNISHMENT_STATUS_CHANGE, Punishment.class, ExecutorType.class, first, second);
        }
    }

    public abstract void apply(Punishment punishment, ExecutorType executorType);
}
