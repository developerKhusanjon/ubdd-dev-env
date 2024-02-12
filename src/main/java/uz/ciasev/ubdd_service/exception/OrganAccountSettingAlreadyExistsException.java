package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

import javax.annotation.Nullable;
import java.util.Optional;

public class OrganAccountSettingAlreadyExistsException extends ValidationException {

    private OrganAccountSettingAlreadyExistsException(String message) {
        super(
                ErrorCode.ORGAN_ACCOUNT_SETTING_ALREADY_EXISTS,
                message
        );
    }

    public OrganAccountSettingAlreadyExistsException(Place place, @Nullable ArticlePart articlePart, Long bankAccountType) {
        this(
                String.format("Bank account with type %s for organ=%s, department=%s, region=%s, district=%s, articlePart=%s already exists",
                        bankAccountType,
                        place.getOrganId(),
                        place.getDepartmentId(),
                        place.getRegionId(),
                        place.getDistrictId(),
                        Optional.ofNullable(articlePart).map(AbstractEmiDict::getId).orElse(null))
        );
    }

    public OrganAccountSettingAlreadyExistsException(Long existCount) {
        this(String.format("For requested place already exists %s setting rows", existCount));
    }
}
