package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ExternalSystemToAliasConverter implements AttributeConverter<ExternalSystemAlias, Long> {
    @Override
    public Long convertToDatabaseColumn(ExternalSystemAlias alias) {
        if (alias == null) {
            return null;
        }
        return alias.getId();
    }

    @Override
    public ExternalSystemAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return ExternalSystemAlias.getInstanceById(id);
    }
}
