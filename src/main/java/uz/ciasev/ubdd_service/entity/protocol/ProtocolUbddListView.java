package uz.ciasev.ubdd_service.entity.protocol;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "v_protocol_ubdd_list")
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolUbddListView {

    @Id
    private Long id;
    private Long admCaseId;
    private Long statusId;
    private String oldSeries;
    private String oldNumber;
    private LocalDateTime registrationTime;
    private String series;
    private String number;
    private LocalDateTime violationTime;
    private Long articleId;
    private Long articlePartId;
    private Long articleViolationTypeId;

    private String vehicleNumber;
    private String vehicleColor;
    private String vehicleBrand;

    private Long violatorId;
    private String violatorPhotoUri;
    private String violatorFirstName;
    private String violatorSecondName;
    private String violatorLastName;
    private String violatorPinpp;
    private Boolean violatorIsRealPinpp;
    private LocalDate violatorBirthDate;
    private Long violatorAddressCountryId;
    private Long violatorAddressRegionId;
    private Long violatorAddressDistrictId;
    private String violatorAddress;
    private String violatorAddressFullText;
    private String violatorDocumentSeries;
    private String violatorDocumentNumber;
    private String violatorMobile;


    private Long registeredOrganId;
    private Long registeredDepartmentId;
    private Long registeredDistrictId;
    private Long registeredRegionId;
    private Long registeredUserId;
    private String registeredUserFio;

    private Long consideredOrganId;
    private Long consideredDepartmentId;
    private Long consideredDistrictId;
    private Long consideredRegionId;
    private Long consideredUserId;
    private String consideredUserFio;

    private Boolean isMain;
    private Boolean isDeleted;
    private Boolean isTablet;
    private Boolean isRaid;
    private Boolean isPaper;
}
