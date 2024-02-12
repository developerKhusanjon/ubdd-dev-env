package uz.ciasev.ubdd_service.dto.internal;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class RequirementPrintDTO {

    @Valid
    @JsonUnwrapped
    @NotNull(message = ErrorCode.VIOLATOR_REQUIRED)
    private ProtocolGroupByPersonDTO person;

    @NotNull(message = ErrorCode.SEARCH_PARAMS_REQUIRED)
    private Map<String, String> searchParams;
}
