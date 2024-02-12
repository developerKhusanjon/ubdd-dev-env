package uz.ciasev.ubdd_service.service.settings;

import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;

public interface OrganSettingsService {

    OrganInfo getOrganInfo(Place user);

    OrganAccountSettings getOrganBankAccount(Place place, Long bankAccountType);

    OrganAccountSettings getOrganBankAccountMaybeWithArticle(Place place, Long bankAccountType, ArticlePart articlePart);
}
