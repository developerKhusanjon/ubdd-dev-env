package uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.EvidenceDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.ResolutionRequestDTO;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CriminalCaseResolutionRequestDTO implements ResolutionRequestDTO {

    private LocalDateTime resolutionTime;

    private String fileUri;

    private List<CriminalCaseDecisionRequestDTO> decisions;

    @Override
    public ResolutionCreateRequest buildResolution() {
        ResolutionCreateRequest resolution = new ResolutionCreateRequest();
        resolution.setResolutionTime(this.resolutionTime);
        resolution.setCourtDecisionUri(fileUri);

        return resolution;
    }
}
