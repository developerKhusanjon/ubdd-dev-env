package uz.ciasev.ubdd_service.repository.dict;

import org.springframework.data.repository.NoRepositoryBean;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

import java.util.Optional;

@NoRepositoryBean
public interface AbstractAliasedDictRepository<T extends AbstractDict, A> extends AbstractDictRepository<T> {

    Optional<T> findByAlias(A alias);
}
