package uz.ciasev.ubdd_service.dto.internal.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;

@Getter
public class InnerPersonResponseDTO extends AbstractPersonResponseDTO {

    private final Long personId;

    public InnerPersonResponseDTO(Person person) {
        super(person);
        this.personId = person.getId();
    }
}