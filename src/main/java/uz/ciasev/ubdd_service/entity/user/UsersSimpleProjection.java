package uz.ciasev.ubdd_service.entity.user;

public interface UsersSimpleProjection {
    Long getId();
    String getPinpp();
    String getLastName();
    String getFirstName();
    String getSecondName();
    Long getRegionId();
    Long getDistrictId();
    Boolean getIsActive();
}
