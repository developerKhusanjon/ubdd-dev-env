package uz.ciasev.ubdd_service.service.webhook.sit_center;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.wanted.WantedProtocol;
import uz.ciasev.ubdd_service.entity.webhook.sit_center.SitCenterGenerateWebhookEvent;
import uz.ciasev.ubdd_service.entity.webhook.sit_center.SitCenterWebhookEvent;
import uz.ciasev.ubdd_service.repository.webhook.SitCenterGenerateWebhookEventRepository;
import uz.ciasev.ubdd_service.repository.webhook.SitCenterWebhookEventRepository;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.webhook.sit_center.dto.SitCenterWebhookEventProtocolDecisionDataDTO;
import uz.ciasev.ubdd_service.service.webhook.sit_center.dto.SitCenterWebhookEventWantedProtocolDataDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SitCenterWebhookCreateServiceImpl implements SitCenterWebhookCreateService {
    private final SitCenterWebhookEventRepository repository;
    private final SitCenterGenerateWebhookEventRepository generateWebhookEventRepository;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createWebhook(Protocol protocol) {
        constructProtocolDecisionWebhookEvent(List.of(protocol.getId()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createWebhooks(List<WantedProtocol> wantedProtocol) {
        Stream<SitCenterWebhookEventWantedProtocolDataDTO> dtoStream = wantedProtocol.stream()
                .map(SitCenterWebhookEventWantedProtocolDataDTO::new);

        constructEventsFromDtosAndSaveThem(dtoStream, SitCenterWebhookEventDataType.WANTED_PERSON);

    }

    @Override
    public void generateProtocolDecisionWebhookEvent(String fromdate) {
        LocalDateTime dateFrom = LocalDate.parse(fromdate).atStartOfDay();
        protocolService.findAllIdByFromDate(dateFrom).forEach(protocolId -> generateWebhookEventRepository.getProjectionsByProtocol(protocolId).stream()
                .map(SitCenterWebhookEventProtocolDecisionDataDTO::new).map(dto -> objectMapper.convertValue(dto, JsonNode.class))
                .map(jsonNode -> new SitCenterGenerateWebhookEvent(jsonNode, SitCenterWebhookEventDataType.GENERATE_PROTOCOL))
                .forEach(this.generateWebhookEventRepository::save));
    }

    private void constructProtocolDecisionWebhookEvent(List<Long> protocolIds) {
        Stream<SitCenterWebhookEventProtocolDecisionDataDTO> dtoStream = repository.getProjectionsByProtocolIds(protocolIds).stream()
                .map(SitCenterWebhookEventProtocolDecisionDataDTO::new);

        constructEventsFromDtosAndSaveThem(dtoStream, SitCenterWebhookEventDataType.PROTOCOL);
    }

    private <T> void constructEventsFromDtosAndSaveThem(Stream<T> dtoStream, SitCenterWebhookEventDataType dataType) {
        List<SitCenterWebhookEvent> events = dtoStream.map(dto -> objectMapper.convertValue(dto, JsonNode.class))
                .map(jsonNode -> new SitCenterWebhookEvent(jsonNode, dataType))
                .collect(Collectors.toList());

        repository.saveAll(events);
    }
}
