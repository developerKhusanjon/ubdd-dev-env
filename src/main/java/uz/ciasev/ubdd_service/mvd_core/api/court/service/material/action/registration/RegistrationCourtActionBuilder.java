package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionBuilder;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionFactory;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialBuilderContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.*;

@RequiredArgsConstructor
@Component
public class RegistrationCourtActionBuilder implements CourtActionBuilder {

    private final CourtActionFactory actionFactory;

    @Override
    public Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest r) {

        if (r.getStatus() != null && r.getStatus().isInformationOnly()) {
            return Optional.of(actionFactory.createInformationAction(r.getStatus()));
        }

        validateRegistrationData(r);

        List<RegistrationCourtAction.CourtViolatorData> violatorsData = extractViolatorsData(context, r);

        RegistrationCourtAction registrationAction = actionFactory.createRegistrationAction(
                r.getMaterialType(),
                r.getStatus(),
                r.getCourt(),
                r.getInstance(),
                r.getJudgeInfo(),
                r.getHearingTime(),
                r.getCaseNumber(),
                r.isProtest(),
                r.isUseVcc(),
                violatorsData
        );

        return Optional.of(registrationAction);
    }

    private List<RegistrationCourtAction.CourtViolatorData> extractViolatorsData(MaterialBuilderContext context, ThirdCourtRequest r) {
        List<DefendantRequest> defendants = r.getDefendants();

        if (defendants == null || defendants.size() == 0) {
            throw new CourtValidationException(DEFENDANT_IS_EMPTY);
        }

        List<Long> materialViolatorIdSet = context.getViolatorsId();
        Set<Long> defendantViolatorSet = defendants.stream().map(DefendantRequest::getViolatorId).collect(Collectors.toSet());

        if (!materialViolatorIdSet.containsAll(defendantViolatorSet)) {
            throw new CourtValidationException(CourtValidationException.FOREIGN_CASE_VIOLATORS);
        }

        return defendants
                .stream()
                .map(d -> new RegistrationCourtAction.CourtViolatorData(
                        d.getViolatorId(),
                        d.getDefendantId(),
                        d.isParticipated(),
                        d.getProsecutorRegion(),
                        d.getProsecutorDistrict()))
                .collect(Collectors.toList());
    }

    private void validateRegistrationData(ThirdCourtRequest r) {
        if (r.getStatus() == null)
            throw new CourtValidationException(STATUS_REQUIRED);

        CourtStatus status = r.getStatus();

        if (r.getClaimId() == null)
            throw new CourtValidationException(CLAIM_ID_REQUIRED);

        if (r.getMaterialType() == null)
            throw new CourtValidationException(MATERIAL_TYPE_FIELD_REQUIRED);

        if (r.getCourt() == null)
            throw new CourtValidationException(COURT_FIELD_REQUIRED);

        if (status.getAlias().equals(CourtStatusAlias.PAUSED) && !r.isPaused())
            throw new CourtValidationException(IS_PAUSED_TRUE);

        if (!status.getAlias().equals(CourtStatusAlias.PAUSED) && r.isPaused())
            throw new CourtValidationException(IS_PAUSED_FALSE);
    }
}
