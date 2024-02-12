package uz.ciasev.ubdd_service.dto.internal.response.user;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.user.UsersSimpleProjection;

@Getter
public class UsersSimpleResponseDTO {
    private final Long id;
    private final String pinpp;
    private final String lastName;
    private final String firstName;
    private final String secondName;
    private final Long regionId;
    private final Long districId;
    private final Boolean isActive;

    public UsersSimpleResponseDTO(UsersSimpleProjection projection) {
        this.id = projection.getId();
        this.pinpp = projection.getPinpp();
        this.lastName = projection.getLastName();
        this.firstName = projection.getFirstName();
        this.secondName = projection.getSecondName();
        this.regionId = projection.getRegionId();
        this.districId = projection.getDistrictId();
        this.isActive = projection.getIsActive();
    }
}
