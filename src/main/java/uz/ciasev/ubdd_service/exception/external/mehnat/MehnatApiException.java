package uz.ciasev.ubdd_service.exception.external.mehnat;

import lombok.Getter;

@Getter
public class MehnatApiException extends RuntimeException {

    private Integer code;

    public MehnatApiException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public MehnatApiException(Integer code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
}
