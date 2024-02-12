package uz.ciasev.ubdd_service.exception.court;

import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;

public class IgnoredMaterialTypeForExistsMaterialException extends ExternalException {

    public IgnoredMaterialTypeForExistsMaterialException(CourtMaterialType materialType) {
        super(CourtResult.VALIDATION_ERROR, String.format("Request try edit registered material type on ignored material type with id %s!", materialType.getId()));
    }

    @Override
    public void setEnvelopeId(Long envelopeId) {
        // на случай если суд сдаст сбой, и начнет слать решения, что бы не возникло путаницы с id лога и id материала
    }
}
