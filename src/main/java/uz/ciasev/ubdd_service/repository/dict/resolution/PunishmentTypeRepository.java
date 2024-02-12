package uz.ciasev.ubdd_service.repository.dict.resolution;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.AbstractAliasedDictRepository;

import java.util.Optional;

public interface PunishmentTypeRepository extends AbstractAliasedDictRepository<PunishmentType, PunishmentTypeAlias> {

    @Query("SELECT pt FROM PunishmentType pt WHERE pt.courtAdditionalPunishmentId = :id")
    Optional<PunishmentType> findByCourtAdditionalPunishmentId(@Param("id") Long id);
}
