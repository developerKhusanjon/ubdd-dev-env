package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class DecisionTypeIdToAliasConverter implements AttributeConverter<DecisionTypeAlias, Long> {

    @Override
    public Long convertToDatabaseColumn(DecisionTypeAlias alias) {
        if (alias == null) {
            return null;
        }

        return alias.getId();
    }

    @Override
    public DecisionTypeAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return DecisionTypeAlias.getInstanceById(id);
    }
}
