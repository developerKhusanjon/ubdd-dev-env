package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.FileFormat;
import uz.ciasev.ubdd_service.entity.dict.requests.FileFormatCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Getter
public class FileFormatCreateRequestDTO extends FileFormatUpdateRequestDTO implements FileFormatCreateDTOI, DictCreateRequest<FileFormat> {
    @NotNull(message = ErrorCode.EXTENSION_REQUIRED)
    @Size(min = 1, max = 10, message = ErrorCode.EXTENSION_MIN_MAX_LENGTH)
    private String extension;

    @Override
    public void applyToNew(FileFormat entity) {
        entity.construct(this);
    }
}
