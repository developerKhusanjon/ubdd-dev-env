package uz.ciasev.ubdd_service.service.court.files;

import uz.ciasev.ubdd_service.entity.court.CourtFile;

import java.util.List;
import java.util.Optional;

public interface CourtFileService {

    CourtFile create(Long caseId, Long climeId, Long fileId, String fileUri);

    List<CourtFile> getByCaseId(Long caseId);

    Optional<CourtFile> findLastByCaseIdAndClimeId(Long caseId, Long climeId);
}
