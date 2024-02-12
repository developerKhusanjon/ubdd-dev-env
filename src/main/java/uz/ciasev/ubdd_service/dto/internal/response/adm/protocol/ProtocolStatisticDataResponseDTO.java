package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolStatisticData;

import java.time.LocalDateTime;

@Data
public class ProtocolStatisticDataResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime editedTime;
    private Long statisticReportTypeId;
    private Long statisticReasonViolationId;
    private String organizationCode;

    public ProtocolStatisticDataResponseDTO(ProtocolStatisticData data) {
        this.id = data.getId();
        this.createdTime = data.getCreatedTime();
        this.editedTime = data.getEditedTime();
        this.statisticReportTypeId = data.getReportType().getId();
        this.statisticReasonViolationId = data.getReasonViolation().getId();
        this.organizationCode = data.getOrganizationCode();
    }
}
