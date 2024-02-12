package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtCompensationPayerType;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;

@Data
public class CompensationRequest {

    private Long amount;
    private Currency currency;
    private CourtCompensationPayerType payerType;
    private Long payerInn;
    private VictimType victimType;
    private Long victimId;
    private Long victimInn;

}
