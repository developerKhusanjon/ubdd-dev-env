package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.settings.*;

import javax.persistence.EntityManager;

@Repository
public class OrganConsideredArticlePartSettingsCustomRepositoryImpl extends AbstractOrganArticlePartSettingsCustomRepository<OrganConsideredArticlePartSettings> implements OrganConsideredArticlePartSettingsCustomRepository {

    @Autowired
    public OrganConsideredArticlePartSettingsCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, OrganConsideredArticlePartSettings.class, OrganConsideredArticlePartSettings_.id);
    }
}
