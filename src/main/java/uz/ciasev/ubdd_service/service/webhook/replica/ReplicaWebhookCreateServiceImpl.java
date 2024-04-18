package uz.ciasev.ubdd_service.service.webhook.replica;

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
import uz.ciasev.ubdd_service.entity.webhook.replica.ReplicaWebhookEvent;
import uz.ciasev.ubdd_service.repository.webhook.ReplicaWebhookEventRepository;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.webhook.replica.dto.ReplicaWebhookEventProtocolDecisionDataDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplicaWebhookCreateServiceImpl implements ReplicaWebhookCreateService {
    private final ReplicaWebhookEventRepository repository;
    private final ObjectMapper objectMapper;
    private final ProtocolService protocolService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createWebhooks(AdmCase admCase) {
        List<Long> protocolIds = protocolService.findAllProtocolsIdInAdmCase(admCase.getId());

        constructProtocolDecisionWebhookEvent(protocolIds);
    }

    @Override
    @Transactional
    public void createWebhooksByViolatorId(Long violatorId) {
        List<Long> protocolIds = protocolService.findAllIdByViolatorId(violatorId);

        constructProtocolDecisionWebhookEvent(protocolIds);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createWebhooks(Decision decision) {
        createWebhooksByViolatorId(decision.getViolatorId());
    }

    @Override
    public void createWebhook(Protocol protocol) {
        constructProtocolDecisionWebhookEvent(List.of(protocol.getId()));
    }

    private void constructProtocolDecisionWebhookEvent(List<Long> protocolIds) {
        Stream<ReplicaWebhookEventProtocolDecisionDataDTO> dtoStream = repository.getProjectionsByProtocolId(protocolIds).stream()
                .map(ReplicaWebhookEventProtocolDecisionDataDTO::new);

        constructEventsFromDtosAndSaveThem(dtoStream);
    }

    private <T> void constructEventsFromDtosAndSaveThem(Stream<T> dtoStream) {
        List<ReplicaWebhookEvent> events = dtoStream.map(dto -> objectMapper.convertValue(dto, JsonNode.class))
                .map(ReplicaWebhookEvent::new)
                .collect(Collectors.toList());

        repository.saveAll(events);
    }
}

