package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

public interface UbddDataBind {

    Long getUbddDrivingLicenseDataId();
    Long getUbddTexPassDataId();
    Long getUbddTintingDataId();
    Long getUbddInsuranceDataId();
    Long getVehicleArrestId();
    Long getUbddAttorneyLetterDataId();
    Long getUbddVehicleInspectionDataId();

    default boolean isOld() {
        return false;
    }
}
