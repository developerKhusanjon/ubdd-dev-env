package uz.ciasev.ubdd_service.service.main;

import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;
import java.util.Optional;

public interface ArchiveService {

    void returnFromArchive(Violator violator);

    void sendToArchive(Violator violator);

    void sendToArchiveAll(List<Long> violators);

    List<Long> findNextNForSendToArchive(int n);

    Optional<Violator> findNextForSendToArchive();
}