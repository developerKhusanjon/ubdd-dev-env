package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataToProtocolBindInternalDTO;
import uz.ciasev.ubdd_service.entity.AdmProtocol;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDGroup;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolDates;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.protocol.ProtocolCreateRequest;
import uz.ciasev.ubdd_service.utils.validator.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ValidProtocol
@ConsistRegionDistrict(message = ErrorCode.PROTOCOL_REGION_AND_DISTRICT_NOT_CONSIST)
public class ProtocolRequestDTO extends QualificationRequestDTO implements RegionDistrictRequest, ProtocolDates, AdmProtocol {

    private Long inspectorRegionId;

    private Long inspectorDistrictId;

    @NotNull(message = "INSPECTOR_POSITION_ID_REQUIRED")
    private Long inspectorPositionId;

    @NotNull(message = "INSPECTOR_RANK_ID_REQUIRED")
    private Long inspectorRankId;

    @NotNull(message = "INSPECTOR_FIO_REQUIRED")
    private String inspectorFio;

    @NotNull(message = "INSPECTOR_WORK_CERTIFICATE_REQUIRED")
    private String inspectorWorkCertificate;

    @NotNull(message = "REGISTRATION_TIME_REQUIRED")
    private LocalDateTime registrationTime;

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    @ActiveOnly(message = ErrorCode.REGION_DEACTIVATED)
    @JsonProperty(value = "regionId")
    private Region region;

    @NotNull(message = ErrorCode.DISTRICT_REQUIRED)
    @ActiveOnly(message = ErrorCode.DISTRICT_DEACTIVATED)
    @JsonProperty(value = "districtId")
    private District district;

    @ActiveOnly(message = ErrorCode.MTP_DEACTIVATED)
    @JsonProperty(value = "mtpId")
    private Mtp mtp;

    @Size(max = 256, message = ErrorCode.PROTOCOL_ADDRESS_MAX_SIZE)
    private String address;

    @Valid
    @NotNull(message = ErrorCode.VIOLATOR_REQUIRED)
    private ViolatorCreateRequestDTO violator;

    @NotNull(message = ErrorCode.VIOLATION_TIME_REQUIRED)
    private LocalDateTime violationTime;

    private Boolean isFamiliarize;

    @NotNull(message = ErrorCode.IS_AGREE_REQUIRED)
    private Boolean isAgree;

    @Size(max = 4000, message = ErrorCode.EXPLANATORY_TEXT_MIN_MAX_LENGTH)
    private String explanatoryText;

    @Min(value = 1, message = ErrorCode.DAMAGE_AMOUNT_IS_ZERO)
    private Long governmentDamageAmount;

    private String inspectorSignature;

    private Double latitude;

    private Double longitude;

    @ValidFileUri(message = ErrorCode.AUDIO_URI_INVALID)
    private String audioUri;

    @ValidFileUri(message = ErrorCode.VIDEO_URI_INVALID)
    private String videoUri;

    @Valid
    private ProtocolRequestAdditionalDTO additional;

    private Boolean isTablet;

    private String externalId;

    @ActiveOnly(message = ErrorCode.UBDD_GROUP_DEACTIVATED)
    @JsonProperty(value = "ubddGroupId")
    private UBDDGroup ubddGroup;

    @NotBlankOrNull(message = ErrorCode.VEHICLE_NUMBER_MUST_NOT_BE_EMPTY)
    @Size(max = 20, message = ErrorCode.VEHICLE_NUMBER_MIN_MAX_SIZE)
    private String vehicleNumber;

    private UbddDataToProtocolBindInternalDTO ubddDataBind;

    public ProtocolCreateRequest buildProtocol() {
        ProtocolCreateRequest protocol = super.buildProtocol();
        protocol.setRegion(this.region);
        protocol.setDistrict(this.district);
        protocol.setMtp(this.mtp);
        protocol.setRegistrationTime(Optional.ofNullable(this.registrationTime).orElseGet(LocalDateTime::now));
        protocol.setViolationTime(this.violationTime);
        protocol.setFamiliarize(this.isFamiliarize);
        protocol.setAgree(this.isAgree);
        protocol.setExplanatory(getExplanatoryText());
        protocol.setLatitude(this.latitude);
        protocol.setLongitude(this.longitude);
        protocol.setAudioUri(this.audioUri);
        protocol.setVideoUri(this.videoUri);
        protocol.setInspectorSignature(this.inspectorSignature);
        protocol.setIsTablet(this.isTablet);
        protocol.setExternalId(this.externalId);
        protocol.setUbddGroup(this.ubddGroup);
        protocol.setVehicleNumber(this.vehicleNumber);

        if (this.inspectorRegionId != null)
            protocol.setInspectorRegion(new Region(this.inspectorRegionId));
        if (this.inspectorDistrictId != null)
            protocol.setInspectorDistrict(new District(this.inspectorDistrictId));

        protocol.setInspectorPosition(new Position(this.inspectorPositionId));
        protocol.setInspectorRank(new Rank(this.inspectorRankId));

        protocol.setInspectorFio(this.inspectorFio);
        protocol.setInspectorWorkCertificate(this.inspectorWorkCertificate);

        return protocol;
    }


    public String getExplanatoryText() {
        return Optional.ofNullable(explanatoryText).map(String::trim).orElse("");
    }
}
