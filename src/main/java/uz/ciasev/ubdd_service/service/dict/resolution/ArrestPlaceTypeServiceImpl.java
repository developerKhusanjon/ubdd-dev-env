package uz.ciasev.ubdd_service.service.dict.resolution;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.resolution.ArrestPlaceType;
import uz.ciasev.ubdd_service.repository.dict.resolution.ArrestPlaceTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class ArrestPlaceTypeServiceImpl extends SimpleEmiDictionaryServiceAbstract<ArrestPlaceType>
        implements ArrestPlaceTypeService {

    private final ArrestPlaceTypeRepository repository;

    private final String subPath = "arrest-place-types";
    private final Class<ArrestPlaceType> entityClass = ArrestPlaceType.class;
}
