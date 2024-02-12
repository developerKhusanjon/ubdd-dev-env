package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.settings.BankAccount;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByBillingId(Long billingId);
}
