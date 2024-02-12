package uz.ciasev.ubdd_service.repository.violator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.Violator_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;

@Repository
public class ViolatorRepositoryCustomImpl extends EntityRepositoryImpl<Violator> implements ViolatorRepositoryCustom {

    @Autowired
    public ViolatorRepositoryCustomImpl(EntityManager entityManager) {
        super(entityManager, Violator.class, Violator_.id);
    }
}
