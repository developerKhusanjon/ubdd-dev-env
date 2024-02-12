package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response;

import lombok.Data;

@Data
public class MibApiResponseResultDTO implements MibApiResponseDTOI {

    private String resultCode;

    private String resultMsg;

    private Long envelopeId;
}
