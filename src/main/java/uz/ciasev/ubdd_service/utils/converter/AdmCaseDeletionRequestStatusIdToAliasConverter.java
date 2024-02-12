package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestStatusAlias;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class AdmCaseDeletionRequestStatusIdToAliasConverter implements AttributeConverter<AdmCaseDeletionRequestStatusAlias, Long> {

    @Override
    public Long convertToDatabaseColumn(AdmCaseDeletionRequestStatusAlias alias) {
        if (alias == null) {
            return null;
        }

        return alias.getId();
    }

    @Override
    public AdmCaseDeletionRequestStatusAlias convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }

        return AdmCaseDeletionRequestStatusAlias.getInstanceById(id);
    }
}
