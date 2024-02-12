package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.entity.dict.requests.DictUpdateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;


@Component
public class UniqCodeMtpUpdateValidator extends UniqCodeMtpAbstractValidator implements DictionaryUpdateValidator<Mtp, DictUpdateDTOI> {

    @Override
    public void validate(DictionaryServiceWithRepository<Mtp> service, Mtp entity, DictUpdateDTOI request) {
        Specification<Mtp> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withDistrictId(entity.getDistrictId()),
                DictionarySpecifications.withCode(request.getCode()),
                Specification.not(DictionarySpecifications.withId(entity.getId()))
        );

        validate(service, request.getCode(), entity.getDistrictId(), spec);
    }

    @Override
    public Class<Mtp> getValidatedType() {
        return Mtp.class;
    }

    @Override
    public Class<DictUpdateDTOI> getRequestType() {
        return DictUpdateDTOI.class;
    }
}
