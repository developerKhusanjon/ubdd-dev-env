package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.history.DictAdminHistoricAction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DictAdminHistoricActionAliasConverter implements AttributeConverter<DictAdminHistoricAction, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DictAdminHistoricAction alias) {
        if (alias == null) {
            return null;
        }

        return alias.getId();
    }

    @Override
    public DictAdminHistoricAction convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return DictAdminHistoricAction.getInstanceById(id);
    }
}
