package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.validation.ProtocolValidationService;
import uz.ciasev.ubdd_service.service.validation.ValidationService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ValidProtocolValidator implements ConstraintValidator<ValidProtocol, ProtocolRequestDTO> {

    private final ValidationService validationService;
    private final ProtocolValidationService protocolValidationService;

    @Override
    public void initialize(ValidProtocol constraintAnnotation) {
    }

    @Override
    public boolean isValid(ProtocolRequestDTO protocolDTO, ConstraintValidatorContext context) {

        if (protocolDTO == null) {
            return true;
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

//        List<String> dateErrors = protocolValidationService.validateProtocolDates(protocolDTO);
//        for (String errorCode : dateErrors) {
//            context.buildConstraintViolationWithTemplate(errorCode).addConstraintViolation();
//            isValid = false;
//        }

        if(validationService.checkMtpNotInDistrict(protocolDTO.getDistrict(), protocolDTO.getMtp())) {
            context.buildConstraintViolationWithTemplate(ErrorCode.MTP_AND_DISTRICT_NOT_CONSIST).addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
