package uz.ciasev.ubdd_service.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class ApplicationException extends RuntimeException {

    private final HttpStatus status;
    private final String code;
    private final String detail;

    protected ApplicationException(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
        this.detail = null;
    }

    protected ApplicationException(HttpStatus status, String code, String detail) {
        this.status = status;
        this.code = code;
        this.detail = detail;
    }

    protected ApplicationException(HttpStatus status, String code, String detail, Throwable cause) {
        super(cause);
        this.status = status;
        this.code = code;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        String detail = getDetail();
        if (detail != null && !detail.isBlank()) {
            return String.format("%s: %s", code, detail);
        }

        return String.format("%s with code %s", this.getClass().getSimpleName(), code);
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
