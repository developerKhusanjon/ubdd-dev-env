package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ParticipantDetailListResponseDTO {

    @JsonUnwrapped
    private final InnerParticipantResponseDTO participant;

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final Long protocolId;
    private final Long personDocumentTypeId;
    private final String documentSeries;
    private final String documentNumber;
    private final LocalDate documentGivenDate;
    private final LocalDate documentExpireDate;
    private final LocalFileUrl photoUrl;

    public ParticipantDetailListResponseDTO(ParticipantDetail participantDetail,
                                            Participant participant,
                                            Person person) {

        this.participant = new InnerParticipantResponseDTO(participant, person);

        this.id = participantDetail.getId();
        this.createdTime = participantDetail.getCreatedTime();
        this.editedTime = participantDetail.getEditedTime();
        this.userId = participantDetail.getUserId();
        this.protocolId = participantDetail.getProtocolId();
        this.personDocumentTypeId = participantDetail.getPersonDocumentTypeId();
        this.documentSeries = participantDetail.getDocumentSeries();
        this.documentNumber = participantDetail.getDocumentNumber();
        this.documentGivenDate = participantDetail.getDocumentGivenDate();
        this.documentExpireDate = participantDetail.getDocumentExpireDate();
        this.photoUrl = LocalFileUrl.ofNullable(participant.getPhotoUri());
    }
}
