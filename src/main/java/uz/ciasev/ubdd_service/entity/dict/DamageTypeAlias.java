package uz.ciasev.ubdd_service.entity.dict;

import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

public enum DamageTypeAlias implements BackendAlias {
    MORAL,
    MATERIAL,
    PHYSICAL;

    @Override
    public long getId() {
        throw new NotImplementedException("BackendAlias");
    }
}
