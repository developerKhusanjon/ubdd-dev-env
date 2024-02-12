package uz.ciasev.ubdd_service.dto.internal.response.court;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.violator.ViolatorCourtReturnReasonProjection;
import uz.ciasev.ubdd_service.utils.FioUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViolatorReturnReasonDTO {

    private Long id;
    private Long violatorId;
    private String violatorFio;
    private Long returnReasonId;

    public ViolatorReturnReasonDTO(ViolatorCourtReturnReasonProjection projection) {
        this.id = projection.getId();
        this.violatorId = projection.getId();
        this.violatorFio = FioUtils.buildShortFio(projection.getFirstNameLat(), projection.getSecondNameLat(), projection.getLastNameLat());
        this.returnReasonId = projection.getCourtReturnReasonId();
    }
}
