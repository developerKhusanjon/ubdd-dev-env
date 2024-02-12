package uz.ciasev.ubdd_service.exception.court;

import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;

public class ReviewCaseInStatusException extends CourtGeneralException {

    public ReviewCaseInStatusException(CourtStatus courtStatus) {
        super("Не могу принять пересмотр на дело в статусе " + courtStatus.getAlias().getValue());
    }
}
