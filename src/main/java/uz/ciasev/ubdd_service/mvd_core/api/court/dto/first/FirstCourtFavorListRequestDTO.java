package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.damage.Damage;

@Data
@NoArgsConstructor
public class FirstCourtFavorListRequestDTO {

    private Long victimId;
    private Long currencyId;
    private Long amount;
    private Long compensated;
    @JsonIgnore
    private Long group;

    public FirstCourtFavorListRequestDTO(Damage damage) {
        this.victimId = damage.getVictimId();
        this.currencyId = 8L;
        this.amount = damage.getTotalAmount();
        this.compensated = damage.getExtinguishedAmount();
        this.group = damage.getVictimTypeId();
    }
}
