package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class PaperProtocolRequestDTO {

    @NotNull(message = ErrorCode.PAPER_PROTOCOL_USER_REQUIRED)
    private Long userId;

    @NotNull
    @Valid
    @JsonUnwrapped
    private PaperPropsRequestDTO paperProps;

    @NotNull
    @Valid
    @JsonUnwrapped
    private ProtocolRequestDTO protocol;

    public String getBlankSeries() {
        return paperProps.getBlankSeries();
    }

    public String getBlankNumber() {
        return paperProps.getBlankNumber();
    }
}
