package uz.ciasev.ubdd_service.exception.notfound;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class ApplicationFileNotFoundException extends ApplicationException {

    public ApplicationFileNotFoundException(String uri) {
        super(
                HttpStatus.NOT_FOUND,
                ErrorCode.FILE_NOT_FOUND,
                String.format("File with path %s not found on server", uri)
        );
    }
}
