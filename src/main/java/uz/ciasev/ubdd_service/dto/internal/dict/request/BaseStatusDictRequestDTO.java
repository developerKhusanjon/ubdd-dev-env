package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseStatusDictRequestDTO extends BaseDictRequestDTO {

    @NotNull(message = ErrorCode.COLOR_REQUIRED)
    @Size(min = 1, max = 20, message = ErrorCode.COLOR_MIN_MAX_LENGTH)
    private String color;
}
