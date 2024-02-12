package uz.ciasev.ubdd_service.dto.internal.request;

import uz.ciasev.ubdd_service.entity.dict.person.Occupation;

public interface ActorDetailRequestDTO {

    Occupation getOccupation();

    String getEmploymentPlace();

    String getEmploymentPosition();

}
