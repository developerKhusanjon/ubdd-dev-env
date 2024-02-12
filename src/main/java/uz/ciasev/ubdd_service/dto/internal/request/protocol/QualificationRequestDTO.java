package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.ArticleRequest;
import uz.ciasev.ubdd_service.dto.internal.request.JuridicCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.protocol.ProtocolCreateRequest;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ConsistArticle;
import uz.ciasev.ubdd_service.utils.validator.ValidQualification;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidQualification
@ConsistArticle(message = ErrorCode.MAIN_ARTICLE_AND_VIOLATION_TYPE_NOT_CONSIST)
public class QualificationRequestDTO implements ArticleRequest  {

    @NotNull(message = ErrorCode.IS_JURIDIC_REQUIRED)
    private Boolean isJuridic;

    @Valid
    private JuridicCreateRequestDTO juridic;

    @Valid
    private List<QualificationArticleRequestDTO> additionArticles;

    @NotNull(message = ErrorCode.MAIN_ARTICLE_PART_REQUIRED)
    @ActiveOnly(message = ErrorCode.ARTICLE_PART_DEACTIVATED)
    @JsonProperty(value = "articlePartId")
    private ArticlePart articlePart;

    @ActiveOnly(message = ErrorCode.ARTICLE_VIOLATION_TYPE_DEACTIVATED)
    @JsonProperty(value = "articleViolationTypeId")
    private ArticleViolationType articleViolationType;

    private List<Long> repeatabilityProtocolsId;

    @Size(min = 50, max = 4000, message = ErrorCode.FABULA_MIN_MAX_LENGTH)
    @NotBlank(message = ErrorCode.FABULA_REQUIRED)
    private String fabula;

    @Size(max = 4000, message = ErrorCode.FABULA_ADDITIONAL_MAX_SIZE)
    private String fabulaAdditional;

    public List<QualificationArticleRequestDTO> getAdditionArticles() {
        return this.additionArticles == null
                ? List.of()
                : this.additionArticles;
    }

    public List<Long> getRepeatabilityProtocolsId() {
        return this.repeatabilityProtocolsId == null
                ? List.of()
                : this.repeatabilityProtocolsId;
    }

    public ProtocolCreateRequest buildProtocol() {
        ProtocolCreateRequest protocol = new ProtocolCreateRequest();
        protocol.setArticle(this.articlePart.getArticle());
        protocol.setArticlePart(this.articlePart);
        protocol.setArticleViolationType(this.articleViolationType);
        protocol.setIsJuridic(this.isJuridic);
        protocol.setFabula(this.fabula);
        protocol.setFabulaAdditional(this.fabula);
        return protocol;
    }

    public Protocol applyTo(Protocol protocol) {
        protocol.setArticle(this.articlePart.getArticle());
        protocol.setArticlePart(this.articlePart);
        protocol.setArticleViolationType(this.articleViolationType);
        protocol.setIsJuridic(this.isJuridic);
        protocol.setFabula(this.fabula);
        protocol.setFabula(this.fabula);
        protocol.setFabulaAdditional(this.fabula);
        return protocol;
    }
}
