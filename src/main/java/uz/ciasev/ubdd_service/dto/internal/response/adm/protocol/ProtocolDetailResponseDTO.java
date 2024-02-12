package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.JuridicDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataToProtocolBindInternalDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProtocolDetailResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final Long inspectorRegionId;
    private final Long inspectorDistrictId;
    private final Long inspectorPositionId;
    private final Long inspectorRankId;
    private final String inspectorFio;
    private final String inspectorWorkCertificate;
    private final String inspectorInfo;
    private final String inspectorSignature;
    private final String series;
    private final String number;
    private final String oldSeries;
    private final String oldNumber;
    private final String trackNumber;
    private final LocalDateTime registrationTime;
    private final Long organId;
    private final Long departmentId;
    private final Long regionId;
    private final Long districtId;
    private final Long mtpId;
    private final String address;
    private final LocalDateTime violationTime;
    private final Long articleId;
    private final Long articlePartId;
    private final Long articleViolationTypeId;
    private final Long violatorDetailId;
    private final Boolean isJuridic;
    private final Long juridicId;
    private final boolean isAgree;
    private final boolean isFamiliarize;
    private final String explanatory;
    private final String fabula;
    private final String fabulaAddition;
    private final boolean isDeleted;
    private final boolean isArchived;
    private final boolean isMain;
    private final Double latitude;
    private final Double longitude;
    private final LocalFileUrl audioUrl;
    private final LocalFileUrl videoUrl;

    private final AdmCaseResponseDTO admCase;
    private final ViolatorDetailResponseDTO violatorDetail;
    private final JuridicDetailResponseDTO juridic;
    private final List<ArticleResponseDTO> additionArticles;
    private final List<RepeatabilityResponseDTO> repeatability;

    private final ProtocolStatisticDataResponseDTO statistic;
    private final ProtocolUbddDataResponseUbddDTO ubdd;
    private final ProtocolUbddDataResponseTransportDTO transport;

    private final Boolean isTablet;
    private final Boolean isRaid;
    private final Boolean isPaper;

    private final String externalId;

    private final Long ubddGroupId;

    private final String vehicleNumber;

    private final UbddDataToProtocolBindInternalDTO ubddDataBind;

    private final List<String> permittedActions;

    public ProtocolDetailResponseDTO(Protocol protocol,
                                     AdmCaseResponseDTO admCase,
                                     ViolatorDetailResponseDTO violatorDetail,
                                     JuridicDetailResponseDTO juridic,
                                     List<ArticleResponseDTO> additionArticles,
                                     List<RepeatabilityResponseDTO> repeatability,
                                     ProtocolStatisticDataResponseDTO statistic,
                                     ProtocolUbddDataResponseUbddDTO ubdd,
                                     ProtocolUbddDataResponseTransportDTO transport,
                                     UbddDataToProtocolBindInternalDTO ubddDataBind,
                                     List<ActionAlias> permittedActions) {
        this.id = protocol.getId();
        this.createdTime = protocol.getCreatedTime();
        this.editedTime = protocol.getEditedTime();
        this.userId = protocol.getUserId();
        this.inspectorRegionId = protocol.getInspectorRegionId();
        this.inspectorDistrictId = protocol.getInspectorDistrictId();
        this.inspectorPositionId = protocol.getInspectorPositionId();
        this.inspectorRankId = protocol.getInspectorRankId();
        this.inspectorFio = protocol.getInspectorFio();
        this.inspectorWorkCertificate = protocol.getInspectorWorkCertificate();
        this.series = protocol.getSeries();
        this.number = protocol.getNumber();
        this.oldSeries = protocol.getOldSeries();
        this.oldNumber = protocol.getOldNumber();
        this.trackNumber = protocol.getTrackNumber();
        this.registrationTime = protocol.getRegistrationTime();
        this.inspectorInfo = protocol.getInspectorInfo();
        this.inspectorSignature = protocol.getInspectorSignature();
        this.organId = protocol.getOrganId();
        this.departmentId = protocol.getDepartmentId();
        this.regionId = protocol.getRegionId();
        this.districtId = protocol.getDistrictId();
        this.mtpId = protocol.getMtpId();
        this.address = protocol.getAddress();
        this.violationTime = protocol.getViolationTime();
        this.articleId = protocol.getArticleId();
        this.articlePartId = protocol.getArticlePartId();
        this.articleViolationTypeId = protocol.getArticleViolationTypeId();
        this.violatorDetailId = protocol.getViolatorDetailId();
        this.isJuridic = protocol.getIsJuridic();
        this.juridicId = protocol.getJuridicId();
        this.isAgree = protocol.isAgree();
        this.isFamiliarize = protocol.isFamiliarize();
        this.explanatory = protocol.getExplanatory();
        this.fabula = protocol.getFabula();
        this.fabulaAddition = protocol.getFabulaAdditional();
        this.isDeleted = protocol.isDeleted();
        this.isArchived = protocol.isArchived();
        this.isMain = protocol.isMain();
        this.latitude = protocol.getLatitude();
        this.longitude = protocol.getLongitude();
        this.videoUrl = LocalFileUrl.ofNullable(protocol.getVideoUri());
        this.audioUrl = LocalFileUrl.ofNullable(protocol.getAudioUri());

        this.admCase = admCase;
        this.violatorDetail = violatorDetail;
        this.juridic = juridic;
        this.additionArticles = additionArticles;
        this.repeatability = repeatability;

        this.statistic = statistic;
        this.ubdd = ubdd;
        this.transport = transport;

        this.isTablet = protocol.getIsTablet();
        this.isRaid = protocol.getIsRaid();
        this.isPaper = protocol.getIsPaper();

        this.externalId = protocol.getExternalId();

        this.ubddGroupId = protocol.getUbddGroupId();

        this.vehicleNumber = protocol.getVehicleNumber();

        this.ubddDataBind = ubddDataBind;

        this.permittedActions = permittedActions.stream().map(ActionAlias::name).collect(Collectors.toList());
    }
}
