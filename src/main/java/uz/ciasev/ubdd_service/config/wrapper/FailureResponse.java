package uz.ciasev.ubdd_service.config.wrapper;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@lombok.Data
@EqualsAndHashCode(callSuper = true)
public class FailureResponse<T> extends ApiResponse {

    public FailureResponse() {
        super(ApiResponse.STATUS_FAILURE);
    }

    private Data<T> data;

    @lombok.Data
    @NoArgsConstructor
    public static class Data<U> {

        private String code;
        private U validationCodes;
        private String message;
        private LocalDateTime timestamp;
        private String exception;
    }
}
