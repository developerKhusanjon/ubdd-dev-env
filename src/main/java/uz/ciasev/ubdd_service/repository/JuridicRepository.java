package uz.ciasev.ubdd_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;

import java.util.Optional;

public interface JuridicRepository extends JpaRepository<Juridic, Long> {

    @Query("SELECT j " +
            " FROM Juridic j " +
            "WHERE j.id = :id")
    Optional<Juridic> findDetailById(@Param("id") Long id);
}
