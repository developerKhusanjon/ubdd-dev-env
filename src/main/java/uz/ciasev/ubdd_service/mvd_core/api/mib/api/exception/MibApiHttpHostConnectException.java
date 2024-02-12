package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import org.apache.http.conn.HttpHostConnectException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class MibApiHttpHostConnectException extends MibApiApplicationException {

    public String getSendResponseMessage() {
        return "MIB SERVERI BILAN ALOQA YO'Q!";
    }

    public String getSendResponseCode() {
        return getDetail();
    }

    public MibApiHttpHostConnectException(HttpHostConnectException cause) {
        super(ErrorCode.MIB_API_HTTP_HOST_CONNECT_ERROR, cause.getMessage());
    }
}
