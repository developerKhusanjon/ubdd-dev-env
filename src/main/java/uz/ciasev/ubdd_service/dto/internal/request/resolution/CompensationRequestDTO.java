package uz.ciasev.ubdd_service.dto.internal.request.resolution;

import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;


public interface CompensationRequestDTO {

    Long getViolatorId();

    Long getVictimId();

    VictimType getVictimType();

    Long getAmount();

    Compensation buildCompensation();
}
