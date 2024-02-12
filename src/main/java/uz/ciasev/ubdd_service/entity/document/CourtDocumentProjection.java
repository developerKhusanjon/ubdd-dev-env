package uz.ciasev.ubdd_service.entity.document;

import uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias;
import uz.ciasev.ubdd_service.utils.FioUtils;

public interface CourtDocumentProjection {

    Long getId();
    String getUrl();
    Long getDocumentTypeId();
    DocumentTypeAlias getDocumentTypeAlias();
    String getFailFormatCode();
    String getPersonFirstName();
    String getPersonSecondName();
    String getPersonLastName();

    default String getPersonFIO() {
        return FioUtils.buildFullFio(getPersonFirstName(), getPersonSecondName(), getPersonLastName());
    }
}