package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.merge;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.exception.ForbiddenCourtActionError;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.ActionCheckUtils;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionBuilder;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialBuilderContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MergeCourtActionBuilder implements CourtActionBuilder {

    @Override
    public Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest request) {
        Optional<Long> claimMergeId = Optional.ofNullable(request.getClaimMergeId());
        if (claimMergeId.isEmpty()) {
            validateNoAction(request);
            return Optional.empty();
        }

        ActionCheckUtils.checkStatus(request, CourtStatusAlias.MERGED, CourtActionName.MERGE);

        throw new ForbiddenCourtActionError(CourtActionName.MERGE);
    }

    private void validateNoAction(ThirdCourtRequest request) {
//        ActionCheckUtils.checkNoOneViolatorHasFinalResult(request, CourtFinalResultAlias.SEPARATING);
    }
}
