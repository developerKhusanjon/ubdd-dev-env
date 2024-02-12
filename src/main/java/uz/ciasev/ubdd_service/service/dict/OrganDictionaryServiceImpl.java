package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.dict.request.OrganRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.response.OrganListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.response.OrganResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.OrganAlias;
import uz.ciasev.ubdd_service.repository.dict.OrganRepository;
import uz.ciasev.ubdd_service.service.settings.OrganInfoAutoCreateService;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class OrganDictionaryServiceImpl extends AbstractAliasedDictionaryCRUDService<Organ, OrganAlias, OrganRequestDTO, OrganRequestDTO> implements OrganDictionaryService {

    private final OrganInfoAutoCreateService organInfoService;

    private final String SubPath = "organs";
    private final TypeReference<OrganRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<OrganRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final OrganRepository repository;
    private final Class<Organ> entityClass = Organ.class;
    private final Class<OrganAlias> aliasClass = OrganAlias.class;


    @Override
    public Object buildListResponseDTO(Organ entity) {
        return new OrganListResponseDTO(entity);
    }

    public Object buildResponseDTO(Organ entity) {
        return new OrganResponseDTO(entity);
    }

    @Override
    @Transactional(timeout = 120)
    public List<Organ> create(List<OrganRequestDTO> request) {
        List<Organ> organs = super.create(request);
        organs.stream().map(organInfoService::createForNew).collect(Collectors.toList());

        return organs;
    }

    @Override
    @Transactional(timeout = 120)
    public Organ create(OrganRequestDTO request) {
        Organ organ = super.create(request);
        organInfoService.createForNew(organ);

        return organ;
    }

    @Override
    public Organ getCourtOrgan() {
        return getByAlias(OrganAlias.COURT);
    }

    @Override
    public Organ getMibOrgan() {
        return getByAlias(OrganAlias.MIB);
    }

    @Override
    public Organ getMvdOrgan() {
        return getByAlias(OrganAlias.MVD);
    }
}
