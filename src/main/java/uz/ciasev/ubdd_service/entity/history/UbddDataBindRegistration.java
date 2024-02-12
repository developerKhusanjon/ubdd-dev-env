package uz.ciasev.ubdd_service.entity.history;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "h_ubdd_data_bind_registration")
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UbddDataBindRegistration extends Registration {

    private Long protocolId;
    private Long fromUbddDrivingLicenseDataId;
    private Long fromUbddTexPassDataId;
    private Long fromUbddTintingDataId;
    private Long fromUbddInsuranceDataId;
    private Long fromVehicleArrestId;
    private Long fromUbddAttorneyLetterDataId;

    private Long toUbddDrivingLicenseDataId;
    private Long toUbddTexPassDataId;
    private Long toUbddTintingDataId;
    private Long toUbddInsuranceDataId;
    private Long toVehicleArrestId;
    private Long toUbddAttorneyLetterDataId;
}
