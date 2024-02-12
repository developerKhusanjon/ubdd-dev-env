package uz.ciasev.ubdd_service.utils;

import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.util.Map;

public class ArticleNameUtils {

    private final static MultiLanguage ARTICLE_WORD = new MultiLanguage("СТАТЬЯ", "МОДДА", "MODDA");
    private final static MultiLanguage PRIM_WORD = new MultiLanguage("ПРИМ", "ПРИМ", "PRIM");
    private final static MultiLanguage PART_WORD = new MultiLanguage("Ч", "Қ", "Q");

    private final static String ARTICLE_WITHOUT_PRIM_SORT_NAME_FORMAT = "${number} - ${articleWord}";
    private final static String ARTICLE_WITH_PRIM_SORT_NAME_FORMAT = ARTICLE_WITHOUT_PRIM_SORT_NAME_FORMAT + " ${primWord} ${prim}";
    private final static String ARTICLE_PART_WITHOUT_PRIM_SORT_NAME_FORMAT = "${number} - ${part}${partWord}";
    private final static String ARTICLE_PART_WITH_PRIM_SORT_NAME_FORMAT = "${number} - ${primWord}-${prim} ${part}${partWord}";

    public static String buildCode(Article article) {
        return String.format("%03d", article.getNumber()) + String.format("%02d", article.getPrim());
    }

    public static String buildCode(ArticlePart part) {
        return buildCode(part.getArticle()) + String.format("%02d", part.getNumber());
    }

    public static MultiLanguage buildShortName(Article article) {
        return new MultiLanguage(
                buildShortName(article, MultiLanguage.Language.RU),
                buildShortName(article, MultiLanguage.Language.KIR),
                buildShortName(article, MultiLanguage.Language.LAT)
        );
    }

    public static MultiLanguage buildName(ArticlePart part) {
        return new MultiLanguage(
                buildName(part, MultiLanguage.Language.RU),
                buildName(part, MultiLanguage.Language.KIR),
                buildName(part, MultiLanguage.Language.LAT)
        );
    }

    private static String buildShortName(Article article, MultiLanguage.Language language) {
        Map<String, String> params = Map.of(
                "number", String.valueOf(article.getNumber()),
                "prim", String.valueOf(article.getPrim()),
                "articleWord", ARTICLE_WORD.get(language),
                "primWord", PRIM_WORD.get(language)
        );

        if (article.isWithoutPrim()) {
            return StrSubstitutor.replace(ARTICLE_WITHOUT_PRIM_SORT_NAME_FORMAT, params);
        }

        return StrSubstitutor.replace(ARTICLE_WITH_PRIM_SORT_NAME_FORMAT, params);
    }

    private static String buildName(ArticlePart part, MultiLanguage.Language language) {
        if (part.getNumber() == 0) {
            return buildShortName(part.getArticle(), language);
        }

        Map<String, String> params = Map.of(
                "number", String.valueOf(part.getArticle().getNumber()),
                "prim", String.valueOf(part.getArticle().getPrim()),
                "part", String.valueOf(part.getNumber()),
                "articleWord", ARTICLE_WORD.get(language),
                "primWord", PRIM_WORD.get(language),
                "partWord", PART_WORD.get(language)
        );

        if (part.getArticle().isWithoutPrim()) {
            return StrSubstitutor.replace(ARTICLE_PART_WITHOUT_PRIM_SORT_NAME_FORMAT, params);
        }

        return StrSubstitutor.replace(ARTICLE_PART_WITH_PRIM_SORT_NAME_FORMAT, params);
    }
}
