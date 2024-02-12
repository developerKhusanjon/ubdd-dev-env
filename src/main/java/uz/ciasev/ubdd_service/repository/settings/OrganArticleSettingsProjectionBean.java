package uz.ciasev.ubdd_service.repository.settings;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.settings.OrganArticleSettingsProjection;

@Getter
public class OrganArticleSettingsProjectionBean implements OrganArticleSettingsProjection {

    private final Long id;

    private final Long articleId;

    private final Long articlePartId;

    private final Boolean isDiscount;

    private final Boolean isActive;

    public OrganArticleSettingsProjectionBean(Long id, Long articleId, Long articlePartId, Boolean isDiscount, Boolean isActive) {
        this.id = id;
        this.articleId = articleId;
        this.articlePartId = articlePartId;
        this.isDiscount = isDiscount;
        this.isActive = isActive;
    }
}
