package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class MibNotificationTypeAliasConverter implements AttributeConverter<MibNotificationTypeAlias, Long> {

    @Override
    public Long convertToDatabaseColumn(MibNotificationTypeAlias alias) {
        if (alias == null) {
            return null;
        }

        return alias.getId();
    }

    @Override
    public MibNotificationTypeAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return MibNotificationTypeAlias.getInstanceById(id);
    }
}
