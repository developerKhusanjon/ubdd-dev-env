package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.participant.Participant;

@Getter
public class ParticipantResponseDTO extends ParticipantListResponseDTO {

    private final AddressResponseDTO actualAddress;

    public ParticipantResponseDTO(Participant participant, Person person, AddressResponseDTO actualAddress) {
        super(participant, person);
        this.actualAddress = actualAddress;
    }
}
