package uz.ciasev.ubdd_service.mvd_core.api.court.exception;

import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

public class MaterialReviewOfReviewedError extends CourtValidationException {

    public MaterialReviewOfReviewedError(CourtMaterialFields fields, Long presentClaimId) {
        super(String.format(
                "Material claim %s already reviewed by claim %s to status %s. But request try review by claim %s",
                fields.getReviewFromClaimId(),
                fields.getClaimId(),
                fields.getCourtStatusId(),
                presentClaimId
        ));
    }
}
