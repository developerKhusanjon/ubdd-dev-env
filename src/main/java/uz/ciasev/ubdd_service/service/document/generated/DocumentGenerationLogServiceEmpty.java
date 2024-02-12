package uz.ciasev.ubdd_service.service.document.generated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.document.DocumentGenerationLog;

@Service
@RequiredArgsConstructor
public class DocumentGenerationLogServiceEmpty implements DocumentGenerationLogService {

    @Override
    public DocumentGenerationLog createAndUpdateLog(AdmEntity admEntity, Object jsonModel, DocumentAutoGenerationEventType eventType) {
        return null;
    }

    @Override
    public DocumentGenerationLog updateLog(DocumentGenerationLog documentGenerationLog,
                                           Object jsonModel) {
        return null;
    }

    @Override
    public DocumentGenerationLog createLog(AdmEntity admEntity,
                                           DocumentAutoGenerationEventType eventType) {
        return null;
    }
}
