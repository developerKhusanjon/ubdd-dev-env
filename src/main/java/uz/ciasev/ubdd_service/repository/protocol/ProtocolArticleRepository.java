package uz.ciasev.ubdd_service.repository.protocol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;

import java.util.List;
import java.util.Optional;

public interface ProtocolArticleRepository extends JpaRepository<ProtocolArticle, Long> {

    @Modifying
    @Query("DELETE FROM ProtocolArticle pa WHERE pa.protocolId = :protocolId ")
    void deleteByProtocolId(Long protocolId);

    @Modifying
    @Query("UPDATE ProtocolArticle pa SET pa.isMain = FALSE WHERE pa.protocolId = :protocolId ")
    void setMainToFalseForAllByProtocolId(Long protocolId);

    @Modifying
    @Query("UPDATE ProtocolArticle pa SET pa.isMain = TRUE WHERE pa = :mainArticle")
    void makeMain(ProtocolArticle mainArticle);

    List<ProtocolArticle> findAllByProtocolId(Long protocolId);

    @Query("SELECT ap " +
            "FROM ProtocolArticle ap " +
            "WHERE ap.protocol.violatorDetail.violator.admCaseId = :admCaseId ")
    List<ProtocolArticle> findAllByAdmCaseId(Long admCaseId);

    @Query("SELECT ap " +
            "FROM ProtocolArticle ap " +
            "WHERE ap.isMain = TRUE " +
            "   AND ap.protocol.violatorDetail.violator.admCaseId = :admCaseId ")
    List<ProtocolArticle> findAllMainByAdmCaseId(Long admCaseId);

    @Query("SELECT DISTINCT ap.articlePart " +
            "FROM ProtocolArticle ap " +
            "WHERE ap.isMain = TRUE " +
            "   AND ap.protocol.violatorDetail.violator.admCaseId = :admCaseId ")
    List<ArticlePart> findAllMainArticlePatsByAdmCaseId(Long admCaseId);


    @Query("SELECT ap " +
            "FROM ProtocolArticle ap " +
            "WHERE ap.protocol.violatorDetail.violatorId = :violatorId ")
    List<ProtocolArticle> findAllByViolatorId(Long violatorId);


    @Query("SELECT DISTINCT ap.articlePart " +
            "FROM ProtocolArticle ap " +
            "WHERE ap.protocol.violatorDetail.violatorId = :violatorId ")
    List<ArticlePart> findAllArticlePartsByViolatorId(Long violatorId);


    @Query("SELECT DISTINCT ap.articlePart " +
            "FROM ProtocolArticle ap " +
            "WHERE ap.isMain = TRUE " +
            "   AND ap.protocol.violatorDetail.violatorId = :violatorId " +
            "   AND ap.protocol.isMain = TRUE ")
    Optional<ArticlePart> findMainByViolatorId(Long violatorId);

    @Query("SELECT DISTINCT ap.articlePartId " +
            "FROM ProtocolArticle ap " +
            "WHERE ap.isMain = FALSE " +
            "   AND ap.protocolId = :protocolId ")
    List<Long> findAllAdditionalArticlePartsId(Long protocolId);
}
