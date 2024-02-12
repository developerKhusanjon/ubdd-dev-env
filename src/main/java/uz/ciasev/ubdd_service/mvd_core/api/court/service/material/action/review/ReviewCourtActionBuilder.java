package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.exception.MaterialClaimNotMatchError;
import uz.ciasev.ubdd_service.mvd_core.api.court.exception.MaterialReviewOfReviewedError;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionBuilder;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionFactory;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialBuilderContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.exception.court.ReviewCaseInStatusException;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialFieldsService;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ReviewCourtActionBuilder implements CourtActionBuilder {

    private final CourtActionFactory actionFactory;
    private final CourtMaterialFieldsService courtMaterialFieldsService;

    @Override
    public Optional<CourtAction> buildAction(MaterialBuilderContext context, ThirdCourtRequest courtRequest) {
        if (courtRequest.isEditing()) {
            return Optional.empty();
        }

        Long reviewClimeId = courtRequest.getClaimReviewId();

        if (reviewClimeId == null || isAlreadyApplied(context, courtRequest)) {
            return Optional.empty();
        }

        CourtMaterial courtMaterial = context.getMaterial();

        if (!reviewClimeId.equals(courtMaterial.getClaimId())) {
            CourtMaterialFields fields = context.getCourtMaterialFields();
            if (Objects.equals(fields.getReviewFromClaimId(), reviewClimeId)) {
                throw new MaterialReviewOfReviewedError(fields, reviewClimeId);
            }
            throw new MaterialClaimNotMatchError(fields, reviewClimeId);
        }

        CourtMaterialFields courtFields = courtMaterialFieldsService.getCurrent(courtMaterial);

        switch (courtFields.getCourtStatus().getAlias()) {
            case RETURNED: {
                return Optional.of(actionFactory.createReviewReturnAction(courtRequest.getClaimId()));
            }
            case RESOLVED:
            case PASSED_TO_ARCHIVE: {
                return Optional.of(actionFactory.createReviewResolutionAction(courtRequest.getClaimId()));
            }
            default: {
                throw new ReviewCaseInStatusException(courtFields.getCourtStatus());
            }
        }
    }

    private boolean isAlreadyApplied(MaterialBuilderContext context, ThirdCourtRequest courtRequest) {
        return courtRequest.getClaimId().equals(context.getMaterial().getClaimId());
    }
}
