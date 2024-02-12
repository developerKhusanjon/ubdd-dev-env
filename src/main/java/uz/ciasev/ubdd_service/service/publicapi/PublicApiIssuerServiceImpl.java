package uz.ciasev.ubdd_service.service.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.OrganAlias;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiIssuer;
import uz.ciasev.ubdd_service.repository.publicapi.PublicApiIssuerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicApiIssuerServiceImpl implements PublicApiIssuerService {

    private final PublicApiIssuerRepository repository;

    @Override
    public Optional<PublicApiIssuer> findByIssuerName(String name) {
        OrganAlias organAlias;
        try {
            organAlias = OrganAlias.valueOf(name);
        } catch (Exception e) {
            return Optional.empty();
        }

        return repository.findByOrganAlias(organAlias);
    }

    @Override
    public Optional<PublicApiIssuer> findByOrgan(Organ organ) {
        return repository.findByOrgan(organ);
    }
}
