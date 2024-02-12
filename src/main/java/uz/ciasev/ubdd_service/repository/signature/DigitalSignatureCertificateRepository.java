package uz.ciasev.ubdd_service.repository.signature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate;
import uz.ciasev.ubdd_service.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DigitalSignatureCertificateRepository extends JpaRepository<DigitalSignatureCertificate, Long> {

    List<DigitalSignatureCertificate> findByUser(User user);

    boolean existsByUser(User user);

    @Query("SELECT c " +
            "FROM DigitalSignatureCertificate c " +
            "WHERE c.user = :user " +
            "AND c.isActive = TRUE ")
    Optional<DigitalSignatureCertificate> findActiveByUser(User user);

    @Query("SELECT COUNT(c.id) != 0 " +
            "FROM DigitalSignatureCertificate c " +
            "WHERE c.user.id = :userId " +
            "AND c.isActive = TRUE ")
    boolean existsActiveByUserId(Long userId);

    @Query("SELECT COUNT(c.id) != 0 " +
            "FROM DigitalSignatureCertificate c " +
            "WHERE c.user.id = :userId " +
            "AND c.expiresOn > :atTime ")
    boolean existsAliveByUserId(Long userId, LocalDateTime atTime);

    @Query("SELECT COUNT(c.id) > 1 " +
            "FROM DigitalSignatureCertificate c " +
            "WHERE c.user.id = :userId " +
            "AND c.isActive = TRUE ")
    boolean existsMoreThenOneByUserId(Long userId);
}
