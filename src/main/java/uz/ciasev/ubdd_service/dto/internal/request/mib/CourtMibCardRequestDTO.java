package uz.ciasev.ubdd_service.dto.internal.request.mib;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CourtMibCardRequestDTO {

    @NotNull(message = ErrorCode.REGION_REQUIRED)
    private Region region;

    @NotNull(message = ErrorCode.DISTRICT_REQUIRED)
    private District district;

    @NotNull(message = ErrorCode.MIB_REQUEST_ID_REQUIRED)
    private Long mibRequestId;

    @NotNull(message = ErrorCode.SEND_TIME_REQUIRED)
    private LocalDateTime sendTime;


    public MibExecutionCard buildMibExecutionCard() {
        MibExecutionCard card = new MibExecutionCard();

        card.setRegion(this.region);
        card.setDistrict(this.district);

        return card;
    }

    public MibExecutionCard applyTo(MibExecutionCard card) {
        card.setRegion(this.region);
        card.setDistrict(this.district);

        return card;
    }
}
