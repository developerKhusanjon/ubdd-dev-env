package uz.ciasev.ubdd_service.repository.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

import java.util.Optional;
import java.util.Set;

public interface AdmStatusRepository extends JpaRepository<AdmStatus, Long> {

    Optional<AdmStatus> findByAlias(@Param("alias") AdmStatusAlias alias);

    @Transactional
    @Modifying
    @Query(value = "delete from {h-schema}d_adm_status where alias not in :declaredAliases", nativeQuery = true)
    void deleteAllByAliasNotIn(@Param("declaredAliases") Set<String> declaredAliases);
}
