package uz.ciasev.ubdd_service.repository.resolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;

@Repository
public class ResolutionCustomRepositoryImpl extends EntityRepositoryImpl<Resolution> implements ResolutionCustomRepository {

    @Autowired
    public ResolutionCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Resolution.class, Resolution_.id);
    }
}
