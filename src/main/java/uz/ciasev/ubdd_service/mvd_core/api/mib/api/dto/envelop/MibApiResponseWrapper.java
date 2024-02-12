package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response.MibApiResponseResultDTO;

@Data
public class MibApiResponseWrapper {

    private MibApiResponseResultDTO result;
}
