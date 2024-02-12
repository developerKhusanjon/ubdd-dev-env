package uz.ciasev.ubdd_service.service.validation;

import uz.ciasev.ubdd_service.dto.internal.request.ArticleRequest;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.RegionDistrict;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface ValidationService {

    boolean checkRegionNotIn(RegionDistrict object, Region region);

    boolean checkDistrictNotIn(RegionDistrict object, District district);

    boolean checkOrganNotInUser(User admin, Organ organ);

    boolean checkDepartmentNotInUser(User admin, Department department);

    boolean checkRegionNotInUser(User user, Region region);

    boolean checkDistrictNotInUser(User user, District district);

    boolean checkDistrictNotInRegion(Region region, District district);

    boolean checkDepartmentNotInOrgan(Organ organ, Department department);

    boolean checkMtpNotInDistrict(District district, Mtp protocolMtp);

    boolean checkNotSameOrgan(Place place, Organ organ);

    boolean checkUserHaveNoAccess(User user, Long regionId, Long districtId, Long organId, Long departmentId);

    boolean checkUserHaveNoAccess(User user, Long regionId, Long districtId, Long departmentId);

    boolean checkNotCurrentYear(LocalDateTime entityTime);

    boolean checkEmploymentPlace(Occupation occupation, String employmentPlace);

    boolean checkEmploymentPosition(Occupation occupation, String employmentPosition);

    boolean checkCourtConsideringAdditionNotInBasis(CourtConsideringBasis courtConsideringBasis, CourtConsideringAddition courtConsideringAddition);

    boolean checkHasDuplicate(List<Long> list);

    boolean isNotEquals(Set<Long> s1, Set<Long> s2);

    boolean checkPartNotInArticle(Article article, ArticlePart articlePart);

    boolean checkAllPartConsideredByCourt(List<ArticlePart> parts);

    boolean checkAllPartNotConsideredByCourt(List<ArticlePart> parts);

    boolean checkViolationTypePresenceInArticleRequest(ArticleRequest articleRequest);
}
