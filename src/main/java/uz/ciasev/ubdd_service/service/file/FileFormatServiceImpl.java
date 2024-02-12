package uz.ciasev.ubdd_service.service.file;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.FileFormatCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.FileFormatUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.FileFormatResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.FileFormat;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.FileFormatRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Service
@RequiredArgsConstructor
@Getter
public class FileFormatServiceImpl extends AbstractDictionaryCRUDService<FileFormat, FileFormatCreateRequestDTO, FileFormatUpdateRequestDTO>
        implements FileFormatService {

    private final String subPath = "file-formats";
    private final TypeReference<FileFormatCreateRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<FileFormatUpdateRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final FileFormatRepository repository;
    private final Class<FileFormat> entityClass = FileFormat.class;

    @Override
    public FileFormat findByExtension(String extension) {
        return repository
                .findByExtension(extension)
                .orElseThrow(() -> new EntityByParamsNotFound(FileFormat.class, "extension", extension));
    }

    @Override
    public FileFormat calcForUrl(String url)  {
        String[] strings = url.split("\\.");
        String code = strings[strings.length - 1];
        return findByExtension(code.toUpperCase());
    }

    @Override
    public FileFormatResponseDTO buildResponseDTO(FileFormat entity) {
        return new FileFormatResponseDTO(entity);
    }

}
