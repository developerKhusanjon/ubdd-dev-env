package uz.ciasev.ubdd_service.mvd_core.api.court.exception;

import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;

public class CourtActionRoutError extends CourtActionError {

    public CourtActionRoutError(CourtStatusAlias from, CourtStatusAlias to, String message) {
        super(String.format(
//                "Ошибка при обработке переходя из статуса '%s' в статус '%s': %s",
                "Error in processing transition from status '%s' to status '%s': %s",
                from.getValue(),
                to.getValue(),
                message
        ));
    }
}
