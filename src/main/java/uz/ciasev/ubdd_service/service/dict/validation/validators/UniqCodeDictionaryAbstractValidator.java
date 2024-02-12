package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.entity.dict.mib.MibReturnRequestReason;
import uz.ciasev.ubdd_service.entity.dict.requests.DictBaseDTOI;
import uz.ciasev.ubdd_service.exception.dict.DictionaryWithCodeAlreadyExistException;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;

import java.util.Set;

@Component
public abstract class UniqCodeDictionaryAbstractValidator implements DictionaryCreateValidator<AbstractDict, DictBaseDTOI> {

    Set<Class> excludeClasses = Set.of(
//            Region.class,
//            District.class,
//            Department.class,
//            Organ.class,
//            Article.class,
//            ArticlePart.class,
            MibReturnRequestReason.class,
            Mtp.class
    );

    @Override
    public Class<AbstractDict> getValidatedType() {
        return AbstractDict.class;
    }

    @Override
    public Class<DictBaseDTOI> getRequestType() {
        return DictBaseDTOI.class;
    }

    protected void validate(DictionaryServiceWithRepository<AbstractDict> service, DictBaseDTOI request, Specification<AbstractDict> spec) {
        if (excludeClasses.contains(service.getEntityClass())) {
            return;
        }

        boolean codeNoExists = service.getRepository().count(spec) == 0;

        if (codeNoExists) {
            return;
        }
        throw new DictionaryWithCodeAlreadyExistException(service.getEntityClass(), request.getCode());
    }
}
