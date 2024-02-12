package uz.ciasev.ubdd_service.entity.dict.requests;

import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;

public interface CourtConsideringAdditionCreateDTOI extends ExternalDictCreateDTOI {
    CourtConsideringBasis getCourtConsideringBasis();
}
