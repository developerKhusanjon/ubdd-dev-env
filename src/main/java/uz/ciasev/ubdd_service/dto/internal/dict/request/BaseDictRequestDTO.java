package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;
import uz.ciasev.ubdd_service.utils.validator.ValidMultiLanguage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BaseDictRequestDTO {

    @NotNull(message = ErrorCode.NAME_REQUIRED)
    @ValidMultiLanguage
    private MultiLanguage name;

    @NotNull(message = ErrorCode.CODE_REQUIRED)
    @Size(min= 1, max = 5, message = ErrorCode.CODE_MIN_MAX_SIZE)
    private String code;
}
