package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.repository.FileFormatRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FileUriValidator implements ConstraintValidator<ValidFileUri, String> {
    private final FileFormatRepository repository;
    private Set<String> allowedFormats;

    @Override
    public void initialize(ValidFileUri constraintAnnotation) {
        this.allowedFormats = Arrays.stream(constraintAnnotation.allow()).map(FileFormatAlias::name).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }

        if (value.length() > 500) {
            context.buildConstraintViolationWithTemplate(ErrorCode.MAX_URI_LENGTH).addConstraintViolation();
            return false;
        }

        String format = extractFileFormat(value);

        if (format == null) {
            context.buildConstraintViolationWithTemplate(ErrorCode.FILE_FORMAT_ABSENT).addConstraintViolation();
            return false;
        }

        if (isAllowedByDefault()) {
            if (repository.existsByExtension(format)) {
                return true;
            } else {
                context.buildConstraintViolationWithTemplate(ErrorCode.UNKNOWN_FILE_FORMAT).addConstraintViolation();
                return false;
            }
        } else if (this.allowedFormats.contains(format)) {
            return true;
        } else {
            context.buildConstraintViolationWithTemplate(ErrorCode.FILE_FORMAT_NOT_ALLOWED).addConstraintViolation();
            return false;
        }
    }

    private String extractFileFormat(String uri) {
        String[] strings = uri.split("\\.");
        if (strings.length==1) {
            return null;
        }
        return strings[strings.length - 1].toUpperCase();
    }

    private boolean isAllowedByDefault() {
        return this.allowedFormats.size() == 0;
    }
}
