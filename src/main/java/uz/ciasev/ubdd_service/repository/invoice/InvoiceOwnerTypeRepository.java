package uz.ciasev.ubdd_service.repository.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerType;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;

import java.util.Optional;

public interface InvoiceOwnerTypeRepository extends JpaRepository<InvoiceOwnerType, Long> {

    Optional<InvoiceOwnerType> findById(Long id);

    Optional<InvoiceOwnerType> findByAlias(InvoiceOwnerTypeAlias alias);
}
