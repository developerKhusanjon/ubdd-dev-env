package uz.ciasev.ubdd_service.service.loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationTypeTag;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationTypeTagAlias;
import uz.ciasev.ubdd_service.repository.dict.article.ArticleViolationTypeTagRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ViolationTypeTagSyncService {

    private final ArticleViolationTypeTagRepository repository;

    public void sync() {
        HashSet<ArticleViolationTypeTagAlias> declaredAliases = new HashSet<>(List.of(ArticleViolationTypeTagAlias.values()));

        Set<ArticleViolationTypeTagAlias> existValues = repository.findAll()
                .stream()
                .map(ArticleViolationTypeTag::getAlias)
                .collect(Collectors.toSet());
        declaredAliases.stream()
                .filter(alias -> !existValues.contains(alias))
                .map(alias -> {
                    String text = String.valueOf(alias).toLowerCase(Locale.ROOT).replace("_", " ");

                    return new ArticleViolationTypeTag(alias);
                })
                .forEach(repository::save);
    }
}
