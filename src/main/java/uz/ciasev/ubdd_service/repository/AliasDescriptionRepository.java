package uz.ciasev.ubdd_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.AliasDescription;

import java.util.List;

public interface AliasDescriptionRepository extends JpaRepository<AliasDescription, Long> {

    List<AliasDescription> findAllByAliasName(String aliasName);
}
