package uz.ciasev.ubdd_service.dto.internal.request.victim;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageRequestDTO;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class VictimRequestCoreDTO implements VictimRequestDTO {

    @Pattern(regexp = "^\\d{12}$", message = ErrorCode.VICTIM_MOBILE_FORMAT_INVALID)
    @NotNull(message = ErrorCode.VICTIM_MOBILE_REQUIRED)
    private String mobile;

    @Pattern(regexp = "^\\d{9}$", message = ErrorCode.VICTIM_LANDLINE_FORMAT_INVALID)
    private String landline;

    @Pattern(regexp = "^\\d{9}$", message = ErrorCode.VICTIM_INN_FORMAT_INVALID)
    private String inn;

    @Valid
    private List<DamageRequestDTO> damages;

//    public Victim buildVictim() {
//        Victim victim = new Victim();
//        victim.setMobile(this.mobile);
//        victim.setLandline(this.landline);
//        victim.setInn(this.inn);
//
//        return victim;
//    }

    public Victim applyTo(Victim victim) {
        victim.setMobile(this.mobile);
        victim.setLandline(this.landline);
        victim.setInn(this.inn);

        return victim;
    }
}
