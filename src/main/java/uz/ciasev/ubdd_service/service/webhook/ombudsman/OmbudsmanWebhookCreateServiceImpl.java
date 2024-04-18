package uz.ciasev.ubdd_service.service.webhook.ombudsman;

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
import uz.ciasev.ubdd_service.entity.webhook.ombudsman.OmbudsmanWebhookEvent;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.repository.webhook.OmbudsmanWebhookEventRepository;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.webhook.ombudsman.dto.OmbudsmanWebhookEventProtocolDecisionDataDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class OmbudsmanWebhookCreateServiceImpl implements OmbudsmanWebhookCreateService {
    private final OmbudsmanWebhookEventRepository repository;
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
        Stream<OmbudsmanWebhookEventProtocolDecisionDataDTO> dtoStream = repository.getProjectionsByProtocolId(protocolIds).stream()
                .map(OmbudsmanWebhookEventProtocolDecisionDataDTO::new);

        constructEventsFromDtosAndSaveThem(dtoStream, type);
    }

    private <T> void constructEventsFromDtosAndSaveThem(Stream<T> dtoStream, AdmEventType type) {
        List<OmbudsmanWebhookEvent> events = dtoStream.map(dto -> objectMapper.convertValue(dto, JsonNode.class))
                .map(jsonNode -> new OmbudsmanWebhookEvent(jsonNode,type))
                .collect(Collectors.toList());

        repository.saveAll(events);
    }
}

