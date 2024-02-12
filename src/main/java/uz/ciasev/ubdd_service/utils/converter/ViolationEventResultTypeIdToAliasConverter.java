package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.violation_event.ViolationEventResultTypeAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class ViolationEventResultTypeIdToAliasConverter implements AttributeConverter<ViolationEventResultTypeAlias, Long> {

    @Override
    public Long convertToDatabaseColumn(ViolationEventResultTypeAlias alias) {
        if (alias == null) {
            return null;
        }

        return alias.getId();
    }

    @Override
    public ViolationEventResultTypeAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return ViolationEventResultTypeAlias.getInstanceById(id);
    }
}
