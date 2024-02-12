package uz.ciasev.ubdd_service.service.publicapi.eventdata;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.service.publicapi.dto.eventdata.PublicApiWebhookEventDataCourtDTO;

import java.util.List;

public interface PublicApiWebhookEventCourtDataService {

    void setCourtDTOForMerge(PublicApiWebhookEventDataCourtDTO courtDTO, AdmCase mergedToCase);

    void setCourtDTOForSeparation(PublicApiWebhookEventDataCourtDTO courtDTO, AdmCase separatedCase);

    void setCourtDTOForResolution(PublicApiWebhookEventDataCourtDTO courtDTO, Resolution resolution, List<Decision> decisions);

    void setCourtDTOForFields(PublicApiWebhookEventDataCourtDTO courtDTO, CourtCaseFields courtCaseFields);

    void setCourtDTOForFields(PublicApiWebhookEventDataCourtDTO courtDTO, CourtCaseChancelleryData courtCaseFields);
}
