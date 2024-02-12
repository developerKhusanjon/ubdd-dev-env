package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtDeclineReason;
import uz.ciasev.ubdd_service.repository.dict.court.CourtDeclineReasonRepository;
import uz.ciasev.ubdd_service.service.UnknownValueService;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourtDeclineReasonServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<CourtDeclineReason>
        implements CourtDeclineReasonService {

    @Getter
    private final String subPath = "court-decline-reasons";

    @Getter
    private final CourtDeclineReasonRepository repository;

    @Getter
    private final Class<CourtDeclineReason> entityClass = CourtDeclineReason.class;
    private final UnknownValueService unknownValueService;

    private static Set<Long> cachedReasonsIds = new HashSet<>();

    public void saveAnyNewReasonInReasons(List<Long> declineReasons) {

        for (Long reason : declineReasons) {
            if (cachedReasonsIds.contains(reason)) continue;

            if (repository.existsById(reason)) cachedReasonsIds.add(reason);

            else repository.save(new CourtDeclineReason(
                    reason,
                    reason,
                    unknownValueService.getDictName()));
        }
    }
}
