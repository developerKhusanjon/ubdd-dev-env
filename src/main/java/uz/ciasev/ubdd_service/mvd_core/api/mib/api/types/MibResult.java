package uz.ciasev.ubdd_service.mvd_core.api.mib.api.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;

@Data
@Builder
@AllArgsConstructor
public class MibResult {

    private MibSendStatus status;

    private Long requestId;

    private String message;
}
