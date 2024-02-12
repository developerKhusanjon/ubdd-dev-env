package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class MibAdmTerminationDTO {

    @NotNull(message = ErrorCode.OFFENCE_ID_REQUIRED)
    private Long offenseId;

    @NotNull(message = ErrorCode.RESOLUTION_NUMBER_REQUIRED)
    @JsonProperty(value = "decreeNumber")
    private String resolutionNumber;

    @NotNull(message = "DATE_DOC_FIELD_REQUIRED")
    private LocalDate dateDoc;

    @NotNull(message = "DOC_FIELD_REQUIRED")
    private Integer doc;

    @NotNull(message = ErrorCode.MIB_DOCUMENT_FILE_CONTENT_REQUIRED)
    private String file;

    @Valid
    @NotNull(message = ErrorCode.INSPECTOR_REQUIRED)
    private ExternalInspectorRequestDTO inspector;
}
