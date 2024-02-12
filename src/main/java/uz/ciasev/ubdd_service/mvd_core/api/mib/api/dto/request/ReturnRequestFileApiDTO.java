package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReturnRequestFileApiDTO {

    @JsonProperty("name")
    public final String name = "return-request.pdf";

    @JsonProperty("contentType")
    public final String contentType = "application/pdf";

    @JsonProperty("hash")
    private String md5Hash;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("data")
    private String content;


}
