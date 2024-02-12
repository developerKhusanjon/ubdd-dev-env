package uz.ciasev.ubdd_service.config.controller_advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import uz.ciasev.ubdd_service.config.wrapper.FailureResponse;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class EmiApiExceptionHandler extends CiasevRestExceptionHandler {

    protected ResponseEntity getFail(HttpStatus status,
                                     String code,
                                     String message,
                                     List<String> validationCodes,
                                     String ex) {

        FailureResponse<List<String>> fail = new FailureResponse<>();
        FailureResponse.Data<List<String>> data = new FailureResponse.Data<>();

        data.setTimestamp(LocalDateTime.now());
        data.setCode(code);
        data.setMessage(message);
        data.setValidationCodes(validationCodes);
        data.setException(ex);

        fail.setData(data);

        return new ResponseEntity<>(fail, status);
    }
}
