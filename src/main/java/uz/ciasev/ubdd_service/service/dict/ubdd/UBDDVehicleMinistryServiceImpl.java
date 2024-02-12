package uz.ciasev.ubdd_service.service.dict.ubdd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleMinistry;
import uz.ciasev.ubdd_service.repository.dict.ubdd.UBDDVehicleMinistryRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class UBDDVehicleMinistryServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<UBDDVehicleMinistry>
        implements UBDDVehicleMinistryService {

    private final String subPath = "ubdd-vehicle-ministry";

    private final UBDDVehicleMinistryRepository repository;
    private final Class<UBDDVehicleMinistry> entityClass = UBDDVehicleMinistry.class;

}
