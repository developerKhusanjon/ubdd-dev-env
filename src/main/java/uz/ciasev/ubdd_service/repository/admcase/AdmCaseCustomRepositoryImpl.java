package uz.ciasev.ubdd_service.repository.admcase;

import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;

@Repository
public class AdmCaseCustomRepositoryImpl extends EntityRepositoryImpl<AdmCase> implements AdmCaseCustomRepository {

    public AdmCaseCustomRepositoryImpl(EntityManager entityManager) {
       super(entityManager, AdmCase.class, AdmCase_.id);
    }
}
