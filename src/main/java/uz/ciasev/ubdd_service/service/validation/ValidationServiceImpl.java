package uz.ciasev.ubdd_service.service.validation;

import org.springframework.stereotype.Service;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean checkRegionNotIn(RegionDistrict object, Region region) {
        //  Республеканскому пользователю доступно все
        if (object.getRegion() == null) {
            return false;
        }

        //  Республеканскому пользователю доступно все
        if (object.getRegion().getIsState()) {
            return false;
        }

        //  Облостной пользователь может выберать тоько свою область
        return !object.getRegion().equals(region);
    }

    @Override
    public boolean checkDistrictNotIn(RegionDistrict object, District district) {
        //  Республеканскому пользователю доступно все
        if (object.getRegion() == null) {
            return false;
        }
        //  Республеканскому пользователю доступно все
        if (object.getRegion().getIsState()) {
            return false;
        }

        if (object.getDistrict() == null) {
            //  Облостной пользователь может выбирать все районы своей области или выбирать область
            return !(district == null || district.isPartOfRegion(object.getRegion()));
        }

        if (object.getDistrict().getIsState()) {
            //  Облостной пользователь может выбирать все районы своей области или выбирать область
            return !(district == null || district.isPartOfRegion(object.getRegion()));
        }

        //  Районыый пользователь может выирать только свой район
        return !object.getDistrict().equals(district);
    }

    @Override
    public boolean checkRegionNotInUser(User user, Region region) {
        //  Республеканскому пользователю доступно все
        if (user.getRegion() == null) {
            return false;
        }

        //  Облостной пользователь может выберать тоько свою область
        return !user.getRegion().equals(region);
    }

    @Override
    public boolean checkDistrictNotInUser(User user, District district) {
        //  Республеканскому пользователю доступно все
        if (user.getRegion() == null) {
            return false;
        }

        if (user.getDistrict() == null) {
            //  Облостной пользователь может выбирать все районы своей области или выбирать область
            return !(district == null || district.isPartOfRegion(user.getRegion()));
        }

        //  Районыый пользователь может выирать только свой район
        return !user.getDistrict().equals(district);
    }

