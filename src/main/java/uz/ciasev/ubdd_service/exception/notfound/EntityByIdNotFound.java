package uz.ciasev.ubdd_service.exception.notfound;

public class EntityByIdNotFound extends EntityByParamsNotFound {

    public EntityByIdNotFound(Class entityClazz, Long value) {
        super(entityClazz, "id", value);
    }
}
