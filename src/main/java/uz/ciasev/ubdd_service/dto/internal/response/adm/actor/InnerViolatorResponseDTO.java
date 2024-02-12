package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.violator.Violator;

@Getter
public class InnerViolatorResponseDTO extends AbstractViolatorResponseDTO {

    private final Long violatorId;

    public InnerViolatorResponseDTO(Violator violator, Person person) {

        super(violator, person);

        this.violatorId = violator.getId();
    }
}