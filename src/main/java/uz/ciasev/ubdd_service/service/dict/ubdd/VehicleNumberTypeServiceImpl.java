package uz.ciasev.ubdd_service.service.dict.ubdd;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.VehicleNumberTypeRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd.VehicleNumberTypeResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.ubdd.VehicleNumberTypeRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractAliasedDictionaryListService;
import uz.ciasev.ubdd_service.service.dict.DictionaryHelperFactory;
import uz.ciasev.ubdd_service.service.dict.DictionaryHelperForUpdate;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Getter
public class VehicleNumberTypeServiceImpl extends AbstractAliasedDictionaryListService<VehicleNumberType, VehicleNumberTypeAlias>
        implements VehicleNumberTypeService {

    @Setter
    @Autowired
    protected DictionaryHelperFactory factory;

    @PostConstruct
    public void init() {
        this.updateDictHelper = factory.constructHelperForUpdate(this);
    }

    private final String subPath = "ubdd-vehicle-number-types";
    private final TypeReference<VehicleNumberTypeRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final VehicleNumberTypeRepository repository;
    private final Class<VehicleNumberType> entityClass = VehicleNumberType.class;

    protected DictionaryHelperForUpdate<VehicleNumberType, VehicleNumberTypeRequestDTO> updateDictHelper;

    @Override
    public VehicleNumberType update(Long id, VehicleNumberTypeRequestDTO request) {
        return updateDictHelper.update(id, request);
    }

    @Override
    public VehicleNumberTypeResponseDTO buildResponseDTO(VehicleNumberType entity) {
        return new VehicleNumberTypeResponseDTO(entity);
    }

    @Override
    public Class<VehicleNumberTypeAlias> getAliasClass() {
        return VehicleNumberTypeAlias.class;
    }
}
