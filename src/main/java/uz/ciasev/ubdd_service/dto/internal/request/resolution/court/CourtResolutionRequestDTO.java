package uz.ciasev.ubdd_service.dto.internal.request.resolution.court;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.ResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourtResolutionRequestDTO implements ResolutionRequestDTO {

    @NotNull(message = ErrorCode.JUDGE_INFO_REQUIRED)
    private String judgeInfo;

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    private Region region;

    private District district;

    private Long claimId;

    // инстанция суда
    private Long instance;

    // Признак использования конферентсвязи
    private Boolean isUseVcc;

    @NotNull(message = ErrorCode.COURT_CASE_NUMBER_REQUIRED)
    private String courtNumber;

    @NotNull(message = ErrorCode.COURT_RESOLUTION_TIME_REQUIRED)
    @Builder.Default
    private LocalDateTime resolutionTime = LocalDateTime.now();

    @NotNull(message = ErrorCode.DECISIONS_REQUIRED)
    @NotEmpty(message = ErrorCode.DECISIONS_REQUIRED)
    @Valid
    @Builder.Default
    private List<CourtDecisionRequestDTO> decisions = new ArrayList<>();

    @Valid
    @Builder.Default
    private List<CourtEvidenceDecisionRequestDTO> evidenceDecisions = new ArrayList<>();

    @Override
    public ResolutionCreateRequest buildResolution() {
        ResolutionCreateRequest resolution = new ResolutionCreateRequest();
        resolution.setResolutionTime(this.resolutionTime);
        resolution.setClaimId(this.claimId);

        return resolution;
    }


    @Override
    public List<CourtDecisionRequestDTO> getDecisions() {
        return Optional.ofNullable(this.decisions).orElseGet(List::of)
                .stream()
                .peek(d -> d.setExecutionFromDate(this.getResolutionTime().toLocalDate()))
                .collect(Collectors.toList());
    }

}
