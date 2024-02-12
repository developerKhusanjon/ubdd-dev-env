package uz.ciasev.ubdd_service.entity.dict.requests;

public interface ViolationRepeatabilityStatusUpdateDTOI extends ExternalDictUpdateDTOI {
    Boolean getIsNeedEarlierViolatedArticleParts();
}
