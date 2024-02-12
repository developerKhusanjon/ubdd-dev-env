package uz.ciasev.ubdd_service.mvd_core.experiencesapi.external.mehnat.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RpcRequest<T> {

    @JsonProperty("id")
    private final Long id = 1L;
    @JsonProperty("jsonrpc")
    private final String jsonrpc = "2.0";
    private String method;
    private T params;

}
