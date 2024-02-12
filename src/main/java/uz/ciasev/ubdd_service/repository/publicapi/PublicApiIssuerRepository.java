package uz.ciasev.ubdd_service.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.OrganAlias;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiIssuer;

import java.util.Optional;

public interface PublicApiIssuerRepository extends JpaRepository<PublicApiIssuer, String> {

    Optional<PublicApiIssuer> findByOrgan(Organ organ);

    @Query("SELECT i " +
            "FROM PublicApiIssuer i " +
            "WHERE i.organ.alias = :organAlias ")
    Optional<PublicApiIssuer> findByOrganAlias(OrganAlias organAlias);


}
