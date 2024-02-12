package uz.ciasev.ubdd_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.report.ReportSql;

import java.util.Optional;

public interface ReportSqlRepository extends JpaRepository<ReportSql, Long> {

    Optional<ReportSql> findByAlias(String reportAlias);

}
