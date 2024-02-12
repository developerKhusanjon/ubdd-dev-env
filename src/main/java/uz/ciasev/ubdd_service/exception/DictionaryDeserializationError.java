package uz.ciasev.ubdd_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.utils.StringUtils;

@Getter
public class DictionaryDeserializationError extends ApplicationException {

    public DictionaryDeserializationError(Class clazz, Object id, Exception e) {
        super(
                HttpStatus.BAD_REQUEST,
                String.format("%s_DESERIALIZATION_ERROR", StringUtils.camelToUpperSnake(clazz.getSimpleName())),
                String.format("Error %s happened during deserialization of value '%s' to '%s': %s", e.getClass().getSimpleName(), id, clazz.getName(), e.getMessage())
        );
    }
}
