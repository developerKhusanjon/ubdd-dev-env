package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UbddDataToProtocolBindDTO implements UbddDataBind {

    @NotNull(message = ErrorCode.PROTOCOL_ID_REQUIRED)
    private Long protocolId;

    private Long ubddDrivingLicenseDataId;
    private Long ubddTexPassDataId;
    private Long ubddTintingDataId;
    private Long ubddInsuranceDataId;
    private Long vehicleArrestId;
    private Long ubddAttorneyLetterDataId;
    private Long ubddVehicleInspectionDataId;

    public UbddDataToProtocolBindDTO(UbddDataToProtocolBind bind) {

        this.protocolId = bind.getProtocolId();
        this.ubddDrivingLicenseDataId = bind.getUbddDrivingLicenseDataId();
        this.ubddTexPassDataId = bind.getUbddTexPassDataId();
        this.ubddTintingDataId = bind.getUbddTintingDataId();
        this.ubddInsuranceDataId = bind.getUbddInsuranceDataId();
        this.vehicleArrestId = bind.getVehicleArrestId();
        this.ubddAttorneyLetterDataId = bind.getUbddAttorneyLetterDataId();
        this.ubddVehicleInspectionDataId = bind.getUbddVehicleInspectionDataId();
    }
}
