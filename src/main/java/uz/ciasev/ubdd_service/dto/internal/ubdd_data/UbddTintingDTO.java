package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.ubdd.TintingCategory;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UbddTintingDTO {

    @Size(max = 20, message = ErrorCode.VEHICLE_NUMBER_MIN_MAX_SIZE)
    private String vehicleNumber;

    private LocalDate fromDate;
    private LocalDate toDate;

    @JsonProperty(value = "tintingCategoryId")
    private TintingCategory tintingCategory;

    private Long vehicleId;
}
