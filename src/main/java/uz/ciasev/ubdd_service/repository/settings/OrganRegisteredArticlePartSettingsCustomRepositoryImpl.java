package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.ciasev.ubdd_service.entity.settings.OrganRegisteredArticlePartSettings;
import uz.ciasev.ubdd_service.entity.settings.OrganRegisteredArticlePartSettings_;

import javax.persistence.EntityManager;

@Repository
public class OrganRegisteredArticlePartSettingsCustomRepositoryImpl extends AbstractOrganArticlePartSettingsCustomRepository<OrganRegisteredArticlePartSettings> implements OrganRegisteredArticlePartSettingsCustomRepository {
    @Autowired
    public OrganRegisteredArticlePartSettingsCustomRepositoryImpl(EntityManager entityManager) {
        super(entityManager, OrganRegisteredArticlePartSettings.class, OrganRegisteredArticlePartSettings_.id);
    }
}
