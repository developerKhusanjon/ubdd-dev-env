package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.Bank;
import uz.ciasev.ubdd_service.entity.dict.requests.BankDTOI;
import uz.ciasev.ubdd_service.exception.dict.BankWithMfoAlreadyExistException;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;


@Component
public class UniqMfoBankValidator implements DictionaryCreateValidator<Bank, BankDTOI>, DictionaryUpdateValidator<Bank, BankDTOI> {

    @Override
    public void validate(DictionaryServiceWithRepository<Bank> service, BankDTOI request) {
        Specification<Bank> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withMfo(request.getMfo())
        );

        validate(service, request, spec);
    }

    @Override
    public void validate(DictionaryServiceWithRepository<Bank> service, Bank entity, BankDTOI request) {
        Specification<Bank> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withMfo(request.getMfo()),
                Specification.not(DictionarySpecifications.withId(entity.getId()))
        );

        validate(service, request, spec);
    }

    @Override
    public Class<Bank> getValidatedType() {
        return Bank.class;
    }

    @Override
    public Class<BankDTOI> getRequestType() {
        return BankDTOI.class;
    }

    private void validate(DictionaryServiceWithRepository<Bank> service, BankDTOI request, Specification<Bank> spec) {
        boolean codeNoExists = service.getRepository().count(spec) == 0;

        if (codeNoExists) {
            return;
        }
        throw new BankWithMfoAlreadyExistException(service.getEntityClass(), request.getMfo());
    }
}
