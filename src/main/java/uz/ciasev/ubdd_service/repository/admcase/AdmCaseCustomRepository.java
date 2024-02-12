package uz.ciasev.ubdd_service.repository.admcase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;

import java.util.List;

public interface AdmCaseCustomRepository {

    Page<Long> findAllId(Specification<AdmCase> specification, Pageable pageable);

    Page<Long> getPagination(Specification<AdmCase> specification, Pageable pageable);

    List<Long> findAllId(Specification<AdmCase> specification);

    List<Long> findAllIdList(Specification<AdmCase> specification, Pageable pageable);
}
