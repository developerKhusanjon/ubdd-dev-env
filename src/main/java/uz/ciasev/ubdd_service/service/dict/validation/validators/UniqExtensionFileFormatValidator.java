package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.FileFormat;
import uz.ciasev.ubdd_service.entity.dict.requests.FileFormatCreateDTOI;
import uz.ciasev.ubdd_service.exception.dict.FileFormatWithExtensionAlreadyExistException;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;


@Component
public class UniqExtensionFileFormatValidator implements DictionaryCreateValidator<FileFormat, FileFormatCreateDTOI> {

    @Override
    public void validate(DictionaryServiceWithRepository<FileFormat> service, FileFormatCreateDTOI request) {
        Specification<FileFormat> spec = SpecificationsCombiner.andAll(
                DictionarySpecifications.withExtension(request.getExtension())
        );

        validate(service, request, spec);
    }

    @Override
    public Class<FileFormat> getValidatedType() {
        return FileFormat.class;
    }

    @Override
    public Class<FileFormatCreateDTOI> getRequestType() {
        return FileFormatCreateDTOI.class;
    }

    private void validate(DictionaryServiceWithRepository<FileFormat> service, FileFormatCreateDTOI request, Specification<FileFormat> spec) {
        boolean codeNoExists = service.getRepository().count(spec) == 0;

        if (codeNoExists) {
            return;
        }
        throw new FileFormatWithExtensionAlreadyExistException(service.getEntityClass(), request.getExtension());
    }
}
