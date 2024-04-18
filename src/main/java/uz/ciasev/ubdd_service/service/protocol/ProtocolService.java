package uz.ciasev.ubdd_service.service.protocol;

import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.AdmCaseArticlesDTO;
import uz.ciasev.ubdd_service.entity.Inspector;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;

import java.time.LocalDateTime;
import java.util.List;

public interface ProtocolService {

    Protocol findSingleMainByAdmCaseId(Long admCaseId);

    Protocol getMainByViolatorId(Long violatorId);

    Protocol create(Inspector user, ViolatorDetail violatorDetail, ProtocolCreateRequest protocol, List<ProtocolArticle> additionArticles);

    Protocol update(Protocol protocol, List<ProtocolArticle> additionArticles);

    void editMainArticle(Protocol protocol, ProtocolArticle mainArticle);

    Protocol findById(Long id);

    Protocol findByExternalId(User user, String id);

    Protocol findEarliestProtocolInAdmCase(Long admCaseId);

    List<Protocol> findAllByViolatorsId(List<Long> violatorsId);

    List<Protocol> findAllByViolatorId(Long violatorId);

    List<Long> findAllIdByViolatorId(Long violatorId);

    List<Long> findAllIdByFromDate(LocalDateTime fromDate);

    List<Protocol> findAllProtocolsInAdmCase(Long admCaseId);

    List<Long> findAllProtocolsIdInAdmCase(Long admCaseId);

    AdmCaseArticlesDTO findAllArticleByAdmCase(Long admCase);

    ProtocolArticle getProtocolArticleById(Long id);

    List<ProtocolArticle> getProtocolAdditionArticles(Long protocolId);

    List<ProtocolArticle> getAllProtocolArticles(Long protocolId);

    List<ProtocolArticle> getViolatorArticles(Violator violator);

    List<ArticlePart> getViolatorArticleParts(Violator violator);

    List<ArticlePart> findMainArticlePartsByAdmCase(AdmCase admCase);

//    void saveAll(List<Protocol> protocols);

    ArticlePart getViolatorMainArticle(Violator violator);

    boolean existsJuvenileByAdmCaseId(Long id);

    boolean existsProtocolAdditionArticles(Protocol protocol);
}
