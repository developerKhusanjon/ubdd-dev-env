package uz.ciasev.ubdd_service.dto.internal.request;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class RequirementRequestDTO {

    @NotNull(message = ErrorCode.PROTOCOL_IDS_REQUIRED)
    private List<Long> ids = new ArrayList<>();
}
