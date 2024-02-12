package uz.ciasev.ubdd_service.service.loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.NotificationType;
import uz.ciasev.ubdd_service.repository.dict.NotificationTypeRepository;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationTypeSyncService {

    private final NotificationTypeRepository repository;

    public void sync() {
        List<NotificationTypeAlias> enumAliasList = List.of(NotificationTypeAlias.values());
        List<Long> dbAliasIdList = repository.findAll()
                .stream()
                .map(NotificationType::getId)
                .collect(Collectors.toList());

        for (NotificationTypeAlias alias : enumAliasList) {
            if (!dbAliasIdList.contains(alias.getId())) {
                repository.save(new NotificationType(alias));
            }
        }
    }
}
