package uz.ciasev.ubdd_service.exception.court;

import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;

public class CourtStatusAndActionNotConsistentException extends CourtGeneralException {

    public CourtStatusAndActionNotConsistentException(CourtActionName actionName, CourtStatusAlias needStatusAlias, CourtStatusAlias presentStatusAlias) {
        super("Действие " + actionName.toString() + " предпологает статус " + needStatusAlias.getValue() + ". Передан статус " + presentStatusAlias.getValue());
    }
}
