package uz.ciasev.ubdd_service.repository;

import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.FileFormat;
import uz.ciasev.ubdd_service.repository.dict.AbstractDictRepository;

import java.util.Optional;

public interface FileFormatRepository extends AbstractDictRepository<FileFormat> {

    Optional<FileFormat> findByExtension(@Param("extension") String extension);

    boolean existsByExtension(@Param("extension") String extension);
}
