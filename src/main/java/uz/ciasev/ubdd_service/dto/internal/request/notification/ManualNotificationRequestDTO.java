package uz.ciasev.ubdd_service.dto.internal.request.notification;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ManualNotificationRequestDTO {

    @NotNull(message = ErrorCode.SENT_DATE_REQUIRED)
    private LocalDate sentDate;

    @NotNull(message = ErrorCode.RECEIVE_DATE_REQUIRED)
    private LocalDate receiveDate;

    @NotNull(message = ErrorCode.NUMBER_REQUIRED)
    @Size(max = 32, message = ErrorCode.NUMBER_MIN_MAX_LENGTH)
    private String number;

    @NotNull(message = ErrorCode.TEXT_REQUIRED)
    @Size(max = 500, message = ErrorCode.TEXT_LENGTH)
    private String text;

    @NotNull(message = ErrorCode.FILE_URI_REQUIRED)
    @ValidFileUri(allow = FileFormatAlias.PDF, message = ErrorCode.FILE_URI_INVALID)
    private String fileUri;
}
