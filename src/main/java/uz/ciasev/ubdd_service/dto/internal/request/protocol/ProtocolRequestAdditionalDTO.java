package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import lombok.Data;

import javax.validation.Valid;

@Data
public class ProtocolRequestAdditionalDTO {

    @Valid
    private ProtocolStatisticDataRequestDTO statistic;

    @Valid
    private ProtocolUbddDataRequestUbddDTO ubdd;

    @Valid
    private ProtocolUbddDataRequestTransportDTO transport;
}
