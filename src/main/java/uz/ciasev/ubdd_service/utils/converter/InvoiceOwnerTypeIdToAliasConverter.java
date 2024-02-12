package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class InvoiceOwnerTypeIdToAliasConverter implements AttributeConverter<InvoiceOwnerTypeAlias, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InvoiceOwnerTypeAlias alias) {
        if (alias == null) {
            return null;
        }

        return (int) alias.getId();
    }

    @Override
    public InvoiceOwnerTypeAlias convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return InvoiceOwnerTypeAlias.getInstanceById(id);
    }
}
