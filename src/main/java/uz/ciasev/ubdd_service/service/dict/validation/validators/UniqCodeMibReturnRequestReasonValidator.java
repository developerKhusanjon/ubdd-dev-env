package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.mib.MibReturnRequestReason;
import uz.ciasev.ubdd_service.entity.dict.requests.DictBaseDTOI;
import uz.ciasev.ubdd_service.exception.dict.DictionaryWithCodeAlreadyExistException;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;

@Component
public class UniqCodeMibReturnRequestReasonValidator
        implements DictionaryCreateValidator<MibReturnRequestReason, DictBaseDTOI>, DictionaryUpdateValidator<MibReturnRequestReason, DictBaseDTOI> {

    @Override
    public void validate(DictionaryServiceWithRepository<MibReturnRequestReason> service, DictBaseDTOI request) {
        Specification<MibReturnRequestReason> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withCode(request.getCode())
        );

        validate(service, request, spec);
    }

    @Override
    public void validate(DictionaryServiceWithRepository<MibReturnRequestReason> service, MibReturnRequestReason entity, DictBaseDTOI request) {

        if (entity.getCode().equals(request.getCode())) {
            return;
        }

        Specification<MibReturnRequestReason> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withCode(request.getCode()),
                Specification.not(DictionarySpecifications.withId(entity.getId()))
        );

        validate(service, request, spec);
    }

    @Override
    public Class<MibReturnRequestReason> getValidatedType() {
        return MibReturnRequestReason.class;
    }

    @Override
    public Class<DictBaseDTOI> getRequestType() {
        return DictBaseDTOI.class;
    }

    private void validate(DictionaryServiceWithRepository<MibReturnRequestReason> service, DictBaseDTOI request, Specification<MibReturnRequestReason> spec) {

        boolean codeNoExists = service.getRepository().count(spec) == 0;

        if (codeNoExists) {
            return;
        }
        throw new DictionaryWithCodeAlreadyExistException(service.getEntityClass(), request.getCode());
    }
}
