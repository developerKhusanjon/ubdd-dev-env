package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
public class MibDocumentDTO {

    @NotNull(message = ErrorCode.MIB_DOCUMENT_FILE_NAME_REQUIRED)
    private String name;

    private Integer fileType;
    private Long size;

    @NotNull(message = ErrorCode.MIB_DOCUMENT_FILE_CONTENT_REQUIRED)
    private String content;

    private String contentType;
}
