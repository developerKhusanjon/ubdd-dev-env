package uz.ciasev.ubdd_service.mvd_core.experiencesapi.advice;


import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.ciasev.ubdd_service.exception.external.mehnat.MehnatApiException;
import uz.ciasev.ubdd_service.mvd_core.expapi.mehnat.dto.RcpResponseError;

import java.time.LocalDateTime;

@Order(-1)
@ControllerAdvice(basePackages = {"uz.ciasev.ubdd_service.mvd_core.experiencesapi"})
public class CitizenExperiencesControllerAdvise {

    @ExceptionHandler(MehnatApiException.class)
    private ResponseEntity handleMehnatApiException(MehnatApiException e) {

        RcpResponseError errorResponse = new RcpResponseError();

        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setCode(e.getCode() == 404 ? null : e.getCode());
        errorResponse.setMessage(e.getMessage());
//        errorResponse.setException(e.getCause().getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getCode()));
    }

}
