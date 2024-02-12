package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.history.TransDictAdminHistoricAction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TransDictAdminHistoricActionAliasConverter implements AttributeConverter<TransDictAdminHistoricAction, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TransDictAdminHistoricAction alias) {
        if (alias == null) {
            return null;
        }

        return alias.getId();
    }

    @Override
    public TransDictAdminHistoricAction convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return TransDictAdminHistoricAction.getInstanceById(id);
    }
}
