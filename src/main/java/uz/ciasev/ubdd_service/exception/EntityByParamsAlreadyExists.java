package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.utils.StringUtils;

public class EntityByParamsAlreadyExists extends ValidationException {

    public EntityByParamsAlreadyExists(Class<?> entityClazz, String fields) {
        super(
                String.format("%s_ALREADY_EXISTS", StringUtils.camelToUpperSnake(entityClazz.getSimpleName())),
                String.format("Entity %s by %s already exists", entityClazz.getName(), fields)
        );
    }

    public EntityByParamsAlreadyExists(Class<?> entityClazz, String field, Object value) {
        this(entityClazz, String.format("%s=%s", field, value));
    }

    public EntityByParamsAlreadyExists(Class<?> entityClazz, String field1, Object value1, String field2, Object value2) {
        this(entityClazz, String.format("%s=%s, %s=%s", field1, value1, field2, value2));
    }

    public EntityByParamsAlreadyExists(Class<?> entityClazz, String field1, Object value1, String field2, Object value2, String field3, Object value3) {
        this(entityClazz, String.format("%s=%s, %s=%s, %s=%s", field1, value1, field2, value2, field3, value3));
    }
}
