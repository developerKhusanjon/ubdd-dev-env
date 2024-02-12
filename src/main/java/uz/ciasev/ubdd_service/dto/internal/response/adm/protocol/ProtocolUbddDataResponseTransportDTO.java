package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.old_structure.ProtocolUbddDataView;

import java.time.LocalDate;


// TODO До устарения апи старой убдд структур
@Data
@EqualsAndHashCode(callSuper=true)
public class ProtocolUbddDataResponseTransportDTO extends ProtocolUbddDataResponseDTO {

    private String vehicleYear;
    private LocalDate vehicleOwnerBirthdate;
    private String vehicleOwnerPass;
    private AddressResponseDTO vehicleOwnerAddress;
    private String vehicleOwnerLastName;
    private String vehicleOwnerFirstName;
    private String vehicleOwnerSecondName;

    public ProtocolUbddDataResponseTransportDTO(ProtocolUbddDataView data, AddressResponseDTO vehicleOwnerAddress) {
        super(data);
        this.vehicleYear = data.getVehicleYear();
        this.vehicleOwnerBirthdate = data.getVehicleOwnerBirthdate();
        this.vehicleOwnerPass = data.getVehicleOwnerPass();
        this.vehicleOwnerAddress = vehicleOwnerAddress;
        this.vehicleOwnerLastName = data.getVehicleOwnerLastName();
        this.vehicleOwnerFirstName = data.getVehicleOwnerFirstName();
        this.vehicleOwnerSecondName = data.getVehicleOwnerSecondName();
    }
}
