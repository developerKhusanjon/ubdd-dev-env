package uz.ciasev.ubdd_service.entity.ubdd_data.insurance;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UbddInsuranceAccident implements Serializable {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate resolveDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate accidentDate;

    @Size(max = 255, message = ErrorCode.INSURANCE_DATA_MAX_SIZE)
    private String comment;

    private Long payment;
}