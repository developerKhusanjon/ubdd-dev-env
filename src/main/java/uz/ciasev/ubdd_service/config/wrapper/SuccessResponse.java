package uz.ciasev.ubdd_service.config.wrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SuccessResponse<T> extends ApiResponse {

    private T data;

    public SuccessResponse() {
        super(ApiResponse.STATUS_SUCCESS);
    }
}



