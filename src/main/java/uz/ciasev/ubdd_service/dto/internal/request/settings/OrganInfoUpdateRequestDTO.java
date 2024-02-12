package uz.ciasev.ubdd_service.dto.internal.request.settings;

import lombok.Getter;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.settings.OrganInfoDescription;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class OrganInfoUpdateRequestDTO implements OrganInfoDescription {

    @NotNull(message = ErrorCode.ADDRESS_REQUIRED)
    @Size(min = 1, max = 300, message = ErrorCode.ORGAN_INFO_ADDRESS_MIN_MAX_LENGTH)
    private String address;

    @NotNull(message = ErrorCode.LANDLINE_REQUIRED)
    @Size(min = 1, max = 30, message = ErrorCode.ORGAN_INFO_LANDLINE_MIN_MAX_LENGTH)
    private String landline;

    @NotNull(message = ErrorCode.POST_INDEX_REQUIRED)
    @Size(min = 1, max = 10, message = ErrorCode.ORGAN_INFO_POST_INDEX_MIN_MAX_LENGTH)
    private String postIndex;
}
