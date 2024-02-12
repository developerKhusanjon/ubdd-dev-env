package uz.ciasev.ubdd_service.service.dict.court;

import uz.ciasev.ubdd_service.entity.dict.court.CourtDeclineReason;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryService;

import java.util.List;

public interface CourtDeclineReasonService extends SimpleExternalIdDictionaryService<CourtDeclineReason> {
    void saveAnyNewReasonInReasons(List<Long> declineReasons);
}
