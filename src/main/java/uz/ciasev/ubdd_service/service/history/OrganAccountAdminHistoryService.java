package uz.ciasev.ubdd_service.service.history;

import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.history.OrganAccountSettingsHistoricAction;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.annotation.Nullable;
import java.util.List;

public interface OrganAccountAdminHistoryService {

    void register(User user, OrganAccountSettingsHistoricAction action, List<OrganAccountSettings> entityList, @Nullable OrganAccountSettingsUpdateRequestDTO requestDTO);
}
