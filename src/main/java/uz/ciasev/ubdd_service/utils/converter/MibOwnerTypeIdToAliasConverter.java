package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.mib.MibOwnerTypeAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class MibOwnerTypeIdToAliasConverter implements AttributeConverter<MibOwnerTypeAlias, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MibOwnerTypeAlias alias) {
        if (alias == null) {
            return null;
        }

        return (int) alias.getId();
    }

    @Override
    public MibOwnerTypeAlias convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return MibOwnerTypeAlias.getInstanceById(id);
    }
}
