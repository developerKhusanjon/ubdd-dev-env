package uz.ciasev.ubdd_service.repository.notification.manual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.notification.manual.ManualNotification;
import uz.ciasev.ubdd_service.entity.notification.manual.ManualNotification_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;

@Repository
public class ManualNotificationCustomRepositoryImpl extends EntityRepositoryImpl<ManualNotification> implements ManualNotificationCustomRepository {

    @Autowired
    public ManualNotificationCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, ManualNotification.class, ManualNotification_.id);
    }
}
