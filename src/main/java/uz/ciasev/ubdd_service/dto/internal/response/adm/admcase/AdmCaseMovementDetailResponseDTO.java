package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.user.InspectorResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseMovement;

@Getter
public class AdmCaseMovementDetailResponseDTO extends AdmCaseMovementListResponseDTO {
    private final InspectorResponseDTO sendUser;
    private final InspectorResponseDTO declineUser;
    private final InspectorResponseDTO cancelUser;

    public AdmCaseMovementDetailResponseDTO(
            AdmCaseMovement admCaseMovement,
            Boolean isCancelPossible,
            InspectorResponseDTO sendUser,
            InspectorResponseDTO declineUser,
            InspectorResponseDTO cancelUser) {

        super(admCaseMovement, isCancelPossible);

        this.sendUser = sendUser;
        this.declineUser = declineUser;
        this.cancelUser = cancelUser;
    }
}
