package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractAliasedDict;

@Getter
@Service
@RequiredArgsConstructor
public abstract class SimpleAliasedDictionaryServiceAbstract<T extends AbstractAliasedDict<A>, A extends Enum<A>>
        extends AbstractAliasedDictionaryCRUDService<T, A, EmiDictCreateRequestDTO<T>, EmiDictUpdateRequestDTO<T>>
        implements SimpleAliasedDictionaryService<T, A> {

    private final TypeReference<EmiDictCreateRequestDTO<T>> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<EmiDictUpdateRequestDTO<T>> updateRequestDTOClass = new TypeReference<>(){};


}
