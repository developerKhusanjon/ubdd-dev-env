package uz.ciasev.ubdd_service.service.publicapi;

import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiIssuer;

import java.util.Optional;

public interface PublicApiIssuerService {

    Optional<PublicApiIssuer> findByIssuerName(String name);

    Optional<PublicApiIssuer> findByOrgan(Organ organ);
}
