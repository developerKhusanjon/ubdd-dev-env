package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;

@Data
@NoArgsConstructor
public class UbddDataToProtocolBindInternalDTO implements UbddDataBind {

    private Long ubddDrivingLicenseDataId;
    private Long ubddTexPassDataId;
    private Long ubddTintingDataId;
    private Long ubddInsuranceDataId;
    private Long vehicleArrestId;
    private Long ubddAttorneyLetterDataId;
    private Long ubddVehicleInspectionDataId;

    public UbddDataToProtocolBindInternalDTO(UbddDataToProtocolBind bind) {

        this.ubddDrivingLicenseDataId = bind.getUbddDrivingLicenseDataId();
        this.ubddTexPassDataId = bind.getUbddTexPassDataId();
        this.ubddTintingDataId = bind.getUbddTintingDataId();
        this.ubddInsuranceDataId = bind.getUbddInsuranceDataId();
        this.vehicleArrestId = bind.getVehicleArrestId();
        this.ubddAttorneyLetterDataId = bind.getUbddAttorneyLetterDataId();
        this.ubddVehicleInspectionDataId = bind.getUbddVehicleInspectionDataId();
    }

    public static UbddDataToProtocolBindInternalDTO ofTexPass(UbddTexPassData texPassData) {
        UbddDataToProtocolBindInternalDTO r = new UbddDataToProtocolBindInternalDTO();
        r.ubddTexPassDataId = texPassData.getId();
        return r;
    }
}
