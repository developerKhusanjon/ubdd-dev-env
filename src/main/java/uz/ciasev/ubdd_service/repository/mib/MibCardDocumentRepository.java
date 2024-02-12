package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.mib.MibCardDocument;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;

import java.util.List;

public interface MibCardDocumentRepository extends JpaRepository<MibCardDocument, Long> {

    List<MibCardDocument> findByCardId(Long cardId);

    @Query("SELECT d FROM MibCardDocument d WHERE d.card = :card AND d.documentType.preventAttach = false")
    List<MibCardDocument> findAttachableByCard(@Param("card") MibExecutionCard card);
}
