package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class MibSverkaResponseDTO extends MibRequestDTO implements MibApiResponseDTOI {

    private String resultCode;

    private String resultMsg;

//    public boolean isSuccessfully() {
//        return "0".equals(resultCode);
//    }
}
