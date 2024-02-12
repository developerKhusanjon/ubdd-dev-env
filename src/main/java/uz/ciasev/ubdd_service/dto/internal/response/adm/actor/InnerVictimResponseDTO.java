package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.victim.Victim;

@Getter
public class InnerVictimResponseDTO extends AbstractVictimResponseDTO {

    private final Long victimId;

    public InnerVictimResponseDTO(Victim victim, Person person) {

        super(victim, person);

        this.victimId = victim.getId();
    }
}