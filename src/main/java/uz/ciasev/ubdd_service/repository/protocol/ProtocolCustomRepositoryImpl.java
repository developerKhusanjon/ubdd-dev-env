package uz.ciasev.ubdd_service.repository.protocol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.Protocol_;

import javax.persistence.EntityManager;

@Repository
public class ProtocolCustomRepositoryImpl extends EntityRepositoryImpl<Protocol> implements ProtocolCustomRepository {

    @Autowired
    public ProtocolCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Protocol.class, Protocol_.id);
    }
}
