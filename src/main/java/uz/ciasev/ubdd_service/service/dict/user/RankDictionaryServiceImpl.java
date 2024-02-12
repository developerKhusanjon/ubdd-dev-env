package uz.ciasev.ubdd_service.service.dict.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.repository.dict.user.RankRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class RankDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<Rank>
        implements RankDictionaryService {

    private final String subPath = "ranks";

    private final RankRepository repository;
    private final Class<Rank> entityClass = Rank.class;
}
