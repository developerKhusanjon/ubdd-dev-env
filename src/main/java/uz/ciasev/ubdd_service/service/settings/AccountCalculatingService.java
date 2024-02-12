package uz.ciasev.ubdd_service.service.settings;

import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.violator.Violator;

public interface AccountCalculatingService {

    OrganAccountSettings calculateForOrgan(Place place, ArticlePart decisionArticlePart, Long bankAccountType);

    OrganAccountSettings calculateForCourtPenalty(AdmCase admCase, Violator violator, Place courtPlace);

    OrganAccountSettings calculateForCourtCompensation(AdmCase admCase, Violator violator, Place courtPlace);
}
