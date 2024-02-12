package uz.ciasev.ubdd_service.service.settings;

import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.UUID;

public interface OrganAccountAdminService {

    List<OrganAccountSettings> findAccounts(User user, OrganAccountSettingsCreateRequestDTO requestDTO);

    List<OrganAccountSettings> createAccounts(User user, OrganAccountSettingsCreateRequestDTO requestDTO);

    OrganAccountSettings update(User user, UUID id, OrganAccountSettingsUpdateRequestDTO requestDTO);

    void delete(User user, UUID id);

    void delete(User user, List<UUID> idList);

}
