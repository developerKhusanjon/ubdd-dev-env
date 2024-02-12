package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.Bank;
import uz.ciasev.ubdd_service.entity.dict.requests.BankDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class BankRequestDTO extends BaseDictRequestDTO implements BankDTOI, DictCreateRequest<Bank>, DictUpdateRequest<Bank> {

    @NotNull(message = ErrorCode.MFO_REQUIRED)
    @Size(min= 3, max = 5, message = ErrorCode.MFO_MIN_MAX_SIZE)
    private String mfo;

    @Override
    public void applyToNew(Bank entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(Bank entity) {
        entity.update(this);
    }
}
