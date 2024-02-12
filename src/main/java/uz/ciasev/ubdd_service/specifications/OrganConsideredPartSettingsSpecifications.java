package uz.ciasev.ubdd_service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.settings.OrganConsideredArticlePartSettings;
import uz.ciasev.ubdd_service.entity.settings.OrganConsideredArticlePartSettings_;

@Component
public class OrganConsideredPartSettingsSpecifications extends OrganArticlePartSettingsSpecifications<OrganConsideredArticlePartSettings> {
    public Specification<OrganConsideredArticlePartSettings> withIsHeaderOnly(Boolean value) {
        return ((root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get(OrganConsideredArticlePartSettings_.isHeaderOnly), value);
        });
    }
}
