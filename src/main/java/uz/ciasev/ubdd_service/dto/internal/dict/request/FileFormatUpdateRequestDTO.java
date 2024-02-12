package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.FileFormat;
import uz.ciasev.ubdd_service.entity.dict.requests.FileFormatUpdateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
public class FileFormatUpdateRequestDTO extends BaseDictRequestDTO implements FileFormatUpdateDTOI, DictUpdateRequest<FileFormat> {
    @NotNull(message = ErrorCode.MAX_SIZE_REQUIRED)
    @Max(value = 2147483647, message = ErrorCode.MAX_SIZE_MIN_MAX_LENGTH)
    @Min(value = 1, message = ErrorCode.MAX_SIZE_MIN_MAX_LENGTH)
    private Long maxSize;

    @Override
    public void applyToOld(FileFormat entity) {
        entity.update(this);
    }
}
