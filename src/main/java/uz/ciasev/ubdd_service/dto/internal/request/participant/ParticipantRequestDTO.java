package uz.ciasev.ubdd_service.dto.internal.request.participant;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ParticipantRequestDTO {

    @Pattern(regexp = "^\\d{12}$", message = ErrorCode.PARTICIPANT_MOBILE_FORMAT_INVALID)
    @NotNull(message = ErrorCode.MOBILE_REQUIRED)
    private String mobile;

    @Pattern(regexp = "^\\d{9}$", message = ErrorCode.PARTICIPANT_LANDLINE_FORMAT_INVALID)
    private String landline;

    public Participant buildParticipant() {
        Participant participant = new Participant();

        participant.setMobile(mobile);
        participant.setLandline(landline);

        return participant;
    }

    public Participant applyTo(Participant participant) {
        participant.setMobile(mobile);
        participant.setLandline(landline);

        return participant;
    }
}
