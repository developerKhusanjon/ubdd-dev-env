package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.exception.dict.MtpWithCodeAlreadyExistsInDistrictException;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;

@Component
public abstract class UniqCodeMtpAbstractValidator {

    protected void validate(DictionaryServiceWithRepository<Mtp> service, String code, Long districtId, Specification<Mtp> spec) {
        boolean codeNoExists = service.getRepository().count(spec) == 0;

        if (codeNoExists) {
            return;
        }
        throw new MtpWithCodeAlreadyExistsInDistrictException(code, districtId);
    }
}
