package uz.ciasev.ubdd_service.service.ubdd_data;

import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberType;

import javax.annotation.Nullable;
import java.util.Optional;

public interface VehicleNumberTypeCalculationService {

    Optional<VehicleNumberType> calculateForNumber(@Nullable String vehicleNumber);
}
