package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipCalculatingModeAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CitizenshipCalculatingModeAliasAliasConverter implements AttributeConverter<CitizenshipCalculatingModeAlias, Long> {

    @Override
    public Long convertToDatabaseColumn(CitizenshipCalculatingModeAlias alias) {
        if (alias == null) {
            return null;
        }
        return alias.getId();
    }

    @Override
    public CitizenshipCalculatingModeAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return CitizenshipCalculatingModeAlias.getInstanceById(id);
    }
}
