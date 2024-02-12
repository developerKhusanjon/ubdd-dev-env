package uz.ciasev.ubdd_service.dto.internal.response.user;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.user.User;

@Data
public class InspectorResponseDTO {

    private final Long id;
    private final String firstName;
    private final String secondName;
    private final String lastName;
    private final String documentSeries;
    private final String documentNumber;
    private final String workCertificate;
    private final String username;
    private final Long branchRegionId;
    private final Long organId;
    private final Long departmentId;
    private final Long regionId;
    private final Long districtId;
    private final Long positionId;
    private final Long rankId;
    private final String mobile;
    private final String landline;
    private final Boolean isActive;
    private final Boolean isConsider;
    private final Boolean isHeader;
    private final String photoUrl;

    public InspectorResponseDTO(User user, Person person) {
        this.id = user.getId();
        this.firstName = person.getFirstNameLat();
        this.secondName = person.getSecondNameLat();
        this.lastName = person.getLastNameLat();
        this.documentSeries = user.getDocumentSeries();
        this.documentNumber = user.getDocumentNumber();
        this.workCertificate = user.getWorkCertificate();
        this.username = user.getUsername();
        this.mobile = user.getMobile();
        this.landline = user.getLandline();
        this.isActive = user.isActive();
        this.isConsider = user.isConsider();
        this.isHeader = user.isHeader();
        this.photoUrl = user.getUserPhotoUri();
        this.branchRegionId = user.getBranchRegionId();
        this.organId = user.getOrganId();
        this.departmentId = user.getDepartmentId();
        this.regionId = user.getRegionId();
        this.districtId = user.getDistrictId();
        this.positionId = user.getPositionId();
        this.rankId = user.getRankId();
    }

}
