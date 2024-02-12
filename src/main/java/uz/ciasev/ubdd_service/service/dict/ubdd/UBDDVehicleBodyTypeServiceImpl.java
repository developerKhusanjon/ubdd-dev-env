package uz.ciasev.ubdd_service.service.dict.ubdd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleBodyType;
import uz.ciasev.ubdd_service.repository.dict.ubdd.UBDDVehicleBodyTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class UBDDVehicleBodyTypeServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<UBDDVehicleBodyType>
        implements UBDDVehicleBodyTypeService {

    private final String subPath = "ubdd-vehicle-body-type";

    private final UBDDVehicleBodyTypeRepository repository;
    private final Class<UBDDVehicleBodyType> entityClass = UBDDVehicleBodyType.class;

}
