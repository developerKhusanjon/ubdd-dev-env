package uz.ciasev.ubdd_service.service.dict.resolution;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.EmiDictUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellation;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;
import uz.ciasev.ubdd_service.repository.dict.resolution.ReasonCancellationRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractAliasedDictionaryCRUDService;

@Getter
@Service
@RequiredArgsConstructor
public class ReasonCancellationServiceImpl extends AbstractAliasedDictionaryCRUDService<ReasonCancellation, ReasonCancellationAlias, EmiDictCreateRequestDTO<ReasonCancellation>, EmiDictUpdateRequestDTO<ReasonCancellation>> implements ReasonCancellationService {

    private final String SubPath = "reasons-cancellation";
    private final TypeReference<EmiDictCreateRequestDTO<ReasonCancellation>> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<EmiDictUpdateRequestDTO<ReasonCancellation>> updateRequestDTOClass = new TypeReference<>(){};

    private final ReasonCancellationRepository repository;
    private final Class<ReasonCancellation> entityClass = ReasonCancellation.class;
    private final Class<ReasonCancellationAlias> aliasClass = ReasonCancellationAlias.class;
}
