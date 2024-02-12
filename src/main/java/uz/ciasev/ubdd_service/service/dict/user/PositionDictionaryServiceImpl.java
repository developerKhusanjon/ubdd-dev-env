package uz.ciasev.ubdd_service.service.dict.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.repository.dict.user.PositionRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class PositionDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<Position>
        implements PositionDictionaryService {

    private final String subPath = "positions";

    private final PositionRepository repository;
    private final Class<Position> entityClass = Position.class;
}
