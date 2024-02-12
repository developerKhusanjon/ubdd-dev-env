package uz.ciasev.ubdd_service.utils.converter;

import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseMovementStatusAlias;

import javax.persistence.AttributeConverter;

public class AdmCaseMovementStatusAliasConverter implements AttributeConverter<AdmCaseMovementStatusAlias, Long> {

    @Override
    public Long convertToDatabaseColumn(AdmCaseMovementStatusAlias alias) {
        if (alias == null) return null;

        return alias.getId();
    }

    @Override
    public AdmCaseMovementStatusAlias convertToEntityAttribute(Long id) {
        if (id==null) return null;

        return AdmCaseMovementStatusAlias.getInstanceById(id);
    }
}
