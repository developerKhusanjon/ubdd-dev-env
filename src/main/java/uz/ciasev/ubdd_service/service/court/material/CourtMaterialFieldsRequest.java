package uz.ciasev.ubdd_service.service.court.material;

import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;

import java.time.LocalDateTime;

public interface CourtMaterialFieldsRequest {

    CourtMaterialType getMaterialType();

    CourtStatus getCourtStatus();

    Region getRegion();

    District getDistrict();

    Long getInstance();

    String getJudgeInfo();

    LocalDateTime getHearingTime();

    String getCaseNumber();

    Boolean getIsProtest();

    Boolean getIsVccUsed();
}
