package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.participant.Participant;

@Getter
public class InnerParticipantResponseDTO extends AbstractParticipantResponseDTO {

    private final Long participantId;

    public InnerParticipantResponseDTO(Participant participant, Person person) {

        super(participant, person);

        this.participantId = participant.getId();
    }
}