package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action;

import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;

import java.util.Collection;

public interface CourtAction {

    MaterialActionContext apply(MaterialActionContext context);

    CourtActionName getName();

    Collection<CourtActionName> getConflicts();
}
