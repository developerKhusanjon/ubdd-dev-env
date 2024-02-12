package uz.ciasev.ubdd_service.service.admcase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseListProjection;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.Map;

public interface AdmCaseSearchService {

    Page<AdmCaseListProjection> findConsideredAdmCasesByUserAndFilter(User user, Map<String, String> filters, Pageable pageable);

    List<AdmCaseListProjection> findConsideredAdmCasesByUserAndFilterList(User user, Map<String, String> filters, Pageable pageable);

    Page<Long> findConsideredAdmCasesByUserAndFilterPagination(User user, Map<String, String> filters, Pageable pageable);

    Page<AdmCaseListProjection> findConsideredAdmCasesByOrgan(User user, Map<String, String> filters, Pageable pageable);

    List<AdmCaseListProjection> findConsideredAdmCasesByOrganList(User user, Map<String, String> filters, Pageable pageable);

    Page<Long> findConsideredAdmCasesByOrganPagination(User user, Map<String, String> filters, Pageable pageable);
}
