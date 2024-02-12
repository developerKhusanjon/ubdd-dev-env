package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.entity.dict.requests.MtpCreateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;


@Component
public class UniqCodeCreateMtpValidator extends UniqCodeMtpAbstractValidator implements DictionaryCreateValidator<Mtp, MtpCreateDTOI> {

    @Override
    public void validate(DictionaryServiceWithRepository<Mtp> service, MtpCreateDTOI request) {
        Specification<Mtp> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withDistrictId(request.getDistrict().getId()),
                DictionarySpecifications.withCode(request.getCode())
        );

        validate(service, request.getCode(), request.getDistrict().getId(), spec);
    }

    @Override
    public Class<Mtp> getValidatedType() {
        return Mtp.class;
    }

    @Override
    public Class<MtpCreateDTOI> getRequestType() {
        return MtpCreateDTOI.class;
    }
}
