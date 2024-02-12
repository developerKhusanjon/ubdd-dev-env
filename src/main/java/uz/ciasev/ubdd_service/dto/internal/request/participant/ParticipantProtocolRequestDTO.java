package uz.ciasev.ubdd_service.dto.internal.request.participant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.request.ActorRequest;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonDocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.ParticipantType;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidAddress;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParticipantProtocolRequestDTO extends ParticipantRequestDTO implements ActorRequest {

    private String pinpp;

    @Valid
    private PersonRequestDTO person;

    @Valid
    private PersonDocumentRequestDTO document;

    @NotNull(message = ErrorCode.PARTICIPANT_TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.PARTICIPANT_TYPE_DEACTIVATED)
    @JsonProperty(value = "participantTypeId")
    private ParticipantType participantType;

    @Valid
    @NotNull(message = ErrorCode.ACTUAL_ADDRESS_REQUIRED)
    @ValidAddress(message = ErrorCode.ACTUAL_ADDRESS_INVALID)
    private AddressRequestDTO actualAddress;

    @Valid
    @NotNull(message = ErrorCode.PARTICIPANT_DETAIL_REQUIRED)
    private ParticipantDetailRequestDTO participantDetail;

    public Participant buildParticipant() {
        Participant participant = super.buildParticipant();
        participant.setParticipantType(this.participantType);

        return participant;
    }
}
