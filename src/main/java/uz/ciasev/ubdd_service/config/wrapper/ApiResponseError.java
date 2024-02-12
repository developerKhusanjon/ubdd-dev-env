package uz.ciasev.ubdd_service.config.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseError {

    private String status;
    private Body data;

    @Data
    public static class Body {
        private String code;
        private String message;
        private LocalDateTime timestamp;
        private String exception;
    }
}
