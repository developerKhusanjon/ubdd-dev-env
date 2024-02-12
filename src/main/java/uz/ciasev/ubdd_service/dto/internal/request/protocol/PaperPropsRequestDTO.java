package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PaperPropsRequestDTO {

    @Size(max = 18, message = ErrorCode.PAPER_PROTOCOL_BLANK_SERIES_MAX_SIZE)
    @NotNull(message = ErrorCode.PAPER_PROTOCOL_BLANK_SERIES_REQUIRED)
    private String blankSeries;

    @Size(max = 45, message = ErrorCode.PAPER_PROTOCOL_BLANK_NUMBER_MAX_SIZE)
    @NotNull(message = ErrorCode.PAPER_PROTOCOL_BLANK_NUMBER_REQUIRED)
    private String blankNumber;
}
