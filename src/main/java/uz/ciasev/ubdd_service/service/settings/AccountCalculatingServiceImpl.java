package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;


@RequiredArgsConstructor
@Service
public class AccountCalculatingServiceImpl implements AccountCalculatingService {

    private final OrganSettingsService organSettingsService;
    private final ProtocolService protocolService;


    @Override
    public OrganAccountSettings calculateForOrgan(Place place, ArticlePart decisionArticlePart, Long bankAccountType) {
        return getOrganBankAccountFor(place, decisionArticlePart, bankAccountType);
    }

    @Override
    public OrganAccountSettings calculateForCourtPenalty(AdmCase admCase, Violator violator, Place courtPlace) {
        return calculateForCourt(admCase, violator, courtPlace, admCase.getOrgan().getIsCourtPenaltyRecipient());
    }

    @Override
    public OrganAccountSettings calculateForCourtCompensation(AdmCase admCase, Violator violator, Place courtPlace) {
        return calculateForCourt(admCase, violator, courtPlace, admCase.getOrgan().getIsCourtCompensationRecipient());
    }

    private OrganAccountSettings calculateForCourt(AdmCase admCase, Violator violator, Place courtPlace, Boolean isCourtMoneyRecipient) {
        if (isCourtMoneyRecipient
                // todo переписать на врозумительное 315
                || admCase.getCourtConsideringBasisId() == -1L) {

            if (admCase.getOrgan().getIsArticleLevelMoneySeparation()) {
                ArticlePart mainArticlePart = protocolService.getViolatorMainArticle(violator);
                return getOrganBankAccountFor(admCase, mainArticlePart, 1L);
            } else {
                return getOrganBankAccountFor(admCase);
            }
        }
        return getOrganBankAccountFor(courtPlace);
    }

    private OrganAccountSettings getOrganBankAccountFor(Place placeRequest, ArticlePart articlePart, Long bankAccountType) {
        if (placeRequest.getOrgan().getIsArticleLevelMoneySeparation()) {
            return organSettingsService.getOrganBankAccountMaybeWithArticle(
                    placeRequest,
                    bankAccountType,
                    articlePart
            );
        } else {
            return getOrganBankAccountFor(placeRequest, bankAccountType);
        }
    }

    private OrganAccountSettings getOrganBankAccountFor(Place placeRequest, Long bankAccountType) {
        return organSettingsService.getOrganBankAccount(
                placeRequest,
                bankAccountType
        );
    }

    private OrganAccountSettings getOrganBankAccountFor(Place placeRequest) {
        return getOrganBankAccountFor(placeRequest, 1L);
    }
}