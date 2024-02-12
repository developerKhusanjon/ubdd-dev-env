package uz.ciasev.ubdd_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.AliasDescription;
import uz.ciasev.ubdd_service.repository.AliasDescriptionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AliasDescriptionServiceImpl implements AliasDescriptionService {
    private final AliasDescriptionRepository repository;

    @Override
    public List<AliasDescription> findAllByAliasEnum(Class<? extends Enum<?>> aliasEnum) {
        return repository.findAllByAliasName(aliasEnum.getSimpleName());
    }
}
