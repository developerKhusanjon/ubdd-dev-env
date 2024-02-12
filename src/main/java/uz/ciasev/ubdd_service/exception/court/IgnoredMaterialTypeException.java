package uz.ciasev.ubdd_service.exception.court;

import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;

public class IgnoredMaterialTypeException extends ExternalException {

    public IgnoredMaterialTypeException(CourtMaterialType materialType) {
        super(CourtResult.SUCCESSFULLY, String.format("Material type with id %s is ignored!", materialType.getId()));
    }

    @Override
    public void setEnvelopeId(Long envelopeId) {
        // на случай если суд сдаст сбой, и начнет слать решения, что бы не возникло путаницы с id лога и id материала
    }
}
