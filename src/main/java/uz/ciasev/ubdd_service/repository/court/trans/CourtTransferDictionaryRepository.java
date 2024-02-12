package uz.ciasev.ubdd_service.repository.court.trans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;

import java.util.List;
import java.util.Optional;

public interface CourtTransferDictionaryRepository extends JpaRepository<CourtTransfer, Long> {

    @Query("SELECT ct " +
            " FROM CourtTransfer ct " +
            "WHERE ct.internalRegionId = :regionId " +
            "  AND ct.internalDistrictId = :districtId")
    Optional<CourtTransfer> findByRegionAndDistrictIds(@Param("regionId") Long regionId,
                                                       @Param("districtId") Long districtId);

    Optional<CourtTransfer> findByExternalId(@Param("externalId") Long externalId);

    @Query("SELECT DISTINCT ct.region FROM CourtTransfer ct WHERE ct.region IS NOT NULL")
    List<Region> findAllCourtRegions();

    @Query("SELECT DISTINCT ct.district FROM CourtTransfer ct WHERE ct.district IS NOT NULL")
    List<District> findAllCourtDistrict();
}
