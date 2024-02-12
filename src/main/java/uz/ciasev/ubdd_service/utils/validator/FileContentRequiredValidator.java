package uz.ciasev.ubdd_service.utils.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileContentRequiredValidator implements ConstraintValidator<FileContentRequired, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) {
            return false;
        }

        return !file.isEmpty();
    }
}
