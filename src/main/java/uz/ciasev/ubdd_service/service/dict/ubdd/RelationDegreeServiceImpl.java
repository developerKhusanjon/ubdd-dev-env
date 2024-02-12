package uz.ciasev.ubdd_service.service.dict.ubdd;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.ubdd.RelationDegree;
import uz.ciasev.ubdd_service.repository.dict.ubdd.RelationDegreeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class RelationDegreeServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<RelationDegree>
        implements RelationDegreeService {

    private final String subPath = "ubdd-relation-degree";

    private final RelationDegreeRepository repository;
    private final Class<RelationDegree> entityClass = RelationDegree.class;
}
