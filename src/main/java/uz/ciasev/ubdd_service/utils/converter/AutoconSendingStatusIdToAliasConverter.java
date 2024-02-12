package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.autocon.AutoconSendingStatusAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class AutoconSendingStatusIdToAliasConverter implements AttributeConverter<AutoconSendingStatusAlias, Long> {

    @Override
    public Long convertToDatabaseColumn(AutoconSendingStatusAlias alias) {
        if (alias == null) {
            return null;
        }

        return alias.getId();
    }

    @Override
    public AutoconSendingStatusAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return AutoconSendingStatusAlias.getInstanceById(id);
    }
}
