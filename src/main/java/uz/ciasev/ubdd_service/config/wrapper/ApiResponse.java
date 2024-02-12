package uz.ciasev.ubdd_service.config.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse implements Serializable {

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILURE = "failure";

    private String status;
}
