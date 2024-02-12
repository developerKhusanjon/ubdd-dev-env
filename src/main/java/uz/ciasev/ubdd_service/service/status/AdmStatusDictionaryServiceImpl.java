package uz.ciasev.ubdd_service.service.status;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.dict.AdmStatusResponseDTO;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.exception.notfound.EntityByAliasNotPresent;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.status.AdmStatusRepository;

import java.util.EnumMap;

@Service
@RequiredArgsConstructor
public class AdmStatusDictionaryServiceImpl implements AdmStatusDictionaryService {

    private final AdmStatusRepository admStatusRepository;
    private final EnumMap<AdmStatusAlias, AdmStatus> cache = new EnumMap<>(AdmStatusAlias.class);

    @Override
    public AdmStatus getById(Long id) {
        return admStatusRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(AdmStatus.class, id));
    }

    @Override
    public AdmStatus findByAlias(AdmStatusAlias admStatus) {
        AdmStatus cachedValue = cache.get(admStatus);
        if (cachedValue == null) {
            cachedValue = admStatusRepository
                    .findByAlias(admStatus)
                    .orElseThrow(() -> new EntityByAliasNotPresent(AdmStatus.class, admStatus.name()));

            cache.put(admStatus, cachedValue);
        }

        return cachedValue;

//        return admStatusRepository
//                .getByAlias(admStatus)
//                .orElseThrow(() -> new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.ADM_STATUS_MISSING));
    }

    @Override
    public Page<AdmStatusResponseDTO> findAll(Pageable pageable) {
        return admStatusRepository
                .findAll(pageable)
                .map(AdmStatusResponseDTO::new);
    }

}
