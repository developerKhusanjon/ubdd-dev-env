package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias;
import uz.ciasev.ubdd_service.entity.dict.requests.MibCaseStatusUpdateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class MibCaseStatusUpdateRequestDTO extends ExternalDictUpdateRequestDTO<MibCaseStatus> implements MibCaseStatusUpdateDTOI {

    @NotNull(message = ErrorCode.ALIAS_REQUIRED)
    protected MibCaseStatusAlias alias;

    @NotNull(message = ErrorCode.IS_SUSPENSION_ARTICLE_REQUIRED)
    private Boolean isSuspensionArticle;

    @Override
    public void applyToOld(MibCaseStatus entity) {
        entity.update(this);
    }
}
