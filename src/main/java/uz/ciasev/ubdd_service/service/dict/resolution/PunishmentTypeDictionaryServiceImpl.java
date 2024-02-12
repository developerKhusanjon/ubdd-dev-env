package uz.ciasev.ubdd_service.service.dict.resolution;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.PunishmentTypeRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.resolution.PunishmentTypeResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.resolution.PunishmentTypeRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractAliasedDictionaryListService;
import uz.ciasev.ubdd_service.service.dict.DictionaryHelperFactory;
import uz.ciasev.ubdd_service.service.dict.DictionaryHelperForUpdate;

import javax.annotation.PostConstruct;


@Service
@RequiredArgsConstructor
@Getter
public class PunishmentTypeDictionaryServiceImpl extends AbstractAliasedDictionaryListService<PunishmentType, PunishmentTypeAlias>
        implements PunishmentTypeDictionaryService {

    @Setter
    @Autowired
    protected DictionaryHelperFactory factory;

    @PostConstruct
    public void init() {
        this.updateDictHelper = factory.constructHelperForUpdate(this);
    }

    private final String subPath = "punishment-types";
    private final TypeReference<PunishmentTypeRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final PunishmentTypeRepository repository;
    private final Class<PunishmentType> entityClass = PunishmentType.class;

    protected DictionaryHelperForUpdate<PunishmentType, PunishmentTypeRequestDTO> updateDictHelper;

    @Override
    public PunishmentType update(Long id, PunishmentTypeRequestDTO request) {
        return updateDictHelper.update(id, request);
    }

    @Override
    public PunishmentTypeResponseDTO buildResponseDTO(PunishmentType entity) {
        return new PunishmentTypeResponseDTO(entity);
    }

    @Override
    public Class<PunishmentTypeAlias> getAliasClass() {
        return PunishmentTypeAlias.class;
    }
}
