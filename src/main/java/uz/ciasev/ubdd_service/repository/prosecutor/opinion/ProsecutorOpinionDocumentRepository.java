package uz.ciasev.ubdd_service.repository.prosecutor.opinion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocumentProjection;

public interface ProsecutorOpinionDocumentRepository extends JpaRepository<ProsecutorOpinionDocument, Long>  {

    Page<ProsecutorOpinionDocument> findAllByOpinionId(Long opinionId, Pageable pageable);

    @Query("SELECT " +
            "p.id AS id, " +
            "p.createdTime AS createdTime, " +
            "p.userId AS userId, " +
            "p.opinionId AS opinionId, " +
            "p.uri AS uri " +
            "FROM ProsecutorOpinionDocument p WHERE p.opinion.admCaseId = :admCaseId")
    Page<ProsecutorOpinionDocumentProjection> findAllByAdmCaseId(Long admCaseId, Pageable pageable);
}
