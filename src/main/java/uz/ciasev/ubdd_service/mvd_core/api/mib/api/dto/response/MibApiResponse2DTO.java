package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class MibApiResponse2DTO implements MibApiResponseDTOI {

    @JsonAlias("result_code")
    private String resultCode;

    @JsonAlias("result_message")
    private String resultMsg;

    @Override
    public Long getEnvelopeId() {
        return null;
    }
}
