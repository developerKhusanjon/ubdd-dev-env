package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.returns;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.*;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.ActionCheckUtils;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.CourtHelper;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReason;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReasonAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.Objects;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias.RETURNING;

@RequiredArgsConstructor
@Component
public class ReturnCourtActionBuilder implements CourtActionBuilder {

    private final CourtActionFactory actionFactory;

    @Override
    public Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest request) {
        CourtStatus status = request.getStatus();

        if (status.not(CourtStatusAlias.RETURNED)) {
            validateNoAction(request);
            return Optional.empty();
        }

        ActionCheckUtils.checkAllViolatorHasFinalResult(request, RETURNING);

        CourtReturnReason returnReason = CourtHelper.extractReturnReason(request);

        return returnReason.is(CourtReturnReasonAlias.APPEAL_WITHDRAWN)
                ? Optional.ofNullable(actionFactory.createReturnWithdrawnMaterialAction(returnReason))
                : Optional.ofNullable(actionFactory.createReturnForProcessAction(returnReason));
    }

    private void validateNoAction(ThirdCourtRequest request) {
        ActionCheckUtils.checkNoOneViolatorHasFinalResult(request, RETURNING);

        boolean hasReturnReason = request.getDefendants().stream()
                .map(DefendantRequest::getReturnReason)
                .anyMatch(Objects::nonNull);
        if (hasReturnReason) {
            throw new CourtValidationException(CourtValidationException.RETURN_REASON_WITHOUT_RETURN_STATUS);
        }
    }

}
