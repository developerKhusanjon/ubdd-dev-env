package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NotificationTypeToAliasConverter implements AttributeConverter<NotificationTypeAlias, Long> {
    @Override
    public Long convertToDatabaseColumn(NotificationTypeAlias alias) {
        if (alias == null) {
            return null;
        }
        return alias.getId();
    }

    @Override
    public NotificationTypeAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return NotificationTypeAlias.getInstanceById(id);
    }
}
