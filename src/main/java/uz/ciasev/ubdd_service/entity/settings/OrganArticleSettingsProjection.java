package uz.ciasev.ubdd_service.entity.settings;

public interface OrganArticleSettingsProjection {

    Long getId();

    Long getArticleId();

    Long getArticlePartId();

    Boolean getIsDiscount();

    Boolean getIsActive();

}
