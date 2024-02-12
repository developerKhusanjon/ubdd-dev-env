package uz.ciasev.ubdd_service.dto.internal.request.settings;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
public class OrganAccountSettingsUpdateRequestDTO {

    @NotNull(message = ErrorCode.PENALTY_DEP_ID_REQUIRED)
    private Long penaltyDepId;

    private Long compensationDepId;

    public Map<String, Object> constructJSON() {
        Map<String, Object> json = new HashMap<>();
        json.put("penaltyDepId", penaltyDepId);
        json.put("compensationDepId", compensationDepId);
        return json;
    }
}
