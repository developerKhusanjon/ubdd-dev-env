package uz.ciasev.ubdd_service.dto.internal.request.notification.sms;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.ciasev.ubdd_service.entity.user.User;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserSmsRequestDTO extends SmsRequestDTO {

    private User user;

    @Override
    public Long getPersonId() {
        if (user == null) return null;
        return user.getPersonId();
    }

    @Override
    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }

    @Override
    public Long getViolatorId() {
        return null;
    }

    @Override
    public Long getAdmCaseId() {
        return null;
    }
}
