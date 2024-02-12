package uz.ciasev.ubdd_service.repository.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.status.EntityStatusAccumulator;

import java.util.List;
import java.util.Optional;

public interface EntityStatusAccumulatorRepository extends JpaRepository<EntityStatusAccumulator, Long> {

//    @Query(value = "SELECT s.* " +
//            "FROM {h-schema}entity_status_accumulator a " +
//            " JOIN {h-schema}d_adm_status s ON s.id = a.adm_status_id " +
//            "WHERE a.entity_id = :entityId AND entity_type = :entityType " +
//            "ORDER BY s.rank " +
//            "LIMIT 1 ",
//            nativeQuery = true)
    Optional<EntityStatusAccumulator> findTopByEntityIdAndEntityTypeOrderByAdmStatusRankDesc(@Param("entityId") Long entityId, @Param("entityType") EntityNameAlias entityType);

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM EntityStatusAccumulator " +
            "WHERE entityId = :entityId AND entityType = :entityType " +
            "   AND admStatus.id IN (SELECT id FROM AdmStatus WHERE rank = (SELECT rank FROM AdmStatus WHERE alias = :alias))")
    void deleteWithSameRank(@Param("entityId") Long entityId, @Param("entityType") EntityNameAlias entityType, @Param("alias") AdmStatusAlias alias);

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM EntityStatusAccumulator " +
            "WHERE entityId = :entityId AND entityType = :entityType " +
            "   AND admStatus.id IN (SELECT id FROM AdmStatus WHERE alias IN :aliases)")
    void deleteWithStatusAlias(@Param("entityId") Long entityId, @Param("entityType") EntityNameAlias entityType, @Param("aliases") List<AdmStatusAlias> alias);

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM EntityStatusAccumulator " +
            "WHERE entityId = :entityId AND entityType = :entityType ")
    void clearAccumulator(@Param("entityId") Long entityId, @Param("entityType") EntityNameAlias entityType);

//    @Modifying
//    @Query(value = "INSERT INTO EntityStatusAccumulator (entityId, entityType, admStatusId) " +
//            "VALUE (:entityId, :entityType, (SELECT id FROM AdmStatus WHERE alias = :alias))")
//    void putStatus(@Param("entityId") Long entityId, @Param("entityType") EntityNameAlias entityType, @Param("alias") AdmStatusAlias alias);
}
