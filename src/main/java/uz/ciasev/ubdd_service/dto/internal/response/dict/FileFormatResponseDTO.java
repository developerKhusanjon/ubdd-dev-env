package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.FileFormat;

@Getter
public class FileFormatResponseDTO extends DictResponseDTO {
    private final String extension;
    private final Long maxSize;

    public FileFormatResponseDTO(FileFormat entity) {
        super(entity);
        this.extension = entity.getExtension();
        this.maxSize = entity.getMaxSizeInKB();
    }
}
