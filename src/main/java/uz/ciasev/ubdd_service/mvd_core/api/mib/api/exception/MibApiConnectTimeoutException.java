package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import org.apache.http.conn.ConnectTimeoutException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class MibApiConnectTimeoutException extends MibApiApplicationException {

    public String getSendResponseMessage() {
        return "MIB SERVERI BILAN ALOQA YO'Q!";
    }

    public String getSendResponseCode() {
        return getDetail();
    }

    public MibApiConnectTimeoutException(ConnectTimeoutException cause) {
        super(ErrorCode.MIB_API_CONNECT_TIMEOUT_ERROR, cause.getMessage());
    }
}
