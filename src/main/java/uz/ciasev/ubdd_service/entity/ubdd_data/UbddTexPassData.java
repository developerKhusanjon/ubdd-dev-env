package uz.ciasev.ubdd_service.entity.ubdd_data;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;

import javax.persistence.*;

@Entity
@Table(name = "ubdd_tex_pass_data")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class UbddTexPassData extends UbddTexPassDataAbstract {

    public UbddTexPassData(UbddTexPassDTOI dto) {
        apply(dto);
    }

    public void apply(UbddTexPassDTOI dto) {

        // OWNER
        this.vehicleOwnerType = dto.getVehicleOwnerType();
        this.vehicleOwnerInn = dto.getVehicleOwnerInn();
        this.vehicleOwnerOrganizationName = dto.getVehicleOwnerOrganizationName();
        this.vehicleOwnerLastName = dto.getVehicleOwnerLastName();
        this.vehicleOwnerFirstName = dto.getVehicleOwnerFirstName();
        this.vehicleOwnerSecondName = dto.getVehicleOwnerSecondName();
        this.vehicleOwnerSecondNameKir = dto.getVehicleOwnerSecondNameKir();
        this.vehicleOwnerFirstNameKir = dto.getVehicleOwnerFirstNameKir();
        this.vehicleOwnerLastNameKir = dto.getVehicleOwnerLastNameKir();
        this.vehicleOwnerBirthDate = dto.getVehicleOwnerBirthDate();
        this.vehicleOwnerPinpp = dto.getVehicleOwnerPinpp();
//        this.vehicleOwnerAddress = Optional.ofNullable(dto.getVehicleOwnerAddress()).map(AddressRequestDTO::buildAddress).orElse(null);
        this.vehicleOwnerCitizenshipType = dto.getVehicleOwnerCitizenshipType();
        this.vehicleOwnerGender = dto.getVehicleOwnerGender();
//        this.vehicleOwnerBirthAddress = Optional.ofNullable(dto.getVehicleOwnerBirthAddress()).map(AddressRequestDTO::buildAddress).orElse(null);
        this.vehicleOwnerDocumentSeries = dto.getVehicleOwnerDocumentSeries();
        this.vehicleOwnerDocumentNumber = dto.getVehicleOwnerDocumentNumber();
        this.vehicleOwnerDocumentType = dto.getVehicleOwnerDocumentType();
        this.vehicleOwnerDocumentGivenDate = dto.getVehicleOwnerDocumentGivenDate();
        this.vehicleOwnerDocumentExpireDate = dto.getVehicleOwnerDocumentExpireDate();
//        this.vehicleOwnerDocumentGivenAddress = Optional.ofNullable(dto.getVehicleOwnerDocumentGivenAddress()).map(AddressRequestDTO::buildAddress).orElse(null);
//        this.texPassGivenAddress = Optional.ofNullable(dto.getTexPassGivenAddress()).map(AddressRequestDTO::buildAddress).orElse(null);

        this.externalSystem = dto.getExternalSystem();
        this.externalId = dto.getExternalId();

        this.addition = dto.getAddition();
        this.phoneInfo = dto.getPhoneInfo();

        // VEHICLE
        this.vehicleNumber = dto.getVehicleNumber();
        this.vehicleColorType = dto.getVehicleColor();
        this.vehicleColor = dto.getVehicleSubColor();

        this.vehicleBodyType = dto.getVehicleBodyType();

        this.vehicleBrand = dto.getVehicleBrand();
        this.vehicleModel = dto.getVehicleModel();
        this.vehicleEngineSeries = dto.getVehicleEngineSeries();
        this.vehicleEngineHorsePower = dto.getVehicleEngineHorsePower();
        this.vehicleRegistrationDate = dto.getVehicleRegistrationDate();
        this.vehicleChassisSeries = dto.getVehicleChassisSeries();
        this.texPassSeries = dto.getTexPassSeries();
        this.texPassNumber = dto.getTexPassNumber();
        this.vehicleYear = dto.getVehicleYear();
        this.vehicleId = dto.getVehicleId();
        this.vehicleBodySeries = dto.getVehicleBodySeries();
    }
}
