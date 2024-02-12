package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.BackendStatusDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.response.AliasedStatusDictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendStatusDict;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;

import javax.annotation.PostConstruct;

@Getter
@Service
@RequiredArgsConstructor
public abstract class SimpleBackendStatusDictionaryServiceAbstract<T extends AbstractBackendStatusDict<A>, A extends Enum<A> & BackendAlias>
        extends AbstractAliasedDictionaryListService<T, A>
        implements SimpleBackendStatusDictionaryService<T, A> {

    private final TypeReference<BackendStatusDictUpdateRequestDTO<T>> updateRequestDTOClass = new TypeReference<>(){};

    protected DictionaryHelperForUpdate<T, BackendStatusDictUpdateRequestDTO<T>> updateDictHelper;

    @Setter
    @Autowired
    protected DictionaryHelperFactory factory;

    @PostConstruct
    public void init() {
        this.updateDictHelper = factory.constructHelperForUpdate(this);
    }

    @Override
    public T update(Long id, BackendStatusDictUpdateRequestDTO<T> request) {
        return updateDictHelper.update(id, request);
    }

    @Override
    public AliasedStatusDictResponseDTO buildListResponseDTO(T entity) {
        return new AliasedStatusDictResponseDTO(entity);
    }
}
