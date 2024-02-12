package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidAddress;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
//@AllArgsConstructor
public class ProtocolUbddDataRequestTransportDTO extends ProtocolUbddDataRequestDTO implements ProtocolDataDTO<ProtocolUbddData> {


    // Старые поля

    // TODO До устарения апи старой убдд структур
    @Size(max = 4, message = ErrorCode.UBDD_VEHICLE_YEAR_MIN_MAX_LENGTH)
    private String vehicleYear;

    private LocalDate vehicleOwnerBirthdate;

    @Size(max = 50, message = ErrorCode.UBDD_VEHICLE_OWNER_PASS_MIN_MAX_LENGTH)
    private String vehicleOwnerPass;

    @ValidAddress(message = ErrorCode.UBDD_VEHICLE_OWNER_ADDRESS_INVALID)
    private AddressRequestDTO vehicleOwnerAddress;

    @Size(max = 200, message = ErrorCode.UBDD_VEHICLE_OWNER_LAST_NAME_MIN_MAX_LENGTH)
    private String vehicleOwnerLastName;

    @Size(max = 200, message = ErrorCode.UBDD_VEHICLE_OWNER_FIRST_NAME_MIN_MAX_LENGTH)
    private String vehicleOwnerFirstName;

    @Size(max = 200, message = ErrorCode.UBDD_VEHICLE_OWNER_SECOND_NAME_MIN_MAX_LENGTH)
    private String vehicleOwnerSecondName;
    
    @Override
    public ProtocolUbddData build(Protocol protocol) {
        ProtocolUbddData rsl = super.build(protocol);
        return apply(rsl, protocol);
    }

    @Override
    public ProtocolUbddData apply(ProtocolUbddData data, Protocol protocol) {
        ProtocolUbddData rsl = super.apply(data, protocol);
//        rsl.setVehicleYear(this.vehicleYear);
//        rsl.setVehicleOwnerBirthdate(this.vehicleOwnerBirthdate);
//        rsl.setVehicleOwnerPass(this.vehicleOwnerPass);
//        Optional.ofNullable(this.vehicleOwnerAddress).map(AddressRequestDTO::buildAddress).ifPresent(rsl::setVehicleOwnerAddress);
//        rsl.setVehicleOwnerLastName(this.vehicleOwnerLastName);
//        rsl.setVehicleOwnerFirstName(this.vehicleOwnerFirstName);
//        rsl.setVehicleOwnerSecondName(this.vehicleOwnerSecondName);
        return rsl;
    }
}
