package uz.ciasev.ubdd_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.AddressReferenceBook;

import java.util.Optional;

public interface AddressReferenceBookRepository extends JpaRepository<AddressReferenceBook, Long> {

    Optional<AddressReferenceBook> findByAlias(String alias);
}
