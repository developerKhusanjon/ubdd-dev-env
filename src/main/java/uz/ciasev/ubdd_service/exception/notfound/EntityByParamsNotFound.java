package uz.ciasev.ubdd_service.exception.notfound;

import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.exception.NotFoundException;
import uz.ciasev.ubdd_service.utils.StringUtils;

public class EntityByParamsNotFound extends NotFoundException {

    public EntityByParamsNotFound(Class entityClazz, String fields) {
        super(
                String.format("%s_NOT_FOUND", StringUtils.camelToUpperSnake(entityClazz.getSimpleName())),
                String.format("Not found entity %s by %s", entityClazz.getName(), fields)
        );
    }

    public EntityByParamsNotFound(Class entityClazz, String field, Object value) {
        this(entityClazz, String.format("%s=%s", field, value));
    }

    public EntityByParamsNotFound(Class entityClazz, String field1, Object value1, String field2, Object value2) {
        this(entityClazz, String.format("%s=%s, %s=%s", field1, value1, field2, value2));
    }

    public EntityByParamsNotFound(Class entityClazz, Place place) {
        this(
                entityClazz,
                String.format(
                        "organ=%s, department=%s, region=%s, district=%s",
                        place.getOrganId(),
                        place.getDepartment(),
                        place.getRegionId(),
                        place.getDistrictId()
                )
        );
    }
}
