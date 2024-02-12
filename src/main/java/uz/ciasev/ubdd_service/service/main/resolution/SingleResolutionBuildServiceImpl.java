package uz.ciasev.ubdd_service.service.main.resolution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.OrganPunishmentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.Inspector;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.service.dict.resolution.PunishmentTypeDictionaryService;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

import static uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias.PUNISHMENT;
import static uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias.PENALTY;


@Service
@RequiredArgsConstructor
public class SingleResolutionBuildServiceImpl implements SingleResolutionBuildService {

    private final PunishmentTypeDictionaryService punishmentTypeService;


    @Override
    public SingleResolutionRequestDTO buildResolutionForPenalty(Protocol protocol, Long penaltyAmount, LocalDateTime resolutionTime, String signature, Inspector inspector) {
        return fillInResolutionForPenalty(new SingleResolutionRequestDTO(), protocol, penaltyAmount, resolutionTime, signature, inspector);
    }

    @Override
    public <T extends SingleResolutionRequestDTO> T fillInResolutionForPenalty(T singleResolutionRequestDTO, Protocol protocol, Long penaltyAmount, LocalDateTime resolutionTime, String signature, Inspector inspector) {
        return fillInResolutionForPenalty(singleResolutionRequestDTO, protocol.getArticlePart(), protocol.getArticleViolationType(), protocol.getIsJuridic(), penaltyAmount, resolutionTime, signature, inspector);
    }


    @Override
    public SingleResolutionRequestDTO buildResolutionForPenalty(ArticlePart articlePart, @Nullable ArticleViolationType violationType, Boolean isJuridic, Long penaltyAmount, LocalDateTime resolutionTime, String signature, Inspector inspector) {
        return fillInResolutionForPenalty(new SingleResolutionRequestDTO(), articlePart, violationType, isJuridic, penaltyAmount, resolutionTime, signature, inspector);
    }

    @Override
    public <T extends SingleResolutionRequestDTO> T fillInResolutionForPenalty(T singleResolutionRequestDTO, ArticlePart articlePart, @Nullable ArticleViolationType violationType, Boolean isJuridic, Long penaltyAmount, LocalDateTime resolutionTime, String signature, uz.ciasev.ubdd_service.entity.Inspector inspector) {
        OrganPunishmentRequestDTO mainPunishment = new OrganPunishmentRequestDTO();
        mainPunishment.setAmount(penaltyAmount);
        mainPunishment.setPunishmentType(punishmentTypeService.getByAlias(PENALTY));

        singleResolutionRequestDTO.setDecisionType(PUNISHMENT);
        singleResolutionRequestDTO.setExecutionFromDate(resolutionTime.toLocalDate());
        singleResolutionRequestDTO.setResolutionTime(resolutionTime);
        singleResolutionRequestDTO.setMainPunishment(mainPunishment);
        singleResolutionRequestDTO.setSignature(signature);

        singleResolutionRequestDTO.setArticlePart(articlePart);
        singleResolutionRequestDTO.setArticleViolationType(violationType);
        singleResolutionRequestDTO.setIsJuridic(isJuridic);

        singleResolutionRequestDTO.setDistrict(inspector.getDistrict());
        singleResolutionRequestDTO.setDepartment(inspector.getDepartment());

        return singleResolutionRequestDTO;
    }
}
