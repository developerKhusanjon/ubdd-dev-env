package uz.ciasev.ubdd_service.service.file;

import uz.ciasev.ubdd_service.dto.internal.dict.request.FileFormatCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.request.FileFormatUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.FileFormat;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;

public interface FileFormatService extends DictionaryCRUDService<FileFormat, FileFormatCreateRequestDTO, FileFormatUpdateRequestDTO> {

    FileFormat findByExtension(String extension);

    FileFormat calcForUrl(String url);
}
