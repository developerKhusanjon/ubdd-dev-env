package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import java.net.SocketTimeoutException;

public class MibApiSocketTimeoutException extends MibApiApplicationException {

    public String getSendResponseMessage() {
        return "MIBNING MIB-PURTAL TIZIMI BIR DAQIQADA JAVOB BERMADI (MIB HARAKATLARIDAGI RO‘YXATNI TEKShIRING)";
    }

    public String getSendResponseCode() {
        return getDetail();
    }

    public MibApiSocketTimeoutException(SocketTimeoutException cause) {
        super(ErrorCode.MIB_API_SOCKET_TIMEOUT_ERROR, cause.getMessage());
    }
}
