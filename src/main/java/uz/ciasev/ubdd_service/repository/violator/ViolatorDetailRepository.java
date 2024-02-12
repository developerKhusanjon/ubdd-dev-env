package uz.ciasev.ubdd_service.repository.violator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;

import java.util.List;
import java.util.Optional;

public interface ViolatorDetailRepository extends JpaRepository<ViolatorDetail, Long> {

    @Modifying
    @Query(value = "UPDATE ViolatorDetail SET violatorId = :toViolatorId WHERE violatorId = :fromViolatorId")
    void mergeAllTo(@Param("fromViolatorId") Long fromViolatorId, @Param("toViolatorId") Long toViolatorId);

    @Modifying
    @Query(value = "UPDATE ViolatorDetail vd SET vd.residenceAddressId = :residenceAddress WHERE violatorId = :violatorId")
    void setResidenceAddressByViolator(Long violatorId, Long residenceAddress);

    @Query("SELECT vd " +
            " FROM ViolatorDetail vd " +
            "WHERE vd.violator.id = :violatorId " +
            "ORDER BY vd.createdTime DESC")
    List<ViolatorDetail> findByViolatorId(@Param("violatorId") Long violatorId);

    boolean existsByViolatorId(Long violatorId);

    @Query("SELECT vd " +
            " FROM ViolatorDetail vd " +
            "WHERE vd.violator.id = :violatorId " +
            " AND vd.id IN (SELECT p.violatorDetailId FROM Protocol p WHERE id = :protocolId)")
    Optional<ViolatorDetail> findByViolatorIdAndProtocolId(@Param("violatorId") Long id, @Param("protocolId") Long protocolId);

    @Query("SELECT p.violatorDetail " +
            " FROM Protocol p " +
            "WHERE p.violatorDetail.violatorId = :violatorId " +
            " AND p.isMain = TRUE ")
    Optional<ViolatorDetail> findMainByViolatorId(Long violatorId);
}
