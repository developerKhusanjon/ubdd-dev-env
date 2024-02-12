package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;

@Getter
public class ParticipantDetailResponseDTO extends ParticipantDetailListResponseDTO {

    private final String signature;
    private final AddressResponseDTO documentGivenAddress;

    public ParticipantDetailResponseDTO(ParticipantDetail participantDetail,
                                        Participant participant,
                                        Person person,
                                        AddressResponseDTO documentGivenAddress) {


        super(participantDetail, participant, person);
        this.signature = participantDetail.getSignature();
        this.documentGivenAddress = documentGivenAddress;
    }
}
