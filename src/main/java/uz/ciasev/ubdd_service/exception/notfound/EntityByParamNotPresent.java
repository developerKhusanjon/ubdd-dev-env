package uz.ciasev.ubdd_service.exception.notfound;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.utils.StringUtils;

public class EntityByParamNotPresent extends ApplicationException {

    public EntityByParamNotPresent(Class entityClazz, String fields) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                String.format("%s_NOT_PRESENT", StringUtils.camelToUpperSnake(entityClazz.getSimpleName())),
                String.format("Entity %s with %s missing in database", entityClazz.getName(), fields)
        );
    }

    public EntityByParamNotPresent(Class entityClazz, String field, Object value) {
        this(
                entityClazz,
                String.format("%s = %s", field, value)
        );
    }

    public EntityByParamNotPresent(Class entityClazz, String field1, Object value1, String field2, Object value2) {
        this(
                entityClazz,
                String.format("%s = %s, %s = %s", field1, value1, field2, value2)
        );
    }
}
