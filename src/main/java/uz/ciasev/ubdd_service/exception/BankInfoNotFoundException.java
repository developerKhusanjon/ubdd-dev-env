package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

import javax.annotation.Nullable;
import java.util.Optional;

public class BankInfoNotFoundException extends ValidationException {

    public BankInfoNotFoundException(Place place, @Nullable ArticlePart articlePart, Long bankAccountType) {
        super(
                ErrorCode.BANK_INFO_NOT_FOUND,
                String.format("Bank account with type %s not found for organ=%s, department=%s, region=%, district=%s, articlePart=%s",
                        bankAccountType,
                        place.getOrganId(),
                        place.getDepartmentId(),
                        place.getRegionId(),
                        place.getDistrictId(),
                        Optional.ofNullable(articlePart).map(AbstractEmiDict::getId).orElse(null))
        );
    }
}
