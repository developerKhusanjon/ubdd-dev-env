package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.requests.DictBaseDTOI;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;


@Component
public class UniqCodeDictionaryCreateValidator
        extends UniqCodeDictionaryAbstractValidator
        implements DictionaryCreateValidator<AbstractDict, DictBaseDTOI>, DictionaryUpdateValidator<AbstractDict, DictBaseDTOI> {

    @Override
    public void validate(DictionaryServiceWithRepository<AbstractDict> service, DictBaseDTOI request) {
        Specification<AbstractDict> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withCode(request.getCode()),
                DictionarySpecifications.withIsActive(true)
        );

        validate(service, request, spec);
    }

    @Override
    public void validate(DictionaryServiceWithRepository<AbstractDict> service, AbstractDict entity, DictBaseDTOI request) {

        if (entity.getCode().equals(request.getCode())) {
            return;
        }

        Specification<AbstractDict> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withCode(request.getCode()),
                DictionarySpecifications.withIsActive(true),
                Specification.not(DictionarySpecifications.withId(entity.getId()))
        );

        validate(service, request, spec);
    }
}
