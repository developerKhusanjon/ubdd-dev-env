package uz.ciasev.ubdd_service.dto.internal.request.participant;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;


@Data
public class ParticipantDetailRequestDTO {

    private String signature;

    public ParticipantDetail buildDetail() {
        ParticipantDetail participantDetail = new ParticipantDetail();
        participantDetail.setSignature(this.signature);
        return participantDetail;
    }

    public ParticipantDetail applyTo(ParticipantDetail participantDetail) {
        participantDetail.setSignature(this.signature);

        return participantDetail;
    }

}
