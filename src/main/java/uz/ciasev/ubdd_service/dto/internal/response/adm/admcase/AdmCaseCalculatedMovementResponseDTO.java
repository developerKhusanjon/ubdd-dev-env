package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.*;
import uz.ciasev.ubdd_service.entity.admcase.MovementTypes;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdmCaseCalculatedMovementResponseDTO {

    private final Long admCaseId;
    private final MovementTypes type;
    private AdmCaseSendingResponseDTO sending;
    private AdmCaseSimplifiedResponseDTO simplified;

    public static AdmCaseCalculatedMovementResponseDTO doYourself(Long admCaseId) {
        return new AdmCaseCalculatedMovementResponseDTO(admCaseId, MovementTypes.YOURSELF, null, null);
    }

    public static AdmCaseCalculatedMovementResponseDTO doNothing(Long admCaseId) {
        return new AdmCaseCalculatedMovementResponseDTO(admCaseId, MovementTypes.NOTHING, null, null);
    }

    public static AdmCaseCalculatedMovementResponseDTO doSimplified(Long admCaseId, AdmCaseSimplifiedResponseDTO simplified) {
        return new AdmCaseCalculatedMovementResponseDTO(admCaseId, MovementTypes.SIMPLIFIED, null, simplified);
    }

    public static AdmCaseCalculatedMovementResponseDTO doSending(Long admCaseId, AdmCaseSendingResponseDTO sending) {
        return new AdmCaseCalculatedMovementResponseDTO(admCaseId, MovementTypes.SENDING, sending, null);
    }
}
