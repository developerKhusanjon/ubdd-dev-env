package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseCaseRequestDTO;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class FirstCourtAdmCaseRequestDTO extends CourtBaseCaseRequestDTO {

    private Long court;
    private Long instance = 1L;

    private String crimeCaseNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate crimeCaseDate;
    private Long admissionType;
    private Long admissionSubType;
    private Long claimPlaceType;
    private String location;
    private String comments;
}
