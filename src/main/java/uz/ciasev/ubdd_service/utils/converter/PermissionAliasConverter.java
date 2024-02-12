package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.permission.PermissionAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PermissionAliasConverter implements AttributeConverter<PermissionAlias, String> {
    @Override
    public String convertToDatabaseColumn(PermissionAlias alias) {
        if (alias == null) {
            return null;
        }

        if (alias.equals(PermissionAlias.UNKNOWN)) {
            throw new RuntimeException("Try to create to database UNKNOWN permission");
        }

        return alias.name();
    }

    @Override
    public PermissionAlias convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        try {
            return PermissionAlias.valueOf(id);
        } catch (IllegalArgumentException e) {
            return PermissionAlias.UNKNOWN;
        }
    }
}
