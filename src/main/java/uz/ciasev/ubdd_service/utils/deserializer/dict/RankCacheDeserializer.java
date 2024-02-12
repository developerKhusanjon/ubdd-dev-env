package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class RankCacheDeserializer extends AbstractDictDeserializer<Rank> {

    @Autowired
    public RankCacheDeserializer(DictionaryService<Rank> rankService) {
        super(Rank.class, rankService);
    }
}