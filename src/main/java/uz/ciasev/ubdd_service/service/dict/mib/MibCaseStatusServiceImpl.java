package uz.ciasev.ubdd_service.service.dict.mib;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.MibCaseStatusCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.MibCaseStatusUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.MibCaseStatusResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.dict.mib.MibCaseStatusRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
public class MibCaseStatusServiceImpl extends AbstractDictionaryCRUDService<MibCaseStatus, MibCaseStatusCreateRequestDTO, MibCaseStatusUpdateRequestDTO>
        implements MibCaseStatusService {

    private final PassiveExpiringMap<Long, String> cache = new PassiveExpiringMap<>(30_000L);

    private final String subPath = "mib-case-statuses";
    private final TypeReference<MibCaseStatusCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<MibCaseStatusUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final Class<MibCaseStatus> entityClass = MibCaseStatus.class;
    private final MibCaseStatusRepository repository;
    private final MibCaseStatusTypeService typeService;

    @Override
    public MibCaseStatus findByCode(String code) {
        return repository.findByCode(code).orElseThrow(() -> new EntityByParamsNotFound(MibCaseStatus.class, "code", code));
    }

    @Override
    public MibCaseStatusResponseDTO buildResponseDTO(MibCaseStatus entity) {
        return new MibCaseStatusResponseDTO(entity, getEntityTypeColor(entity));
    }

    @Override
    public MibCaseStatusResponseDTO buildListResponseDTO(MibCaseStatus entity) {
        return new MibCaseStatusResponseDTO(entity, getEntityTypeColor(entity));
    }

    private String getEntityTypeColor(MibCaseStatus entity) {
        Long typeId = entity.getAlias().getId();

        return Optional.ofNullable(cache.get(typeId)).orElseGet(() -> putColorInCache(typeId));
    }

    private String putColorInCache(Long typeId) {
        String color = typeService.getById(typeId).getColor();
        cache.put(typeId, color);
        return color;
    }
}
