package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class FiltersNotSetException extends ApplicationException {

    private FiltersNotSetException(String message) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.FILTERS_NOT_SET,
                message
        );
    }

    public static FiltersNotSetException allRequired(Collection<String> requiredFields) {
        return new FiltersNotSetException(String.format("All of fields required: %s", String.join(", ", requiredFields)));
    }

    @SafeVarargs
    public static FiltersNotSetException onOfRequired(Collection<String>... requiredFieldsTuple) {
        return new FiltersNotSetException(String.format(
                "One of fields tuple required: %s",
                Arrays.stream(requiredFieldsTuple)
                        .map(fields -> String.format("(%s)", String.join(", ", fields)))
                        .collect(Collectors.joining(", "))

        ));
    }

    public static FiltersNotSetException onOfRequired(String... requiredFields) {
        return new FiltersNotSetException(String.format(
                "One of fields required: %s",
                String.join(", ", requiredFields)

        ));
    }
}
