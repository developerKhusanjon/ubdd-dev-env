package uz.ciasev.ubdd_service.service.dict.validation.validators;

import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;
import uz.ciasev.ubdd_service.entity.dict.requests.CourtConsideringAdditionCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;


@Component
public class CourtConsideringAdditionCreateValidator implements DictionaryCreateValidator<CourtConsideringAddition, CourtConsideringAdditionCreateDTOI> {

    @Override
    public void validate(DictionaryServiceWithRepository<CourtConsideringAddition> service, CourtConsideringAdditionCreateDTOI request) {
        if (!request.getCourtConsideringBasis().getHasAdditions()) {
            throw new ValidationException(ErrorCode.COURT_CONSIDERING_BASIS_SET_TO_HAS_NO_ADDITIONS);
        }
    }

    @Override
    public Class<CourtConsideringAddition> getValidatedType() {
        return CourtConsideringAddition.class;
    }

    @Override
    public Class<CourtConsideringAdditionCreateDTOI> getRequestType() {
        return CourtConsideringAdditionCreateDTOI.class;
    }
}
