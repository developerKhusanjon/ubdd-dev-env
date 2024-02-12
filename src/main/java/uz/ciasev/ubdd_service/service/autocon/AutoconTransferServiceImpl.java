package uz.ciasev.ubdd_service.service.autocon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.trans.autocon.AutoconTransRegion;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.exception.transfer.DictTransferNotPresent;
import uz.ciasev.ubdd_service.repository.autocon.AutoconRegionRepository;

@Service
@RequiredArgsConstructor
public class AutoconTransferServiceImpl implements AutoconTransferService {

    private final AutoconRegionRepository regionRepository;

    @Override
    public Long getExternalId(Region region) {
        return regionRepository.findByInternalId(region.getId())
                .map(AutoconTransRegion::getExternalId)
                .orElseThrow(() -> new DictTransferNotPresent(AutoconTransRegion.class, region));
    }
}
