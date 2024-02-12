package uz.ciasev.ubdd_service.repository.dict;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.Region;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends AbstractDictRepository<Region> {

    @Query("SELECT r FROM Region r WHERE r.isActive = true")
    List<Region> findAllActive();

    @Query("FROM Region WHERE id IN (SELECT regionId FROM MibTransGeography WHERE isAvailableForSendMibExecutionCard = true)")
    List<Region> findAllMibPresence();

//    @Query("FROM Region WHERE id IN (SELECT regionId FROM CourtTransGeography WHERE isCourt = true)")
//    List<Region> findAllCourtPresence();

    @Query("FROM Region WHERE id IN (SELECT regionId FROM MibTransGeography WHERE isAvailableForSendMibExecutionCard = true) AND id = :id")
    Optional<Region> findByIdMibPresence(@Param("id") Long id);

//    @Query("FROM Region WHERE id IN (SELECT regionId FROM CourtTransGeography WHERE isCourt = true) AND id = :id")
//    Optional<Region> findByIdCourtPresence(@Param("id") Long id);
}
