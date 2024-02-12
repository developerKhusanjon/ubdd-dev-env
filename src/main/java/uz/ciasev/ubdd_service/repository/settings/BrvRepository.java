package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.settings.Brv;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BrvRepository extends JpaRepository<Brv, Long> {

    @Query(value ="SELECT amount FROM {h-schema}d_brv WHERE from_date <= :date AND to_date IS NULL" +
            " UNION ALL " +
            " SELECT amount FROM {h-schema}d_brv WHERE from_date <= :date AND to_date >= :date", nativeQuery = true)
    Optional<Long> findByDate(@Param("date") LocalDate date);


    @Query("SELECT brv FROM Brv brv WHERE brv.toDate IS NULL")
    List<Brv> findCurrent();
}
