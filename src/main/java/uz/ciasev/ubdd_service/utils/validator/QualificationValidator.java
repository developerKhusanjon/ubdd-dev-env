package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationArticleRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.validation.ProtocolValidationService;
import uz.ciasev.ubdd_service.service.validation.ValidationService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

@RequiredArgsConstructor
public class QualificationValidator implements ConstraintValidator<ValidQualification, QualificationRequestDTO> {

    private final ProtocolValidationService protocolValidationService;
    private final ValidationService validationService;

    @Override
    public void initialize(ValidQualification constraintAnnotation) {
    }

    @Override
    public boolean isValid(QualificationRequestDTO protocolDTO, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (protocolDTO == null || protocolDTO.getArticlePart() == null) {
//            context.buildConstraintViolationWithTemplate(ErrorCode.MAIN_ARTICLE_PART_REQUIRED).addConstraintViolation();
            return true;
        }

        boolean isValid = true;

        if (protocolValidationService.validateJuridic(protocolDTO)) {
            context.buildConstraintViolationWithTemplate(ErrorCode.IS_JURIDIC_AND_JURIDIC_NOT_CONSIST).addConstraintViolation();
            isValid = false;
        }

        Optional<String> error = protocolValidationService.checkArticleWithParticipantType(protocolDTO.getArticlePart(), protocolDTO.getIsJuridic());
        if (error.isPresent()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(error.get()).addConstraintViolation();
        }

        Optional<String> violationTypeError = protocolValidationService.validateArticleViolationTypePresence(protocolDTO);
        if (violationTypeError.isPresent()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(violationTypeError.get()).addConstraintViolation();
        }

        List<QualificationArticleRequestDTO> additionArticles = protocolDTO.getAdditionArticles();

        if (additionArticles.isEmpty())
            return isValid;

        boolean additionViolationTypeIsValid = additionArticles.stream()
                .allMatch(validationService::checkViolationTypePresenceInArticleRequest);

        if (!additionViolationTypeIsValid) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(ErrorCode.ADDITION_ARTICLE_VIOLATION_TYPE_REQUIRED).addConstraintViolation();
        }

        Set<QualificationArticleRequestDTO> presentedArticle = new HashSet<>();
        presentedArticle.add(new QualificationArticleRequestDTO(protocolDTO.getArticlePart(), protocolDTO.getArticleViolationType()));

        Boolean hasDuplicate = !additionArticles.stream()
                .allMatch(presentedArticle::add);

        if (hasDuplicate) {
            context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_HAS_DUPLICATE).addConstraintViolation();
            isValid = false;
        }

        if (!protocolDTO.getArticlePart().isCourtOnly()) {
            boolean isHasCourtArticles = additionArticles
                    .stream()
                    .map(QualificationArticleRequestDTO::getArticlePart)
                    .filter(Objects::nonNull)
                    .anyMatch(ArticlePart::isCourtOnly);

            if (isHasCourtArticles) {
                context.buildConstraintViolationWithTemplate(ErrorCode.COURT_ARTICLE_PART_CAN_NOT_BE_ADDITION_FOR_ORGAN_ARTICLE_PART).addConstraintViolation();
                isValid = false;
            }
        }

        return isValid;

    }
}
