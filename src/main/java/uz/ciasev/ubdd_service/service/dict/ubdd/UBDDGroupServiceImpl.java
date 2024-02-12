package uz.ciasev.ubdd_service.service.dict.ubdd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDGroup;
import uz.ciasev.ubdd_service.repository.dict.ubdd.UBDDGroupRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class UBDDGroupServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<UBDDGroup>
        implements UBDDGroupService {

    private final String subPath = "ubdd-group";

    private final UBDDGroupRepository repository;
    private final Class<UBDDGroup> entityClass = UBDDGroup.class;
}
