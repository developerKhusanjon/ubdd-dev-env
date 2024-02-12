package uz.ciasev.ubdd_service.repository.dashboard.projections;

public interface ProtocolCountByGeographyProjection {

    Long getRegionId();
    Long getDistrictId();
    Long getCountValue();
}
