package uz.ciasev.ubdd_service.dto.internal.request.damage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.damage.DamageDetail;
import uz.ciasev.ubdd_service.entity.dict.DamageType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.MoneyAmount;

import javax.validation.constraints.NotNull;

@Data
public class DamageRequestDTO {

    @NotNull(message = ErrorCode.DAMAGE_TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.DAMAGE_TYPE_DEACTIVATED)
    @JsonProperty(value = "damageTypeId")
    private DamageType damageType;

    @MoneyAmount(message = ErrorCode.DAMAGE_AMOUNT_INVALID)
    private Long amount;

    public DamageDetail buildDamageDetail(User user, Damage damage, Protocol protocol) {
        return new DamageDetail(user, damage, protocol, damageType, amount);
    }

    public DamageDetail applyTo(DamageDetail damageDetail) {
        return damageDetail.update(damageType, amount);
    }
}
