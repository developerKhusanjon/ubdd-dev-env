package uz.ciasev.ubdd_service.repository.notification.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotificationFile;

import java.util.Optional;

public interface MailNotificationFileRepository extends JpaRepository<MailNotificationFile, Long>, JpaSpecificationExecutor<MailNotificationFile> {

    @Query("SELECT m FROM MailNotificationFile m WHERE m.mailNotificationId = :mailNotificationId")
    Optional<MailNotificationFile> findByMailNotificationId(Long mailNotificationId);
}
