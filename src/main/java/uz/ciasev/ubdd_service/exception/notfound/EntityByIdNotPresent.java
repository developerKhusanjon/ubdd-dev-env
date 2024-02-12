package uz.ciasev.ubdd_service.exception.notfound;

public class EntityByIdNotPresent extends EntityByParamNotPresent {

    public EntityByIdNotPresent(Class entityClazz, Long value) {
        super(entityClazz, "id", value);
    }
}
