package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;


@Service
@RequiredArgsConstructor
public class OrganSettingsServiceImpl implements OrganSettingsService {

    private final OrganInfoSearchService infoSearchService;
    private final OrganAccountService accountService;

    @Override
    public OrganInfo getOrganInfo(Place user) {
        return infoSearchService.findByPlace(user).orElseThrow(() -> new EntityByParamsNotFound(OrganInfo.class, user));
    }

    @Override
    public OrganAccountSettings getOrganBankAccount(Place place, Long bankAccountType) {
        return accountService.getByPlace(place, null, bankAccountType);
    }

    public OrganAccountSettings getOrganBankAccountMaybeWithArticle(Place place,
                                                                    Long bankAccountType,
                                                                    ArticlePart articlePart) {
        return accountService.findByPlace(place, articlePart, bankAccountType).orElseGet(() -> getOrganBankAccount(place, bankAccountType));
    }
}
