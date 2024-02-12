package uz.ciasev.ubdd_service.service.main.resolution;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.Inspector;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public interface SingleResolutionBuildService {

    SingleResolutionRequestDTO buildResolutionForPenalty(Protocol protocol, Long penaltyAmount, LocalDateTime resolutionTime, String signature, uz.ciasev.ubdd_service.entity.Inspector inspector);

    SingleResolutionRequestDTO buildResolutionForPenalty(ArticlePart articlePart, @Nullable ArticleViolationType violationType, Boolean isJuridic, Long penaltyAmount, LocalDateTime resolutionTime, String signature, Inspector inspector);

    <T extends SingleResolutionRequestDTO> T fillInResolutionForPenalty(T singleResolutionRequestDTO, Protocol protocol, Long penaltyAmount, LocalDateTime resolutionTime, String signature, uz.ciasev.ubdd_service.entity.Inspector inspector);

    <T extends SingleResolutionRequestDTO> T fillInResolutionForPenalty(T singleResolutionRequestDTO, ArticlePart articlePart, @Nullable ArticleViolationType violationType, Boolean isJuridic, Long penaltyAmount, LocalDateTime resolutionTime, String signature, uz.ciasev.ubdd_service.entity.Inspector inspector);
}
