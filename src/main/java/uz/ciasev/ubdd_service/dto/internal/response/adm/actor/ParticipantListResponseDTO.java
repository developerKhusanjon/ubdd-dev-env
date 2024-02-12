package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

@Getter
public class ParticipantListResponseDTO extends AbstractParticipantResponseDTO {

    private final Long id;
    private final LocalFileUrl photoUrl;

    public ParticipantListResponseDTO(Participant participant, Person person) {
        super(participant, person);
        this.id = participant.getId();
        this.photoUrl = LocalFileUrl.ofNullable(participant.getPhotoUri());
    }
}
