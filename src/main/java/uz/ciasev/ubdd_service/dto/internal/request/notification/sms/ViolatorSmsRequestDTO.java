package uz.ciasev.ubdd_service.dto.internal.request.notification.sms;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.ciasev.ubdd_service.entity.violator.Violator;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ViolatorSmsRequestDTO extends SmsRequestDTO {

    private Violator violator;

    @Override
    public Long getPersonId() {
        if (violator == null) return null;
        return violator.getPersonId();
    }

    @Override
    public Long getUserId() {
        return null;
    }

    @Override
    public Long getViolatorId() {
        if (violator == null) return null;
        return violator.getId();
    }

    @Override
    public Long getAdmCaseId() {
        if (violator == null) return null;
        return violator.getAdmCaseId();
    }
}
