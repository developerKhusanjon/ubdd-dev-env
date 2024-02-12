package uz.ciasev.ubdd_service.exception;

public class PrefixException extends ApplicationException {

    public PrefixException(ApplicationException e, String codePostfix) {
//        HttpStatus status, String code, String detail, Throwable cause
        super(e.getStatus(), codePostfix + "_" + e.getCode(), e.getDetail(), e.getCause());
    }
}
