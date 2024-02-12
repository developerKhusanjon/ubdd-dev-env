package uz.ciasev.ubdd_service.repository.resolution.decision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;

@Repository
public class DecisionCustomRepositoryImpl extends EntityRepositoryImpl<Decision> implements DecisionCustomRepository {

    @Autowired
    public DecisionCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Decision.class, Decision_.id);
    }
}
