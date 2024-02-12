package uz.ciasev.ubdd_service.entity.dict.person;

import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

public enum CitizenshipTypeAlias implements BackendAlias {
    UZBEK,
    FOREIGN,
    STATELESS,
    UNKNOWN;

    @Override
    public long getId() {
        throw new NotImplementedException("BackendAlias for ArticleParticipantTypeAlias");
    }
}
