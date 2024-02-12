package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.action.ActionAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ActionAliasConverter implements AttributeConverter<ActionAlias, String> {
    @Override
    public String convertToDatabaseColumn(ActionAlias alias) {
        if (alias == null) {
            return null;
        }

        if (alias.equals(ActionAlias.UNKNOWN)) {
            throw new RuntimeException("Try to create to database UNKNOWN action");
        }

        return alias.name();
    }

    @Override
    public ActionAlias convertToEntityAttribute(String id) {
        if (id == null) {
            return null;
        }

        try {
            return ActionAlias.valueOf(id);
        } catch (IllegalArgumentException e) {
            return ActionAlias.UNKNOWN;
        }
    }
}
