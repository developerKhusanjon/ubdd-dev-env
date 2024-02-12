package uz.ciasev.ubdd_service.service.loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.single_thread_operation.SingleThreadOperationType;
import uz.ciasev.ubdd_service.entity.single_thread_operation.SingleThreadOperationTypeAlias;
import uz.ciasev.ubdd_service.repository.single_thread_operation.SingleThreadOperationTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SingleThreadOperationTypeSyncService {
    private final SingleThreadOperationTypeRepository typeRepository;

    public void sync() {
        List<SingleThreadOperationTypeAlias> enumAliasList = List.of(SingleThreadOperationTypeAlias.values());
        List<Long> dbAliasIdList = typeRepository.findAll()
                .stream()
                .map(SingleThreadOperationType::getId)
                .collect(Collectors.toList());

        for (SingleThreadOperationTypeAlias alias : enumAliasList) {
            if (!dbAliasIdList.contains(alias.getId())) {
                typeRepository.save(new SingleThreadOperationType(alias.getId(), alias.name()));
            }
        }
    }
}
