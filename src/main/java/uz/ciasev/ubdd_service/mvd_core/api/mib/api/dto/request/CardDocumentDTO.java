package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardDocumentDTO {

    @JsonProperty("file_type_id")
    private Long documentTypeId;

    @JsonProperty("file_name")
    private String documentName;

    @JsonProperty("file")
    private String content;
}
