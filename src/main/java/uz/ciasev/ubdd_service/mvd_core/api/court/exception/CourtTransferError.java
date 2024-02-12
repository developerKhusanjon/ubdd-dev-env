package uz.ciasev.ubdd_service.mvd_core.api.court.exception;

import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

public class CourtTransferError extends CourtValidationException {

    public CourtTransferError(Class transClass, Object value) {
        super(String.format(
//                "Не удалось найти переводировочной записи в '%s' для переданного значение '%s'",
                "Transfer data no defined in '%s' for external value '%s'",
                transClass.getSimpleName(),
                value
        ));

    }
}
