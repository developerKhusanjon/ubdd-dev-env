package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

import java.time.LocalDateTime;

@Data
public class ProtocolListResponseDTO {

    private final Long id;
    private final String series;
    private final String number;
    private final String inspectorInfo;
    private final LocalDateTime violationTime;
    private final LocalDateTime registrationTime;
    private final Long organId;
    private final Long departmentId;
    private final Long regionId;
    private final Long districtId;
    private final Long mtpId;
    private final Long userId;
    private final Long articleId;
    private final Long articlePartId;
    private final Long articleViolationTypeId;
    private final Boolean isJuridic;
    private final boolean isAgree;
    private final boolean isFamiliarize;
    private final boolean isDeleted;
    private final boolean isArchived;
    private final boolean isMain;

    public ProtocolListResponseDTO(Protocol protocol) {
        this.id = protocol.getId();
        this.series = protocol.getSeries();
        this.number = protocol.getNumber();
        this.inspectorInfo = protocol.getInspectorInfo();
        this.violationTime = protocol.getViolationTime();
        this.registrationTime = protocol.getRegistrationTime();
        this.organId = protocol.getOrganId();
        this.departmentId = protocol.getDepartmentId();
        this.regionId = protocol.getRegionId();
        this.districtId = protocol.getDistrictId();
        this.mtpId = protocol.getMtpId();
        this.userId = protocol.getUserId();
        this.articleId = protocol.getArticleId();
        this.articlePartId = protocol.getArticlePartId();
        this.articleViolationTypeId = protocol.getArticleViolationTypeId();
        this.isJuridic = protocol.getIsJuridic();
        this.isAgree = protocol.isAgree();
        this.isFamiliarize = protocol.isFamiliarize();
        this.isDeleted = protocol.isDeleted();
        this.isArchived = protocol.isArchived();
        this.isMain = protocol.isMain();
    }
}
