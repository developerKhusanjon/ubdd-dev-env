package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class MibCaseStatusTypeIdToAliasConverter implements AttributeConverter<MibCaseStatusAlias, Long> {

    @Override
    public Long convertToDatabaseColumn(MibCaseStatusAlias alias) {
        if (alias == null) {
            return null;
        }

        return alias.getId();
    }

    @Override
    public MibCaseStatusAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return MibCaseStatusAlias.getInstanceById(id);
    }
}
