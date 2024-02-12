package uz.ciasev.ubdd_service.service.ubdd_data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberType;
import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberTypeAlias;
import uz.ciasev.ubdd_service.service.dict.ubdd.VehicleNumberTypeService;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleNumberTypeCalculationServiceImpl implements VehicleNumberTypeCalculationService {

    private final VehicleNumberTypeService service;

    @Override
    public Optional<VehicleNumberType> calculateForNumber(@Nullable String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isBlank()) {
            return Optional.empty();
        }

        if (vehicleNumber.equals("111111")) {
            return Optional.ofNullable(service.getByAlias(VehicleNumberTypeAlias.JURIDIC));
        }

        return Optional.ofNullable(service.getByAlias(VehicleNumberTypeAlias.CITIZEN));
    }
}
