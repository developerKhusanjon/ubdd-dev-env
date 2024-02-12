package uz.ciasev.ubdd_service.dto.internal.request.settings;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.request.DictIdListValueOrAllType;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class DistrictIdListType extends DictIdListValueOrAllType<District> {

    @NotNull(message = ErrorCode.IS_HEAD_REQUIRED)
    private Boolean isHead;

    public static DistrictIdListType ofAllWithHead() {
        var r = new DistrictIdListType();
        r.setIsAll(true);
        r.setValue(null);
        r.setIsHead(true);
        return r;
    }

    @Override
    public boolean isValueEmpty() {
        if (Boolean.TRUE.equals(isHead)) {
            return false;
        }
        return super.isValueEmpty();
    }
}
