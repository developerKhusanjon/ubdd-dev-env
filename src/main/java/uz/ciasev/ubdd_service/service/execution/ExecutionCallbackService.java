package uz.ciasev.ubdd_service.service.execution;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;

/**
 * Вызывать методы только по факту исполнения.
 * Внутрри нет проверки на допустимость исполнения
 */
public interface ExecutionCallbackService {

    void executeCallback(Decision decision);

    void executeCallbackWithoutLazy(Decision decision);

    void autoExecute(Resolution resolution);
}
