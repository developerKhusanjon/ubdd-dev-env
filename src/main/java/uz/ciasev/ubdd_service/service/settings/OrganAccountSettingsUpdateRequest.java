package uz.ciasev.ubdd_service.service.settings;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.settings.BankAccount;

@Data
public class OrganAccountSettingsUpdateRequest {

    private BankAccount penaltyAccount;

    private BankAccount compensationAccount;
}
