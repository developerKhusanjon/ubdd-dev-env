package uz.ciasev.ubdd_service.entity.dict.requests;

import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias;

public interface MibCaseStatusUpdateDTOI extends ExternalDictUpdateDTOI {

    MibCaseStatusAlias getAlias();
    Boolean getIsSuspensionArticle();
}
