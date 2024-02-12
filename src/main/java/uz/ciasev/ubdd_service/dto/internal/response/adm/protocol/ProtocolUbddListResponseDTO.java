package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddListView;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProtocolUbddListResponseDTO {

    private final Long id;
    private final Long admCaseId;
    private final Long statusId;
    private final String oldSeries;
    private final String oldNumber;
    private final LocalDateTime registrationTime;
    private final String series;
    private final String number;
    private final LocalDateTime violationTime;
    private final Long articleId;
    private final Long articlePartId;
    private final Long articleViolationTypeId;

    private final String vehicleNumber;
    private final String vehicleColor;
    private final String vehicleBrand;

    private final Long violatorId;
    private final LocalFileUrl violatorPhotoUrl;
    private final String violatorFirstName;
    private final String violatorSecondName;
    private final String violatorLastName;
    private final String violatorPinpp;
    private final Boolean violatorIsRealPinpp;
    private final LocalDate violatorBirthDate;
    private final Long violatorAddressCountryId;
    private final Long violatorAddressRegionId;
    private final Long violatorAddressDistrictId;
    private final String violatorAddress;
    private final String documentSeries;
    private final String documentNumber;
    private final String violatorMobile;


    private final Long registeredOrganId;
    private final Long registeredDepartmentId;
    private final Long registeredDistrictId;
    private final Long registeredRegionId;
    private final Long registeredUserId;
    private final String registeredUserFio;

    private final Long consideredOrganId;
    private final Long consideredDepartmentId;
    private final Long consideredDistrictId;
    private final Long consideredRegionId;
    private final Long consideredUserId;
    private final String consideredUserFio;

    private Boolean isMain;
    private Boolean isDeleted;
    private Boolean isTablet;
    private Boolean isRaid;
    private Boolean isPaper;

    public ProtocolUbddListResponseDTO(ProtocolUbddListView protocol) {
        this.id = protocol.getId();
        this.admCaseId = protocol.getAdmCaseId();
        this.statusId = protocol.getStatusId();
        this.oldSeries = protocol.getOldSeries();
        this.oldNumber = protocol.getOldNumber();
        this.registrationTime = protocol.getRegistrationTime();
        this.series = protocol.getSeries();
        this.number = protocol.getNumber();
        this.violationTime = protocol.getViolationTime();
        this.articleId = protocol.getArticleId();
        this.articlePartId = protocol.getArticlePartId();
        this.articleViolationTypeId = protocol.getArticleViolationTypeId();
        this.vehicleNumber = protocol.getVehicleNumber();
        this.vehicleColor = protocol.getVehicleColor();
        this.vehicleBrand = protocol.getVehicleBrand();
        this.violatorId = protocol.getViolatorId();
        this.violatorPhotoUrl = LocalFileUrl.ofNullable(protocol.getViolatorPhotoUri());
        this.violatorFirstName = protocol.getViolatorFirstName();
        this.violatorSecondName = protocol.getViolatorSecondName();
        this.violatorLastName = protocol.getViolatorLastName();
        this.violatorPinpp = protocol.getViolatorPinpp();
        this.violatorIsRealPinpp = protocol.getViolatorIsRealPinpp();
        this.violatorBirthDate = protocol.getViolatorBirthDate();
        this.violatorAddressCountryId = protocol.getViolatorAddressCountryId();
        this.violatorAddressRegionId = protocol.getViolatorAddressRegionId();
        this.violatorAddressDistrictId = protocol.getViolatorAddressDistrictId();
        this.violatorAddress = protocol.getViolatorAddress();
        this.documentSeries = protocol.getViolatorDocumentSeries();
        this.documentNumber = protocol.getViolatorDocumentNumber();
        this.violatorMobile = protocol.getViolatorMobile();
        this.registeredOrganId = protocol.getRegisteredOrganId();
        this.registeredDepartmentId = protocol.getRegisteredDepartmentId();
        this.registeredDistrictId = protocol.getRegisteredDistrictId();
        this.registeredRegionId = protocol.getRegisteredRegionId();
        this.registeredUserId = protocol.getRegisteredUserId();
        this.registeredUserFio = protocol.getRegisteredUserFio();
        this.consideredOrganId = protocol.getConsideredOrganId();
        this.consideredDepartmentId = protocol.getConsideredDepartmentId();
        this.consideredDistrictId = protocol.getConsideredDistrictId();
        this.consideredRegionId = protocol.getConsideredRegionId();
        this.consideredUserId = protocol.getConsideredUserId();
        this.consideredUserFio = protocol.getConsideredUserFio();
        this.isMain = protocol.getIsMain();
        this.isDeleted = protocol.getIsDeleted();
        this.isTablet = protocol.getIsTablet();
        this.isRaid = protocol.getIsRaid();
        this.isPaper = protocol.getIsPaper();
    }

}
