package uz.ciasev.ubdd_service.service.document.generated;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.document.DocumentGenerationLog;
import uz.ciasev.ubdd_service.repository.document.DocumentGenerationLogRepository;
import uz.ciasev.ubdd_service.service.user.SystemUserService;


// 2022-03-31 Саша сказал не логировать, слишкм много места занимает
//@Service
@RequiredArgsConstructor
public class DocumentGenerationLogServiceImpl implements DocumentGenerationLogService {

    private final DocumentGenerationLogRepository repository;
    private final ObjectMapper mapper;
    private final SystemUserService systemUserService;

    @Override
    public DocumentGenerationLog createAndUpdateLog(AdmEntity admEntity, Object jsonModel, DocumentAutoGenerationEventType eventType) {

        DocumentGenerationLog documentGenerationLog = new DocumentGenerationLog();
        documentGenerationLog.setEntityId(admEntity.getId());
        documentGenerationLog.setEntityType(admEntity.getEntityNameAlias().toString());
        documentGenerationLog.setEventType(eventType.toString());
        documentGenerationLog.setUser(systemUserService.getCurrentUser());
        documentGenerationLog.setJsonContent(mapper.convertValue(jsonModel, JsonNode.class));
        return repository.save(documentGenerationLog);
    }

    @Override
    public DocumentGenerationLog updateLog(DocumentGenerationLog documentGenerationLog,
                                           Object jsonModel) {

        documentGenerationLog.setJsonContent(mapper.convertValue(jsonModel, JsonNode.class));
        return repository.save(documentGenerationLog);
    }

    @Override
    public DocumentGenerationLog createLog(AdmEntity admEntity,
                                           DocumentAutoGenerationEventType eventType) {

        DocumentGenerationLog documentGenerationLog = new DocumentGenerationLog();
        documentGenerationLog.setEntityId(admEntity.getId());
        documentGenerationLog.setEntityType(admEntity.getEntityNameAlias().toString());
        documentGenerationLog.setEventType(eventType.toString());
        documentGenerationLog.setUser(systemUserService.getCurrentUser());
        return repository.save(documentGenerationLog);
    }
}
