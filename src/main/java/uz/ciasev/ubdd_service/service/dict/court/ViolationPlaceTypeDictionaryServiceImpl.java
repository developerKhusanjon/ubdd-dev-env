package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationPlaceType;
import uz.ciasev.ubdd_service.repository.dict.court.ViolationPlaceTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class ViolationPlaceTypeDictionaryServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<ViolationPlaceType>
        implements ViolationPlaceTypeDictionaryService {

    private final String subPath = "violation-place-type";

    private final ViolationPlaceTypeRepository repository;
    private final Class<ViolationPlaceType> entityClass = ViolationPlaceType.class;
}
