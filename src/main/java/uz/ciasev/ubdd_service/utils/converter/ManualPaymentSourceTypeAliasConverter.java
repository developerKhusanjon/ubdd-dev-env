package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceTypeAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ManualPaymentSourceTypeAliasConverter implements AttributeConverter<ManualPaymentSourceTypeAlias, Long> {

    @Override
    public Long convertToDatabaseColumn(ManualPaymentSourceTypeAlias alias) {
        if (alias == null) {
            return null;
        }
        return alias.getId();
    }

    @Override
    public ManualPaymentSourceTypeAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return ManualPaymentSourceTypeAlias.getInstanceById(id);
    }
}
