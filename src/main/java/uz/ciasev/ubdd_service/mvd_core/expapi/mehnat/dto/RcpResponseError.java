package uz.ciasev.ubdd_service.mvd_core.expapi.mehnat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RcpResponseError {

    private Integer code;
    private String message;
    private LocalDateTime timestamp;
    private String exception;

}
