package uz.ciasev.ubdd_service.service.document.generated;

import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.document.DocumentGenerationLog;

public interface DocumentGenerationLogService {

    DocumentGenerationLog createLog(AdmEntity admEntity,
                                    DocumentAutoGenerationEventType eventType);

    DocumentGenerationLog updateLog(DocumentGenerationLog documentGenerationLog,
                                    Object jsonModel);

    DocumentGenerationLog createAndUpdateLog(AdmEntity admEntity,
                                             Object jsonModel,
                                             DocumentAutoGenerationEventType eventType);
}
