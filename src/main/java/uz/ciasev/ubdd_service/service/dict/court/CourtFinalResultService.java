package uz.ciasev.ubdd_service.service.dict.court;

import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResult;
import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias;

import java.util.Optional;

public interface CourtFinalResultService {

    Optional<CourtFinalResult> findByAlias(CourtFinalResultAlias alias);
    Optional<CourtFinalResult> findByCode(Long code);
    CourtFinalResult getByCode(Long code);
    Optional<CourtFinalResult> findById(Long id);
}
