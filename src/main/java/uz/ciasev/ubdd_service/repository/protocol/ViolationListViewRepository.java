package uz.ciasev.ubdd_service.repository.protocol;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.protocol.ViolationListView;

import java.util.List;

public interface ViolationListViewRepository extends JpaRepository<ViolationListView, Long> {

    Page<ViolationListView> findAllByIdIn(Iterable<Long> ids, Pageable pageable);

    List<ViolationListView> findAllByIdIn(Iterable<Long> ids, Sort sort);

    List<ViolationListView> findAllByIdIn(Iterable<Long> ids);
}
