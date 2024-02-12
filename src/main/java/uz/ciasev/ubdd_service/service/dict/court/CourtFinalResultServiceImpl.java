package uz.ciasev.ubdd_service.service.dict.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResult;
import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.dict.court.CourtFinalResultRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourtFinalResultServiceImpl implements CourtFinalResultService {

    private final CourtFinalResultRepository courtFinalResultRepository;

    @Override
    public Optional<CourtFinalResult> findByAlias(CourtFinalResultAlias alias) {
        return courtFinalResultRepository.findByAlias(alias);
    }

    @Override
    public Optional<CourtFinalResult> findByCode(Long code) {
        return courtFinalResultRepository.findByCode(code.toString());
    }

    @Override
    public CourtFinalResult getByCode(Long code) {
        return findByCode(code)
                .orElseThrow(() -> new EntityByParamsNotFound(CourtFinalResult.class, "code", code));
    }

    @Override
    public Optional<CourtFinalResult> findById(Long id) {
        return courtFinalResultRepository.findById(id);
    }
}
