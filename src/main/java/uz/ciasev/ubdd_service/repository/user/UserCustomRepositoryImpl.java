package uz.ciasev.ubdd_service.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.user.User_;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepositoryImpl;

import javax.persistence.EntityManager;

public class UserCustomRepositoryImpl extends EntityRepositoryImpl<User> implements UserCustomRepository {

    @Autowired
    public UserCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, User.class, User_.id);
    }
}
