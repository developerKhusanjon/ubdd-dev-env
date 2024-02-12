package uz.ciasev.ubdd_service.repository.court;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.court.CourtRequestHash;

public interface CourtRequestHashRepository extends JpaRepository<CourtRequestHash, Long> {

    boolean existsByMethodAndHashCode(Long method, Integer hashCode);

}
