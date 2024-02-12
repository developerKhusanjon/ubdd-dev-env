package uz.ciasev.ubdd_service.service.dict.ubdd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleColorType;
import uz.ciasev.ubdd_service.repository.dict.ubdd.UBDDVehicleColorTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class UBDDVehicleColorTypeServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<UBDDVehicleColorType>
        implements UBDDVehicleColorTypeService {

    private final String subPath = "ubdd-vehicle-color-type";

    private final UBDDVehicleColorTypeRepository repository;
    private final Class<UBDDVehicleColorType> entityClass = UBDDVehicleColorType.class;

}
