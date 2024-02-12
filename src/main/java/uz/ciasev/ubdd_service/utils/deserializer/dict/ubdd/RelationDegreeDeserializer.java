package uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.ubdd.RelationDegree;
import uz.ciasev.ubdd_service.service.dict.ubdd.RelationDegreeService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

@Component
public class RelationDegreeDeserializer extends AbstractDictDeserializer<RelationDegree> {

    @Autowired
    public RelationDegreeDeserializer(RelationDegreeService relationDegreeService) {
        super(RelationDegree.class, relationDegreeService);
    }
}