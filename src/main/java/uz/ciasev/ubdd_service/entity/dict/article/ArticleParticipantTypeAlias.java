package uz.ciasev.ubdd_service.entity.dict.article;

import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

public enum ArticleParticipantTypeAlias implements BackendAlias {
    Person,
    Juridic,
    PersonAndJuridic;

    @Override
    public long getId() {
        throw new NotImplementedException("BackendAlias for ArticleParticipantTypeAlias");
    }
}
