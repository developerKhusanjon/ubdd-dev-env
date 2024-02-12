package uz.ciasev.ubdd_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.report.ReportTemplate;
import uz.ciasev.ubdd_service.service.report.ReportAlias;

import java.util.Optional;

public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, Long> {

    Optional<ReportTemplate> findByAlias(ReportAlias reportAlias);

}
