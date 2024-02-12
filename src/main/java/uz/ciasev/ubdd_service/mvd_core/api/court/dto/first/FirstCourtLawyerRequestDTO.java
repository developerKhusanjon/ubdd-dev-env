package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FirstCourtLawyerRequestDTO {

    private List<Long> violators;
    private List<Long> victims;
    private Long lawyerId;
    @JsonProperty("pinpp")
    private Long pinpp;
    private String lawyerLastName;
    private String lawyerFirstName;
    private String lawyerMiddleName;
    private String certificateNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateGivenDate;
}