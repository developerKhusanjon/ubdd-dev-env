package uz.ciasev.ubdd_service.dto.internal.response;

import lombok.Getter;

@Getter
public class SendResponse {

    protected static String failure = "failure";
    protected static String success = "success";

    private final String status;

    private final String code;

    private final String message;

    private final Object data;

    protected SendResponse(String status, String code, String message, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private static SendResponse failure(String code, String message, Object data) {
        return new SendResponse(failure, code, message, null);
    }

    public static SendResponse success(String code, String message, Object data) {
        return new SendResponse(success, code, message, data);
    }

    public static SendResponse failure(Exception e) {
        return failure("NO CODE", e.getMessage(), null);
    }

    public static SendResponse failure(String code, String message) {
        return failure(code, message, null);
    }

}
