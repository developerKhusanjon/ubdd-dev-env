package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

@Data
public class AdmCaseSimplifiedResponseDTO {

    private Long minAmountPenalty;
    private Long maxAmountPenalty;
    private Long articleId;
    private Long articlePartId;
    private Long articleViolationTypeId;
    private Boolean isJuridic;


    public AdmCaseSimplifiedResponseDTO(Protocol protocol, long minAmountPenalty, long maxAmountPenalty) {
        // todo Добавить вычисление с текуший минималкой
        this.articleId = protocol.getArticleId();
        this.articlePartId = protocol.getArticlePartId();
        this.articleViolationTypeId = protocol.getArticleViolationTypeId();
        this.isJuridic = protocol.getIsJuridic();
        this.minAmountPenalty = minAmountPenalty;
        this.maxAmountPenalty = maxAmountPenalty;

    }
}
