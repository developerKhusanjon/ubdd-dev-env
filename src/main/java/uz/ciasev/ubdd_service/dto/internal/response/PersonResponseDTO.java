package uz.ciasev.ubdd_service.dto.internal.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PersonResponseDTO extends PersonListResponseDTO {

    private final AddressResponseDTO birthAddress;

    public PersonResponseDTO(Person person, AddressResponseDTO birthAddress) {
        super(person);
        this.birthAddress = birthAddress;
    }
}
