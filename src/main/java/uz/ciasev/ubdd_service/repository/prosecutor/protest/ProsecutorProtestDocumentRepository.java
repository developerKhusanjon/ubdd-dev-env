package uz.ciasev.ubdd_service.repository.prosecutor.protest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocumentProjection;

public interface ProsecutorProtestDocumentRepository extends JpaRepository<ProsecutorProtestDocument, Long> {
    Page<ProsecutorProtestDocument> findAllByProtestId(Long protestId, Pageable pageable);

    ProsecutorProtestDocument findFirstByProtestIdOrderByCreatedTimeAsc(Long protestId);

    @Query("SELECT " +
            "p.id AS id, " +
            "p.createdTime AS createdTime, " +
            "p.userId AS userId, " +
            "p.protestId AS protestId, " +
            "p.protest.number AS protestNumber, " +
            "p.uri AS uri " +
            "FROM ProsecutorProtestDocument p WHERE p.protest.resolutionId = :resolutionId")
    Page<ProsecutorProtestDocumentProjection> findAllByResolutionId(Long resolutionId, Pageable pageable);
}
