package uz.ciasev.ubdd_service.repository.notification.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;
import uz.ciasev.ubdd_service.specifications.MailNotificationSpecifications;

import javax.persistence.EntityManager;

@Repository
public class MailNotificationCustomRepositoryImpl extends EntityRepositoryImpl<MailNotification> implements MailNotificationCustomRepository {

    private final EntityManager entityManager;
    private final MailNotificationSpecifications specifications;

    @Autowired
    public MailNotificationCustomRepositoryImpl(EntityManager entityManager, MailNotificationSpecifications specifications) {
        super(entityManager, MailNotification.class, MailNotification_.id);
        this.entityManager = entityManager;
        this.specifications = specifications;
    }

//    @Override
//    public Optional<MailNotificationLiteProjection> findByIdLite(Long id) {
//        return findByOneLite(specifications.withId(id));
//    }
//
//    @Override
//    public Optional<MailNotificationLiteProjection> findByOneLite(Specification<MailNotification> specification) {
//        List<MailNotificationLiteProjection> res = findAllLite(specification);
//
//        if (res.size() > 1) {
//            throw new IncorrectResultSizeDataAccessException(1, res.size());
//        }
//
//        return res.stream().findFirst();
//    }
//
//    @Override
//    public List<MailNotificationLiteProjection> findAllLite(Specification<MailNotification> specification) {
//        return findAllLite(specification, Sort.unsorted());
//    }
//
//    @Override
//    public List<MailNotificationLiteProjection> findAllLite(Specification<MailNotification> specification, Sort sort) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//
//        CriteriaQuery<MailNotificationLiteProjection> cq = criteriaBuilder.createQuery(MailNotificationLiteProjection.class).distinct(true);
//        Root<MailNotification> root = cq.from(MailNotification.class);
//
//        List<Selection<?>> selectedFields = List.of(
//                root.get(MailNotification_.id),
//                root.get(MailNotification_.createdTime),
//                root.get(MailNotification_.userId),
//                root.get(MailNotification_.decisionId),
//                root.get(MailNotification_.violatorId),
//                root.get(MailNotification_.notificationType),
//                root.get(MailNotification_.fio),
//                root.get(MailNotification_.address),
//                root.get(MailNotification_.areaId),
//                root.get(MailNotification_.regionId),
//                root.get(MailNotification_.organInfoId),
//                root.get(MailNotification_.messageNumber),
//                root.get(MailNotification_.sendTime),
//                root.get(MailNotification_.receiveDate),
//                root.get(MailNotification_.sendStatus),
//                root.get(MailNotification_.receiveStatus),
//                root.get(MailNotification_.mailStatusDescription),
//                root.get(MailNotification_.deliveryStatusId),
//                root.get(MailNotification_.changeStatusTime)
//        );
//
//        cq.multiselect(selectedFields);
//        cq.where(specification.toPredicate(root, cq, criteriaBuilder));
//        cq.orderBy(QueryUtils.toOrders(sort, root, criteriaBuilder));
//
//        return entityManager.createQuery(cq)
//                .getResultList();
//    }
}
