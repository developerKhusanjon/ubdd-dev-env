package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionStatusDocument;

public interface MibExecutionStatusDocumentRepository extends JpaRepository<MibExecutionStatusDocument, Long> {

    @Query("SELECT d FROM MibExecutionStatusDocument d WHERE d.cardMovement.card.id = :cardId")
    Page<MibExecutionStatusDocument> findAllByCardId(Long cardId, Pageable pageable);
}
