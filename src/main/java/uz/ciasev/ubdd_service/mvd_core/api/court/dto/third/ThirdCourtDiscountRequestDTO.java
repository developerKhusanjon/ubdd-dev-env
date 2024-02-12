package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ThirdCourtDiscountRequestDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "firstFineDeadline is null")
    private LocalDate firstFineDeadline; //  "15.05.2023",

    @NotNull(message = "firstTotalFine is null")
    private Long firstTotalFine; //  100000,

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "secondFineDeadline is null")
    private LocalDate secondFineDeadline; //  "31.05.2023",

    @NotNull(message = "secondTotalFine is null")
    private Long secondTotalFine; //  140000
}
