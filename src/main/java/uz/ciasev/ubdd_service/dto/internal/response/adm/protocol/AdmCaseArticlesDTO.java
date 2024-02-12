package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AdmCaseArticlesDTO {

    private final List<ArticleResponseDTO> leadArticles;
    private final List<ArticleResponseDTO> additionArticles;

    public AdmCaseArticlesDTO(List<ProtocolArticle> articles) {
        this.leadArticles = articles.stream().filter(ProtocolArticle::getIsMain).map(ArticleResponseDTO::new).collect(Collectors.toList());
        this.additionArticles = articles.stream().filter(p -> !p.getIsMain()).map(ArticleResponseDTO::new).collect(Collectors.toList());
    }

}
