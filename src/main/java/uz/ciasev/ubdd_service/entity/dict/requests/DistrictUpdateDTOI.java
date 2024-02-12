package uz.ciasev.ubdd_service.entity.dict.requests;

public interface DistrictUpdateDTOI extends DictUpdateDTOI {
    Boolean getIsState();

    String getReportName();

    Boolean getIsNotDistrict();
}
