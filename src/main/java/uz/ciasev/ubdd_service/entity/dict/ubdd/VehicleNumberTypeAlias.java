package uz.ciasev.ubdd_service.entity.dict.ubdd;

import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

public enum VehicleNumberTypeAlias implements BackendAlias {
    JURIDIC,
    CITIZEN,
    DIPLOMATIC;

    @Override
    public long getId() {
        throw new NotImplementedException("BackendAlias");
    }
}
