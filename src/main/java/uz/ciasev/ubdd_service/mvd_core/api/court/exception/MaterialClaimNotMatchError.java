package uz.ciasev.ubdd_service.mvd_core.api.court.exception;

import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

public class MaterialClaimNotMatchError extends CourtValidationException {

    public MaterialClaimNotMatchError(CourtMaterialFields fields, Long presentClaimId) {
        super(String.format(
                "Current сlaimId of material '%s' different from request value '%s'",
                fields.getReviewFromClaimId() != null
                    ? String.format("%s (review of %s)", fields.getClaimId(), fields.getReviewFromClaimId())
                    : fields.getClaimId(),
                presentClaimId
        ));
    }
}
