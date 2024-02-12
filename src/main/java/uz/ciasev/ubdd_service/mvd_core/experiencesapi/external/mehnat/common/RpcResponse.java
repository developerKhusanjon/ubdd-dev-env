package uz.ciasev.ubdd_service.mvd_core.experiencesapi.external.mehnat.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RpcResponse<T> {

    private Long id;
    private String jsonrpc;
    private Error error;
    private Result<T> result;

    @Data
    public static class Result<T> {
        private Integer code;
        private T data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Error {
        private Integer code;
        private String message;
    }

}
