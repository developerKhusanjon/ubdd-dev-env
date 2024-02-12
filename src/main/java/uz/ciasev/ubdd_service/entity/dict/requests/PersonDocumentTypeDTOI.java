package uz.ciasev.ubdd_service.entity.dict.requests;

import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipCalculatingMode;

public interface PersonDocumentTypeDTOI extends DictUpdateDTOI, DictCreateDTOI {

    Boolean getIsBiometric();

    CitizenshipCalculatingMode getCitizenshipCalculatingMode();

}
