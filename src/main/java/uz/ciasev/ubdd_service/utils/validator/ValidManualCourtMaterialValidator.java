package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.ManualCourtMaterialDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.repository.court.CourtCaseFieldsRepository;
import uz.ciasev.ubdd_service.repository.court.CourtMaterialFieldsRepository;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ValidManualCourtMaterialValidator implements ConstraintValidator<ValidManualCourtMaterial, ManualCourtMaterialDTO> {

    private final ResolutionService resolutionService;
    private final CourtCaseFieldsRepository caseFieldsRepository;
    private final CourtMaterialFieldsRepository materialFieldsRepository;

    @Override
    public void initialize(ValidManualCourtMaterial constraintAnnotation) {
    }

    @Override
    public boolean isValid(ManualCourtMaterialDTO dto, ConstraintValidatorContext context) {

        if (dto == null) {
            return true;
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        if (dto.getRegistrationDate() != null && dto.getHearingTime() != null) {

            if (dto.getRegistrationDate().isAfter(dto.getHearingTime().toLocalDate())) {
                context.buildConstraintViolationWithTemplate(ErrorCode.REGISTRATION_DATE_MORE_THAN_HEARING_TIME).addConstraintViolation();
                isValid = false;
            }

            if (dto.getHearingTime().isAfter(LocalDateTime.now())) {
                context.buildConstraintViolationWithTemplate(ErrorCode.HEARING_TIME_IN_FUTURE).addConstraintViolation();
                isValid = false;
            }
        }

        if (dto.getCaseNumber() != null) {

            String number = dto.getCaseNumber();

            if (resolutionService.existsBySeriesAndNumber("", number)) {
                context.buildConstraintViolationWithTemplate(ErrorCode.RESOLUTION_WITH_SAME_NUMBER_EXISTS).addConstraintViolation();
                isValid = false;
            }

            if (caseFieldsRepository.existsByCaseNumber(number)) {
                context.buildConstraintViolationWithTemplate(ErrorCode.ADM_CASE_WITH_SAME_NUMBER_EXISTS).addConstraintViolation();
                isValid = false;
            }

            if (materialFieldsRepository.existsByCaseNumber(number)) {
                context.buildConstraintViolationWithTemplate(ErrorCode.COURT_MATERIAL_WITH_SAME_NUMBER_EXISTS).addConstraintViolation();
                isValid = false;
            }

        }

        return isValid;
    }
}
