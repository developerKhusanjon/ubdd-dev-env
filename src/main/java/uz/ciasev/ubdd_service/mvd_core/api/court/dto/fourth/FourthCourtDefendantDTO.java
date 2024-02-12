package uz.ciasev.ubdd_service.mvd_core.api.court.dto.fourth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FourthCourtDefendantDTO {

    private Long defendantId;

    //person id
    private Long violatorId;

    @JsonProperty("invoice")
    private String invoiceSerial;

    @JsonProperty("fileId")
    private String invoiceUrl;

    // штраф(01) или ущерб(02) взять из SupplierType
    private Long supplierType;

    private List<FourthCourtMunisDTO> munis;
}
