package uz.ciasev.ubdd_service.repository.dashboard.projections;

public interface CountByAdmStatusProjection {

    Long getAdmStatusId();
    String getAdmStatusAlias();
    Long getCountValue();
}
