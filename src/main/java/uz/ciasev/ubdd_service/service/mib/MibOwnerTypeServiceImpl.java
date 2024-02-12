package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerType;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerTypeAlias;
import uz.ciasev.ubdd_service.exception.notfound.EntityByAliasNotPresent;
import uz.ciasev.ubdd_service.repository.mib.MibOwnerTypeRepository;

@Service
@RequiredArgsConstructor
public class MibOwnerTypeServiceImpl implements MibOwnerTypeService {

    private final MibOwnerTypeRepository mibOwnerTypeRepository;

    @Override
    public MibOwnerType findByAlias(MibOwnerTypeAlias alias) {
        return mibOwnerTypeRepository.findByAlias(alias)
                .orElseThrow(() -> new EntityByAliasNotPresent(MibOwnerType.class, alias.name()));
    }
}
