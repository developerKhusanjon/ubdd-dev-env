package uz.ciasev.ubdd_service.exception.notfound;

public class EntityByAliasNotPresent extends EntityByParamNotPresent {

    public EntityByAliasNotPresent(Class entityClazz, String value) {
        super(entityClazz, "alias", value);
    }
}
