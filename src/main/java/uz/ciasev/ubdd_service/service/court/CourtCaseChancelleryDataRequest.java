package uz.ciasev.ubdd_service.service.court;

import lombok.Builder;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CourtCaseChancelleryDataRequest {
    private Long caseId;
    private Long claimId;
    private String registrationNumber;
    private LocalDate registrationDate;
    private LocalDate declinedDate;
    private List<Long> declinedReasons;
    private CourtStatus status;
    private Long regionId;
    private Long districtId;
}
