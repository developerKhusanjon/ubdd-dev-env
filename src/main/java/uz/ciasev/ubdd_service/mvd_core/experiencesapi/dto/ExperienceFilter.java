package uz.ciasev.ubdd_service.mvd_core.experiencesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.experiencesapi.external.mehnat.experience.response.MehnatExperience;

import java.util.function.Predicate;

@Getter
@Setter
@AllArgsConstructor
public class ExperienceFilter {

    private Boolean current;

    public boolean apply (MehnatExperience experience) {

        Predicate<MehnatExperience> root = e -> true;
        Predicate<MehnatExperience> currentCheck = e -> experience.getEndDate() == null;

        if (Boolean.TRUE.equals(current)) {
            root = root.and(currentCheck);
        }

        return root.test(experience);
    }
}
