package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.history.OrganAccountSettingsHistoricAction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrganAccountSettingsHistoricActionAliasConverter implements AttributeConverter<OrganAccountSettingsHistoricAction, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrganAccountSettingsHistoricAction alias) {
        if (alias == null) {
            return null;
        }

        return alias.getId();
    }

    @Override
    public OrganAccountSettingsHistoricAction convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return OrganAccountSettingsHistoricAction.getInstanceById(id);
    }
}
