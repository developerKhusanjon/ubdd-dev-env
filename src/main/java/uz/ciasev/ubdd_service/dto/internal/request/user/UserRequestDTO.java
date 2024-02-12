package uz.ciasev.ubdd_service.dto.internal.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.f1.dto.F1Document;
import uz.ciasev.ubdd_service.dto.internal.request.PlaceRequest;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.dict.user.UserStatus;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO implements PlaceRequest {

    @NotNull(message = ErrorCode.BRANCH_REGION_REQUIRED)
    @ActiveOnly(message = ErrorCode.BRANCH_REGION_DEACTIVATED)
    @JsonProperty(value = "branchRegionId")
    private Region branchRegion;

    @ActiveOnly(message = ErrorCode.ORGAN_DEACTIVATED)
    @JsonProperty(value = "organId")
    private Organ organ;

    @ActiveOnly(message = ErrorCode.DEPARTMENT_DEACTIVATED)
    @JsonProperty(value = "departmentId")
    private Department department;

    @ActiveOnly(message = ErrorCode.REGION_DEACTIVATED)
    @JsonProperty(value = "regionId")
    private Region region;

    @ActiveOnly(message = ErrorCode.DISTRICT_DEACTIVATED)
    @JsonProperty(value = "districtId")
    private District district;

    @NotNull(message = ErrorCode.WORK_CERTIFICATE_REQUIRED)
    private String workCertificate;

    @NotNull(message = ErrorCode.POSITION_REQUIRED)
    @ActiveOnly(message = ErrorCode.POSITION_DEACTIVATED)
    @JsonProperty(value = "positionId")
    private Position position;

    @NotNull(message = ErrorCode.RANK_REQUIRED)
    @ActiveOnly(message = ErrorCode.RANK_DEACTIVATED)
    @JsonProperty(value = "rankId")
    private Rank rank;

    @Pattern(regexp = "^\\d{12}$", message = ErrorCode.USER_MOBILE_FORMAT_INVALID)
    private String mobile;

    @Pattern(regexp = "^\\d{9}$", message = ErrorCode.USER_LANDLINE_FORMAT_INVALID)
    private String landline;

    @NotNull(message = ErrorCode.USER_STATUS_REQUIRED)
    @JsonProperty(value = "statusId")
    private UserStatus status;

    private Boolean isOffline = false;

    private Boolean isExternal = false;

    private Boolean isSystemNotificationSubscriber = false;

    private Boolean isConsider = false;

    private Boolean isHeader = true;

    private Boolean isSuperuser = false;

//        @NotNull(message = ErrorCode.MUST_PROVIDE_DIGITAL_SIGNATURE_REQUIRED)
    private Boolean mustProvideDigitalSignature;

    public Boolean getMustProvideDigitalSignature() {
        if (mustProvideDigitalSignature == null) return false;
        return mustProvideDigitalSignature;
    }

    public User buildUser() {
        User user = new User();

        user.setWorkCertificate(this.workCertificate);
        user.setMobile(this.mobile);
        user.setLandline(this.landline);
        user.setOffline(this.isOffline);
        user.setExternal(this.isExternal);
        user.setSystemNotificationSubscriber(this.isSystemNotificationSubscriber);
        user.setConsider(this.isConsider);
        user.setHeader(this.isHeader);
        user.setSuperuser(this.isSuperuser);
        user.setBranchRegion(this.branchRegion);
        user.setOrgan(this.organ);
        user.setDepartment(this.department);
        user.setRegion(this.region);
        user.setDistrict(this.district);
        user.setPosition(this.position);
        user.setRank(this.rank);
        user.setMustProvideDigitalSignature(this.getMustProvideDigitalSignature());

        user.setStatus(this.status);

        return user;
    }

    public User applyTo(User user) {

        User newUser = user.toBuilder()
                .branchRegion(this.branchRegion)
                .organ(this.organ)
                .department(this.department)
                .region(this.region)
                .district(this.district)
                .workCertificate(this.workCertificate)
                .position(this.position)
                .rank(this.rank)
                .mobile(this.mobile)
                .landline(this.landline)
                .isOffline(this.isOffline)
                .isExternal(this.isExternal)
                .isConsider(this.isConsider)
                .isHeader(this.isHeader)
                .isSystemNotificationSubscriber(this.isSystemNotificationSubscriber)
                .mustProvideDigitalSignature(this.getMustProvideDigitalSignature())
                .build();

        newUser.setStatus(this.status);
        return newUser;
    }

    public User buildUser(Person person, String userPhotoUrl, F1Document f1Document) {
        User user = buildUser();

        user.setPerson(person);
        user.setLastNameLat(person.getLastNameLat());
        user.setFirstNameLat(person.getFirstNameLat());
        user.setSecondNameLat(person.getSecondNameLat());
        user.setUserPhotoUri(userPhotoUrl);
        user.setDocumentSeries(f1Document.getSeries());
        user.setDocumentNumber(f1Document.getNumber());

        return user;
    }
}