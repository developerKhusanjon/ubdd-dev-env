package uz.ciasev.ubdd_service.dto.internal.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PersonListResponseDTO extends AbstractPersonResponseDTO {

    private final Long id;

    public PersonListResponseDTO(Person person) {
        super(person);
        this.id = person.getId();
    }
}
