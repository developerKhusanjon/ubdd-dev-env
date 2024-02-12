package uz.ciasev.ubdd_service.repository.admcase.deletion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;

@Repository
public class AdmCaseDeletionRequestCustomRepositoryImpl extends EntityRepositoryImpl<AdmCaseDeletionRequest> implements AdmCaseDeletionRequestCustomRepository {

    @Autowired
    public AdmCaseDeletionRequestCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, AdmCaseDeletionRequest.class, AdmCaseDeletionRequest_.id);
    }
}
