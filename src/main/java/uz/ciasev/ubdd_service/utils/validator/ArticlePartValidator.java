package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleParticipantTypeAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ArticlePartValidator implements ConstraintValidator<ValidArticlePart, ArticlePartRequestDTO> {

    @Override
    public void initialize(ValidArticlePart constraintAnnotation) {
    }

    @Override
    public boolean isValid(ArticlePartRequestDTO entity, ConstraintValidatorContext context) {
        if (entity == null) {
            return true;
        }

        if (entity.getArticleParticipantType() == null) {
            return true;
        }

        boolean isValid = checkJuridicPenalty(entity, context) && checkPersonPenalty(entity, context);

        if (entity.getIsPenaltyOnly()) {
            //if (entity.getPersonMinNumerator() == null) {
            if (entity.getPenaltyRange().stream().anyMatch(
                    r -> r.getPersonMinNumerator() == null
            )) {
                isValid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_IS_PENALTY_ONLY_MAST_CONTENT_PENALTY_RANGE_FOR_PERSON).addConstraintViolation();
            }
        }

        if (entity.getIsDiscount()) {
            //if (entity.getPersonMinNumerator() == null && entity.getJuridicMinNumerator() == null) {
            if (entity.getPenaltyRange().stream().anyMatch(
                    r -> r.getPersonMinNumerator() == null && r.getJuridicMinNumerator() == null
            )) {
                isValid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_IS_DISCOUNT_MAST_CONTENT_PENALTY_RANGE).addConstraintViolation();
            }
        }

        return isValid;
    }

    private boolean checkJuridicPenalty(ArticlePartRequestDTO entity, ConstraintValidatorContext context) {
//        Long fromNumerator = entity.getJuridicMinNumerator();
//        Long fromDenominator = entity.getJuridicMinDenominator();
//        Long toNumerator = entity.getJuridicMaxNumerator();
//        Long toDenominator = entity.getJuridicMaxDenominator();
//
//        return checkPenaltyWithParticipantType(entity, context, fromNumerator, fromDenominator, toNumerator, toDenominator, ArticleParticipantTypeAlias.Juridic);

        return entity.getPenaltyRange().stream().allMatch(
                r -> checkPenaltyWithParticipantType(entity, context, r.getJuridicMinNumerator(), r.getJuridicMinDenominator(),
                        r.getJuridicMaxNumerator(), r.getJuridicMaxDenominator(), ArticleParticipantTypeAlias.Juridic)
        );
    }


    private boolean checkPersonPenalty(ArticlePartRequestDTO entity, ConstraintValidatorContext context) {
//        Long fromNumerator = entity.getPersonMinNumerator();
//        Long fromDenominator = entity.getPersonMinDenominator();
//        Long toNumerator = entity.getPersonMaxNumerator();
//        Long toDenominator = entity.getPersonMaxDenominator();
//
//        return checkPenaltyWithParticipantType(entity, context, fromNumerator, fromDenominator, toNumerator, toDenominator, ArticleParticipantTypeAlias.Person);

        return entity.getPenaltyRange().stream().allMatch(
                r -> checkPenaltyWithParticipantType(entity, context, r.getPersonMinNumerator(), r.getPersonMinDenominator(),
                        r.getPersonMaxNumerator(), r.getPersonMaxDenominator(), ArticleParticipantTypeAlias.Person)
        );
    }


    private boolean checkPenaltyWithParticipantType(ArticlePartRequestDTO entity,
                                                    ConstraintValidatorContext context,
                                                    Integer fromNumerator,
                                                    Integer fromDenominator,
                                                    Integer toNumerator,
                                                    Integer toDenominator,
                                                    ArticleParticipantTypeAlias participantTypeAlias) {

        boolean isValid = checkPenaltyRange(context, fromNumerator, fromDenominator, toNumerator, toDenominator);

        ArticleParticipantTypeAlias participantType = entity.getArticleParticipantType().getAlias();
        boolean isAllPresent = isAllPresent(fromNumerator, fromDenominator, toNumerator, toDenominator);

        if (isAllPresent) {
            if (!(participantType == participantTypeAlias
                    || participantType == ArticleParticipantTypeAlias.PersonAndJuridic)) {
                isValid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_PENALTY_RANGE_FOR_PARTICIPANT_TYPE_INVALID).addConstraintViolation();
            }
        }

        return isValid;
    }


    private boolean checkPenaltyRange(ConstraintValidatorContext context, Integer fromNumerator, Integer fromDenominator, Integer toNumerator, Integer toDenominator) {
        boolean isAllNotPresent = isAllNotPresent(fromNumerator, fromDenominator, toNumerator, toDenominator);
        if (isAllNotPresent) {
            return true;
        }

        boolean isAllPresent = isAllPresent(fromNumerator, fromDenominator, toNumerator, toDenominator);
        if (!isAllPresent) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_PENALTY_RANGE_PRESENT_INVALID).addConstraintViolation();
            return false;
        }

        return validateRangeValue(context, fromNumerator, fromDenominator, toNumerator, toDenominator);
    }

    private boolean validateRangeValue(ConstraintValidatorContext context, Integer fromNumerator, Integer fromDenominator, Integer toNumerator, Integer toDenominator) {
        boolean isValid = true;

        if (fromDenominator < 1) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_PENALTY_RANGE_DENOMINATOR_INVALID).addConstraintViolation();
        }

        if (toDenominator < 1) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_PENALTY_RANGE_DENOMINATOR_INVALID).addConstraintViolation();
        }

        if (fromNumerator < 0) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_PENALTY_RANGE_NUMERATOR_INVALID).addConstraintViolation();
        }

        if (toNumerator < 0) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_PENALTY_RANGE_NUMERATOR_INVALID).addConstraintViolation();
        }

        float fromValue = Float.valueOf(fromNumerator) / fromDenominator;
        float toValue = Float.valueOf(toNumerator) / toDenominator;

        if (toValue < fromValue) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_PENALTY_TO_LOWE_THEN_FROM).addConstraintViolation();
        }

        return isValid;
    }


    private boolean isAllPresent(Number fromNumerator, Number fromDenominator, Number toNumerator, Number toDenominator) {
        return fromNumerator != null
                && fromDenominator != null
                && toNumerator != null
                && toDenominator != null;
    }

    private boolean isAllNotPresent(Number fromNumerator, Number fromDenominator, Number toNumerator, Number toDenominator) {
        return fromNumerator == null
                && fromDenominator == null
                && toNumerator == null
                && toDenominator == null;
    }

}
