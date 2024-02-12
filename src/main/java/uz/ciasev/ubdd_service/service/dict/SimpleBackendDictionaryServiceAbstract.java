package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.BackendDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;

import javax.annotation.PostConstruct;

@Getter
@Service
@RequiredArgsConstructor
public abstract class SimpleBackendDictionaryServiceAbstract<T extends AbstractBackendDict<A>, A extends Enum<A> & BackendAlias>
        extends AbstractAliasedDictionaryListService<T, A>
        implements SimpleBackendDictionaryService<T, A> {

    private final TypeReference<BackendDictUpdateRequestDTO<T>> updateRequestDTOClass = new TypeReference<>(){};

    protected DictionaryHelperForUpdate<T, BackendDictUpdateRequestDTO<T>> updateDictHelper;

    @Setter
    @Autowired
    protected DictionaryHelperFactory factory;

    @PostConstruct
    public void init() {
        this.updateDictHelper = factory.constructHelperForUpdate(this);
    }

    @Override
    public T update(Long id, BackendDictUpdateRequestDTO<T> request) {
        return updateDictHelper.update(id, request);
    }
}
