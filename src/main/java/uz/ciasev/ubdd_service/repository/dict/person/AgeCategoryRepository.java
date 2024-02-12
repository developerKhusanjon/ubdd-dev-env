package uz.ciasev.ubdd_service.repository.dict.person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;
import uz.ciasev.ubdd_service.repository.dict.AbstractDictRepository;

import java.util.Optional;

public interface AgeCategoryRepository extends AbstractDictRepository<AgeCategory> {

    @Query("SELECT a " +
            " FROM AgeCategory a " +
            "WHERE a.ageFrom <= :years " +
            "  AND a.ageTo >= :years " +
            "  AND a.isViolatorOnly = :isViolatorOnly")
    Optional<AgeCategory> findByFullYears(@Param("isViolatorOnly") Boolean isViolatorOnly,
                                          @Param("years") Integer years);
}
