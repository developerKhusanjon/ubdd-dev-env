package uz.ciasev.ubdd_service.service.webhook.egov;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.webhook.egov.*;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.repository.webhook.EgovWebhookEventRepository;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class EgovWebhookCreateServiceImpl implements EgovWebhookCreateService {
    private final EgovWebhookEventRepository repository;
    private final ObjectMapper objectMapper;
    private final ProtocolService protocolService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createWebhooks(AdmCase admCase, AdmEventType type) {
        List<Long> protocolIds = protocolService.findAllProtocolsIdInAdmCase(admCase.getId());

        constructProtocolDecisionWebhookEvent(protocolIds, type);
    }

    @Override
    @Transactional
    public void createWebhooksByViolatorId(Long violatorId, AdmEventType type) {
        List<Long> protocolIds = protocolService.findAllIdByViolatorId(violatorId);

        constructProtocolDecisionWebhookEvent(protocolIds, type);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createWebhooks(Decision decision, AdmEventType type) {
        createWebhooksByViolatorId(decision.getViolatorId(), type);
    }

    @Override
    public void createWebhook(Protocol protocol, AdmEventType type) {
        constructProtocolDecisionWebhookEvent(List.of(protocol.getId()), type);
    }


    private void constructProtocolDecisionWebhookEvent(List<Long> protocolIds, AdmEventType type) {

        List<EgovWebhookArticleProjection> articles = repository.getProtocolArticlesByProtocolIds(protocolIds);

        Stream<EgovWebhookEventDataDto> dtoStream = repository.getProjectionsByProtocolId(protocolIds).stream()
                .map(projection -> {
                    EgovWebhookEventDataDto dto = new EgovWebhookEventDataDto(projection);
                    articles.stream()
                            .filter(p -> p.getProtocolId().equals(projection.getProtocolId()))
                            .forEach(dto::addArticle);
                    return dto;
                });

        constructEventsFromDtosAndSaveThem(dtoStream, type);
    }

    private <T> void constructEventsFromDtosAndSaveThem(Stream<T> dtoStream, AdmEventType type) {
        List<EgovWebhookEvent> events = dtoStream.map(dto -> objectMapper.convertValue(dto, JsonNode.class))
                .map(jsonNode -> new EgovWebhookEvent(jsonNode, type))
                .collect(Collectors.toList());

        repository.saveAll(events);
    }
}

