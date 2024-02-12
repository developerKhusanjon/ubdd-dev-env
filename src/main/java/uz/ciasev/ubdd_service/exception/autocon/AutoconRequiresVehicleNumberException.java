package uz.ciasev.ubdd_service.exception.autocon;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class AutoconRequiresVehicleNumberException extends ApplicationException {
    public AutoconRequiresVehicleNumberException(String vehicleNumber) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.AUTOCON_REQUIRES_VEHICLE_NUMBER_ERROR,
                String.format("For send penalty to Autocon system need vehicle number, but violator main protocol has vehicleNumber=%s", vehicleNumber)
        );
    }
}
