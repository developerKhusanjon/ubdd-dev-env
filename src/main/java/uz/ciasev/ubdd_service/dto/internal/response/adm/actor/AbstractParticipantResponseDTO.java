package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.InnerPersonResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.participant.Participant;

@Getter
public class AbstractParticipantResponseDTO {

    @JsonUnwrapped
    private final InnerPersonResponseDTO person;

    private final Long participantTypeId;
    private final Long admCaseId;
    private final Long mergedToParticipantId;
    private final Long actualAddressId;
    private final String mobile;
    private final String landline;

    public AbstractParticipantResponseDTO(Participant participant,
                                          Person person) {

        this.person = new InnerPersonResponseDTO(person);

        this.participantTypeId = participant.getParticipantTypeId();
        this.admCaseId = participant.getAdmCaseId();
        this.mergedToParticipantId = participant.getMergedToParticipantId();
        this.actualAddressId = participant.getActualAddressId();
        this.mobile = participant.getMobile();
        this.landline = participant.getLandline();

    }
}
