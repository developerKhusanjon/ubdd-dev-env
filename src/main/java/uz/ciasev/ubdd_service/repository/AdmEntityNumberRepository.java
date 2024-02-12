package uz.ciasev.ubdd_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.AdmEntityNumber;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface AdmEntityNumberRepository extends JpaRepository<AdmEntityNumber, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT n " +
            " FROM AdmEntityNumber n " +
            "WHERE n.alias = :alias AND n.year = :year")
    Optional<AdmEntityNumber> findByAlias(@Param("alias") EntityNameAlias alias,
                                          @Param("year") long year);

    @Query(value = "SELECT nextval('{h-schema}invoice_number_seq')", nativeQuery = true)
    Long getNextInvoiceNumber();
}