//    @Override
//    public boolean checkDistrictNotInUserRegion(User user, District district) {
//        return user != null && district != null && user.getRegionId() != null && !user.getRegionId().equals(district.getRegionId());
//    }

    @Override
    public boolean checkMtpNotInDistrict(District district, Mtp mtp) {
        //  Пустое МТП входит в любой район
        if (mtp == null) {
            return false;
        }

        return !mtp.isPartOfDistrict(district);
//        return !mtp.getDistrict().equals(district);
    }

    @Override
    public boolean checkDistrictNotInRegion(Region region, District district) {
        //  Пустой дистрикт входит в любую область
        if (district == null) {
            return false;
        }

        return !district.isPartOfRegion(region);
    }

    @Override
    public boolean checkDepartmentNotInOrgan(Organ organ, Department department) {
        //  Пустое подразделение входит в любой орган
        if (department == null) {
            return false;
        }

        return !department.isPartOfOrgan(organ);
    }

    @Override
    public boolean checkOrganNotInUser(User user, Organ organ) {

        //  Республеканскому пользователю доступно все
        if (user.getOrgan() == null) {
            return false;
        }

        //  Облостной пользователь может выберать тоько свою область
        return !user.getOrgan().equals(organ);
    }

    @Override
    public boolean checkDepartmentNotInUser(User user, Department department) {
        //  Республеканскому пользователю доступно все
        if (user.getOrgan() == null) {
            return false;
        }

        if (user.getDepartment() == null) {
            //  Облостной пользователь может выбирать все районы своей области или выбирать область
            return !(department == null || department.isPartOfOrgan(user.getOrgan()));
        }

        //  Районыый пользователь может выирать только свой район
        return !user.getDepartment().equals(department);
    }

    @Override
    public boolean checkNotSameOrgan(Place admCase, Organ organ) {
        return admCase != null && admCase.getOrgan() != null && !admCase.getOrgan().equals(organ);
    }

    @Override
    public boolean checkAllPartConsideredByCourt(List<ArticlePart> parts) {
        return parts.stream()
                .allMatch(ArticlePart::isCourtOnly);
    }

    @Override
    public boolean checkAllPartNotConsideredByCourt(List<ArticlePart> parts) {
        return parts.stream()
                .noneMatch(ArticlePart::isCourtOnly);
    }

    @Override
    public boolean checkCourtConsideringAdditionNotInBasis(CourtConsideringBasis courtConsideringBasis,
                                                           CourtConsideringAddition courtConsideringAddition) {
        return !(courtConsideringBasis.getHasAdditions()
                ? (courtConsideringAddition != null && courtConsideringBasis.getId().equals(courtConsideringAddition.getCourtConsideringBasisId()))
                : courtConsideringAddition == null);
    }

    @Override
    public boolean checkNotCurrentYear(LocalDateTime registrationTime) {
        return registrationTime.isBefore(LocalDateTime.now().minusYears(1));
    }

    @Override
    public boolean checkPartNotInArticle(Article article, ArticlePart articlePart) {
        if (article == null) {
            return true;
        }
        return (!article.getId().equals(articlePart.getArticleId()));
    }

    @Override
    public boolean checkUserHaveNoAccess(User user, Long regionId, Long districtId, Long organId, Long departmentId) {
        return (
                (user.getOrganId() != null && !user.getOrganId().equals(organId))
                        || (user.getDepartmentId() != null && !user.getDepartmentId().equals(departmentId))
                        || (user.getRegionId() != null && !user.getRegionId().equals(regionId))
                        || (user.getDistrictId() != null && !user.getDistrictId().equals(districtId))
        );
    }

    @Override
    public boolean checkUserHaveNoAccess(User user, Long regionId, Long districtId, Long departmentId) {
        return (
                (user.getDepartmentId() != null && !user.getDepartmentId().equals(departmentId))
                        || (user.getRegionId() != null && !user.getRegionId().equals(regionId))
                        || (user.getDistrictId() != null && !user.getDistrictId().equals(districtId))
        );
    }

    @Override
    public boolean checkHasDuplicate(List<Long> list) {
        Set<Long> passedValue = new HashSet<>();

        for (Long e : list) {
            if (!passedValue.add(e)) {
                return true;
            }
        }

        return false;
        //  Если в списке есть null, то решение со стримом ломаеться
        //        return list
        //                .stream()
        //                .filter(e -> !passedValue.add(e))
        //                .findFirst()
        //                .isPresent();

    }

    @Override
    public boolean checkEmploymentPlace(Occupation occupation, String employmentPlace) {
        if (occupation.isNotWorker()) {
            return true;
        }

        if (employmentPlace == null || employmentPlace.isBlank()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean checkEmploymentPosition(Occupation occupation, String employmentPosition) {
        if (occupation.isNotWorker()) {
            return true;
        }

        if (employmentPosition == null || employmentPosition.isBlank()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isNotEquals(Set<Long> set1, Set<Long> set2) {
        Set<Long> s1 = new HashSet<>(set1);
        Set<Long> s2 = new HashSet<>(set2);


        Set<Long> intersection = new HashSet<>();
        intersection.addAll(s1);
        intersection.retainAll(s2);

        s1.addAll(s2);
        s1.removeAll(intersection);

        return !s1.isEmpty();
    }

    @Override
    public boolean checkViolationTypePresenceInArticleRequest(ArticleRequest articleRequest) {
        ArticlePart articlePart = articleRequest.getArticlePart();

        if (articlePart == null) return true;

        return !articlePart.getIsViolationTypeRequired() || articleRequest.getArticleViolationType() != null;
    }
}