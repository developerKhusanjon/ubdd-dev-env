package uz.ciasev.ubdd_service.dto.internal.response.user;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.PersonResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Getter
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserListResponseDTO {

    private final Long id;
    private final PersonResponseDTO person;
    private final String documentSeries;
    private final String documentNumber;
    private final String workCertificate;
    private final String username;
    private final LocalDateTime createdTime;
    private final LocalDateTime closedTime;
    private final Long branchRegionId;
    private final Long organId;
    private final Long departmentId;
    private final Long regionId;
    private final Long districtId;
    private final Long positionId;
    private final Long rankId;
    private final String mobile;
    private final String landline;
    private final Long statusId;
    private final Boolean isActive;
    private final Boolean isOffline;
    private final Boolean isExternal;
    private final Boolean isConsider;
    private final Boolean isHeader;
    private final Boolean isGod;
    private final Boolean isSystemNotificationSubscriber;
    private final LocalFileUrl photoUrl;
    private final Set<RoleResponseDTO> roles;
    private Boolean mustProvideDigitalSignature;

    public UserListResponseDTO(User user,
                               PersonResponseDTO person,
                               Set<RoleResponseDTO> roles) {
        this.id = user.getId();
        this.person = person;
        this.documentSeries = user.getDocumentSeries();
        this.documentNumber = user.getDocumentNumber();
        this.workCertificate = user.getWorkCertificate();
        this.username = user.getUsername();
        this.createdTime = user.getCreatedTime();
        this.closedTime = user.getClosedTime();
        this.mobile = user.getMobile();
        this.landline = user.getLandline();
        this.isActive = user.isActive();
        this.isOffline = user.isOffline();
        this.isExternal = user.isExternal();
        this.isConsider = user.isConsider();
        this.isHeader = user.isHeader();
        this.isGod = user.isGod();
        this.isSystemNotificationSubscriber = user.isSystemNotificationSubscriber();
        this.photoUrl = LocalFileUrl.ofNullable(user.getUserPhotoUri());
        this.branchRegionId = user.getBranchRegionId();
        this.organId = user.getOrganId();
        this.departmentId = user.getDepartmentId();
        this.regionId = user.getRegionId();
        this.districtId = user.getDistrictId();
        this.positionId = user.getPositionId();
        this.rankId = user.getRankId();
        this.statusId = user.getStatusId();
        this.roles = Optional.ofNullable(roles).orElseGet(Set::of);
        this.mustProvideDigitalSignature = user.getMustProvideDigitalSignature();

    }
}
