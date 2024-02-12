package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolStatisticData;
import uz.ciasev.ubdd_service.entity.dict.statistic.StatisticReasonViolation;
import uz.ciasev.ubdd_service.entity.dict.statistic.StatisticReportType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolStatisticDataRequestDTO implements ProtocolDataDTO<ProtocolStatisticData> {

    @NotNull(message = ErrorCode.STATISTIC_REPORT_TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.STATISTIC_REPORT_TYPE_DEACTIVATED)
    @JsonProperty(value = "reportTypeId")
    private StatisticReportType reportType;

    @NotNull(message = ErrorCode.STATISTIC_REASON_VIOLATION_REQUIRED)
    @ActiveOnly(message = ErrorCode.STATISTIC_REASON_VIOLATION_DEACTIVATED)
    @JsonProperty(value = "reasonViolationId")
    private StatisticReasonViolation reasonViolation;

    @NotBlank(message = ErrorCode.STATISTIC_ORGANIZATION_CODE_REQUIRED)
    @Size(max = 50, message = ErrorCode.ORGANIZATION_CODE_MAX_LENGTH)
    private String organizationCode;

    @Override
    public ProtocolStatisticData build(Protocol protocol) {
        ProtocolStatisticData rsl = new ProtocolStatisticData();
        return apply(rsl, protocol);
    }

    @Override
    public ProtocolStatisticData apply(ProtocolStatisticData data, Protocol protocol) {
        data.setProtocol(protocol);
        data.setReportType(this.reportType);
        data.setReasonViolation(this.reasonViolation);
        data.setOrganizationCode(this.organizationCode);
        return data;
    }
}
