package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.AbstractManualIdDictEntity;
import uz.ciasev.ubdd_service.entity.dict.requests.ManualIdDictCreateDTOI;
import uz.ciasev.ubdd_service.exception.dict.DictionaryWithIdAlreadyExistException;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;

@Component
public class UniqIdManualIdDictionanaryCreateValidator implements DictionaryCreateValidator<AbstractManualIdDictEntity, ManualIdDictCreateDTOI> {

    @Override
    public void validate(DictionaryServiceWithRepository<AbstractManualIdDictEntity> service, ManualIdDictCreateDTOI request) {
        Specification<AbstractManualIdDictEntity> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withId(request.getId())
        );

        validate(service, request, spec);
    }

    @Override
    public Class<AbstractManualIdDictEntity> getValidatedType() {
        return AbstractManualIdDictEntity.class;
    }

    @Override
    public Class<ManualIdDictCreateDTOI> getRequestType() {
        return ManualIdDictCreateDTOI.class;
    }

    private void validate(DictionaryServiceWithRepository<AbstractManualIdDictEntity> service, ManualIdDictCreateDTOI request, Specification<AbstractManualIdDictEntity> spec) {

        boolean codeNoExists = service.getRepository().count(spec) == 0;

        if (codeNoExists) {
            return;
        }
        throw new DictionaryWithIdAlreadyExistException(service.getEntityClass(), request.getId());
    }
}
