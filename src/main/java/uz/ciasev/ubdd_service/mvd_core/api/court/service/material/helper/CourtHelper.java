package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper;

import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialBuilderContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.MovementRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialRejectBase;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReason;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CourtHelper {

    public static Optional<Boolean> extractIGrunted(ThirdCourtRequest request) {
        return request.getDefendants().stream().findFirst().map(DefendantRequest::getMaterialIsGranted);
    }

    public static Optional<CourtMaterialRejectBase> extractCourtMaterialRejectBase(ThirdCourtRequest request) {
        return request.getDefendants().stream().findFirst().map(DefendantRequest::getMaterialRejectBase);
    }

    public static CourtReturnReason extractReturnReason(ThirdCourtRequest requestDTO) {
        List<CourtReturnReason> returnReasons = requestDTO.getDefendants().stream()
                .map(DefendantRequest::getReturnReason)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        if (returnReasons.size() == 0) {
            throw new CourtValidationException(CourtValidationException.RETURN_REASON_NOT_SET);
        }

        if (returnReasons.size() > 1) {
            throw new CourtValidationException(CourtValidationException.RETURN_REASON_MULTIPLY);
        }

        return returnReasons.get(0);
    }

    public static boolean isMovement(ThirdCourtRequest request) {
        Optional<MovementRequest> courtMovement = Optional.ofNullable(request.getMovement());
        return courtMovement.map(MovementRequest::getClaimId).isPresent();
    }

    public static boolean isEditingAvailable(MaterialBuilderContext context, ThirdCourtRequest request) {
        CourtMaterialFields courtFields = context.getCourtMaterialFields();

        if (!request.isEditing()) {
            return false;
        }

        if (!courtFields.getClaimId().equals(request.getClaimId())) {
            return false;
        }

        if (courtFields.getCourtStatus().notOneOf(CourtStatusAlias.RETURNED, CourtStatusAlias.RESOLVED)) {
            return false;
        }

        if (request.getStatus().notOneOf(CourtStatusAlias.RETURNED, CourtStatusAlias.RESOLVED)) {
            return false;
        }

        return true;
    }
}
