package uz.ciasev.ubdd_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {


    @Query(value = "select * from core_v0.person where is_real_pinpp = true and length(pinpp) != 14", nativeQuery = true)
    List<Person> findForFix();

    Optional<Person> findByPinppAndIsRealPinpp(@Param("pinpp") String pinpp,
                                               @Param("is_real_pinpp") Boolean isRealPinpp);

    @Query("SELECT p " +
            " FROM Person p " +
            "WHERE p.id = :id")
    Optional<Person> findDetailById(@Param("id") Long id);

    @Query("SELECT p " +
            " FROM ViolatorDetail vd " +
            " JOIN Violator v ON vd.violatorId = v.id " +
            " JOIN Person p ON v.personId = p.id " +
            "WHERE vd.id = :id")
    Optional<Person> findByViolatorDetailId(@Param("id") Long id);


    Optional<Person> findByPinpp(@Param("pinpp") String pinpp);

    @Query("SELECT p " +
            " FROM Violator v " +
            " JOIN Person p ON v.personId = p.id " +
            "WHERE v.admCaseId = :caseId")
    List<Person> findViolatorsPinppByAdmCaseId(@Param("caseId") Long caseId);
}