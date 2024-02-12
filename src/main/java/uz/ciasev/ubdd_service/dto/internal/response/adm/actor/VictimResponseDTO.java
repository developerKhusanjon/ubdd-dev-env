package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.victim.Victim;

@Getter
public class VictimResponseDTO extends VictimListResponseDTO {

    private final AddressResponseDTO actualAddress;
    private final AddressResponseDTO postAddress;

    public VictimResponseDTO(Victim victim, Person person, AddressResponseDTO actualAddress, AddressResponseDTO postAddress) {
        super(victim, person);
        this.actualAddress = actualAddress;
        this.postAddress = postAddress;
    }
}
