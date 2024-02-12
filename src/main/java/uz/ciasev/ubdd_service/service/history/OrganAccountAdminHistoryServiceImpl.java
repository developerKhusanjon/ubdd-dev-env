package uz.ciasev.ubdd_service.service.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.history.OrganAccountSettingsActionRegistration;
import uz.ciasev.ubdd_service.entity.history.OrganAccountSettingsDetailRegistration;
import uz.ciasev.ubdd_service.entity.history.OrganAccountSettingsHistoricAction;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.history.OrganAccountSettingsDetailRegistrationRepository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganAccountAdminHistoryServiceImpl implements OrganAccountAdminHistoryService {

    private final HistoryService historyService;

    private final OrganAccountSettingsDetailRegistrationRepository registrationDetailRepository;

    @Override
    @Transactional
    public void register(User user,
                         OrganAccountSettingsHistoricAction action,
                         List<OrganAccountSettings> entityList,
                         @Nullable OrganAccountSettingsUpdateRequestDTO requestDTO) {

        OrganAccountSettingsActionRegistration actionRegistration = historyService.registerOrganAccountSettingsAction(user, action, requestDTO);

        List<OrganAccountSettingsDetailRegistration> detailRegistrationList = entityList.stream()
                .map(entity -> new OrganAccountSettingsDetailRegistration(actionRegistration, entity))
                .collect(Collectors.toList());

        registrationDetailRepository.saveAll(detailRegistrationList);
    }
}
