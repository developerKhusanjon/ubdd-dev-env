package uz.ciasev.ubdd_service.repository.resolution.punishment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestPunishment_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;

@Repository
public class ArrestCustomRepositoryImpl extends EntityRepositoryImpl<ArrestPunishment> implements ArrestCustomRepository {

    @Autowired
    public ArrestCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, ArrestPunishment.class, ArrestPunishment_.id);
    }
}
