package uz.ciasev.ubdd_service.repository.dict;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.District;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends AbstractDictRepository<District> {

    @Query("SELECT d FROM District d WHERE d.regionId = :regionId AND d.isActive = true")
    List<District> findAllByRegionId(@Param("regionId") Long regionId);

    @Query("FROM District WHERE id IN (SELECT districtId FROM MibTransGeography WHERE isAvailableForSendMibExecutionCard = true)")
    List<District> findAllMibPresence();

    @Query("FROM District WHERE id IN (SELECT districtId FROM CourtTransGeography WHERE isCourt = true)")
    List<District> findAllCourtPresence();

    @Query("FROM District WHERE id IN (SELECT districtId FROM MibTransGeography WHERE isAvailableForSendMibExecutionCard = true) AND id = :id")
    Optional<District> findByIdMibPresence(@Param("id") Long id);

    @Query("FROM District WHERE id IN (SELECT districtId FROM CourtTransGeography WHERE isCourt = true) AND id = :id")
    Optional<District> findByIdCourtPresence(@Param("id") Long id);
}
