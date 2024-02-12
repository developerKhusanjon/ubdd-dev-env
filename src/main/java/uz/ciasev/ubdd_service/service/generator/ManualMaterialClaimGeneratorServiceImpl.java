package uz.ciasev.ubdd_service.service.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ManualMaterialClaimGeneratorServiceImpl implements ManualMaterialClaimGeneratorService {

    private final NumberGeneratorService numberGeneratorService;

    @Override
    public Long generateClaim() {
        return -1 * numberGeneratorService.incrementAndGet(EntityNameAlias.PROTOCOL, LocalDate.now().getYear());
    }
}