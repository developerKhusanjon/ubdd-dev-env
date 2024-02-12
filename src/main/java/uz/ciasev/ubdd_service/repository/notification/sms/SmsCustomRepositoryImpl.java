package uz.ciasev.ubdd_service.repository.notification.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;

@Repository
public class SmsCustomRepositoryImpl extends EntityRepositoryImpl<SmsNotification> implements SmsCustomRepository {

    @Autowired
    public SmsCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, SmsNotification.class, SmsNotification_.id);
    }
}
