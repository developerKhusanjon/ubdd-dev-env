package uz.ciasev.ubdd_service.service.dict.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtPerformanceType;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.dict.court.CourtPerformanceTypeRepository;

@Service
@RequiredArgsConstructor
public class CourtPerformanceTypeServiceImpl implements CourtPerformanceTypeService {

    private final CourtPerformanceTypeRepository courtPerformanceTypeRepository;

    @Override
    public CourtPerformanceType getById(Long id) {
        return courtPerformanceTypeRepository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(CourtPerformanceType.class, id));
    }
}
