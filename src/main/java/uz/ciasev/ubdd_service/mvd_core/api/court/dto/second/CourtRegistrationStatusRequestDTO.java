package uz.ciasev.ubdd_service.mvd_core.api.court.dto.second;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class CourtRegistrationStatusRequestDTO implements CourtBaseDTO {

    @NotNull(message = "CASE_ID_REQUIRED")
    private Long caseId;

    @NotNull(message = "CLAIM_ID_REQUIRED")
    private Long claimId;

    @NotNull(message = "STATUS_ID_REQUIRED")
    private CourtStatus status;

    private String regNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate declinedDate;

    private List<Long> declinedReasons;
}
