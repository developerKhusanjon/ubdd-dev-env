package uz.ciasev.ubdd_service.service.webhook.ibd;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookArticlesProjection;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookEvent;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookProtocolDecisionProjection;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.repository.webhook.IBDWebhookEventRepository;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.webhook.ibd.dto.IBDFileDTO;
import uz.ciasev.ubdd_service.service.webhook.ibd.dto.IBDWebhookEventProtocolDecisionDataDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class IBDWebhookCreateServiceImpl implements IBDWebhookCreateService {
    private final IBDWebhookEventRepository repository;
    private final ObjectMapper objectMapper;
    private final ProtocolService protocolService;
    private final String baseUrl;
    private final String host;

    @Autowired
    public IBDWebhookCreateServiceImpl(IBDWebhookEventRepository repository, ObjectMapper objectMapper, ProtocolService protocolService, @Value("${mvd-ciasev.url-v0}") String baseUrl, @Value("${mvd-ciasev.host}") String host) {
        this.repository = repository;
        this.objectMapper = objectMapper;
        this.protocolService = protocolService;
        this.baseUrl = baseUrl;
        this.host = host;
    }

    private final List<Long> CONSIDERING_ARTICLES =
            new ArrayList<>(List.of(1L,//"40-TUXMAT"
                    2L,//"41-XAKORAT KILISH"
                    6L,//"45-FUKAROLARNING TURAR JOYI DAXLSIZLIGINI BUZISH"
                    10L,//"47-BOLALARNI TARBIYALASH VA ULARGA TA’LIM BERISH BORASIDAGI MAJBURIYATLARNI BAJARMASLIK"
                    34L,//"52-ENGIL TAN JAROXATI ETKAZISH"
                    38L,//"56-GIYOXVANDLIK VOS.NI YOKI PSIXOTROP MODDA.NI OZ MIKDORDA GAYRIKONUNIY TARZDA TAYYORLASH, SAKLASH, TASHISH YOKI JUNATISH"
                    41L,//"58-TANOSIL KASALLIGI YOKI OITSGA UCHRAGAN SHAXSLARNING TEKSHIRISHDAN BUYIN TOVLASH"
                    46L,//"61-OZ MIKDORDA TALON-TAROJ KILISH"
                    104L,//"106-YOVVOYI NASHANI YUK KILIB YUBORISH CHORALARINI KURMAGANLIK"
                    139L,//"131-TANSPORT VOSITALARINI MAST XOLDA BOSHKARISH"
                    182L,//"165-PRIM1 SIFATSIZ YOKI KALBAKI DORI VOS.NI YOXUD TIBBIY BUYUMLARNI UTKAZISH MAKSADIDA ISHLAB CHIKARISH, TAYYORLASH, OLISH, SAKLASH, TASHISH YOKI UTKAZISH"
                    220L,//"183-MAYDA BEZORILIK"
                    221L,//"184-JAMOAT XAVFSIZLIGI VA JAMOAT TARTIBIGA TAXDID SOLADIGAN MATERIALLARNI TAYYORLASH YOKI TARKATISH MAKSADIDA SAKLASH"
                    222L,//"184 -PRIM1"
                    223L,//"184 -PRIM2"
                    224L,//"184 -PRIM3"
                    229L,//"187-JAMOAT JOYLARIDA ALKOGOL MAXSULOTINI ISTE’MOL K-SH"
                    230L,//"188-VOYAGA ETMAGAN SHAXSNI GAYRIIJTIMOIY XATTI-XARAKATLARGA JALB KILISH"
                    231L,//"188 -PRIM1"
                    234L,//"189-PORNOGRAFIK MAZMUNDAGI MATERIALLARNI TARKATISH"
                    236L,//"190-FOXISHALIK BILAN SHUGULLANISH"
                    237L,//"191-KIMOR UYNASH"
                    263L,//"201-YIGILISHLAR, MITINGLAR, KUCHA YURISHLARI YOKI NAMOYISHLAR UYUSHTIRISH, UTKAZISH TARTIBINI BUZISH"
                    264L,//"202-RUXSAT ETILMAGAN YIGILISHLAR, MITINGLAR, KUCHA YURISHLARI VA NAMOYISHLAR UTKAZISH UCHUN SHAROITLAR YARATISH"
                    265L,//"202 -PRIM1 G‘AYRIQONUNIY NODAVLAT NOTIJORAT TASHKILOTLARI, OQIMLAR, SEKTALARNING FAOLIYATIDA QATNASHISHGA UNDASH"
                    347L,//"240-DINIY TASHKILOTLAR TUGRISIDAGI KONUN XUJJATLARINI BUZISH"
                    348L //"241-DINIY TA’LIMOTDAN SABOK BERISH TARTIBINI BUZISH"
            ));

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createWebhooks(AdmCase admCase) {
        List<Long> protocolIds = protocolService.findAllProtocolsIdInAdmCase(admCase.getId());

        constructProtocolDecisionWebhookEvent(protocolIds);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createWebhooks(Long admCaseId, AdmEventType eventType) {
        List<Long> protocolIds = protocolService.findAllProtocolsIdInAdmCase(admCaseId);

        constructProtocolDecisionWebhookEvent(protocolIds, eventType);
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createWebhook(Protocol protocol, AdmEventType eventType) {
        constructProtocolDecisionWebhookEvent(List.of(protocol.getId()), eventType);
    }

    private void constructProtocolDecisionWebhookEvent(List<Long> protocolIds) {
        List<Long> ids = repository.getProtocolArticle(protocolIds).stream()
                .filter(pair -> CONSIDERING_ARTICLES.contains(pair[1]))
                .map(pair -> pair[0])
                .collect(Collectors.toList());
        if (ids.isEmpty()) return;

        List<IBDWebhookArticlesProjection> articles = repository.getProtocolArticlesByProtocolIds(ids);
        Stream<IBDWebhookEventProtocolDecisionDataDTO> dtoStream = repository.getProjectionsByProtocolId(ids).stream()
                .map(projection -> {
                    IBDWebhookEventProtocolDecisionDataDTO dto = new IBDWebhookEventProtocolDecisionDataDTO(projection);
                    articles.stream()
                            .filter(p -> p.getProtocolId().equals(projection.getProtocolId()))
                            .forEach(dto::addArticle);
                    repository.getAddressById(projection.getViolatorBirthAddressId()).ifPresent(dto::setViolatorBirthAddress);
                    repository.getAddressById(projection.getViolatorResidenceAddressId()).ifPresent(dto::setViolatorResidenceAddress);
                    repository.getAddressById(projection.getViolatorDocumentGivenAddressId()).ifPresent(dto::setDocumentGivenAddress);
                    return dto;
                });

        constructEventsFromDtosAndSaveThem(dtoStream, IBDWebhookEventDataType.PROTOCOL);
    }

    private void constructProtocolDecisionWebhookEvent(List<Long> protocolIds, AdmEventType eventType) {
        List<Long> ids = repository.getProtocolArticle(protocolIds).stream()
                .filter(pair -> CONSIDERING_ARTICLES.contains(pair[1]))
                .map(pair -> pair[0])
                .collect(Collectors.toList());
        if (ids.isEmpty()) return;

        List<IBDWebhookProtocolDecisionProjection> protocolProjection = repository.getProjectionsByProtocolId(ids);
        List<IBDWebhookArticlesProjection> articles = repository.getProtocolArticlesByProtocolIds(ids);
        Stream<IBDWebhookEventProtocolDecisionDataDTO> dtoStream = protocolProjection.stream()
                .map(projection -> {
                    IBDWebhookEventProtocolDecisionDataDTO dto = new IBDWebhookEventProtocolDecisionDataDTO(projection);
                    articles.stream()
                            .filter(p -> p.getProtocolId().equals(projection.getProtocolId()))
                            .forEach(dto::addArticle);
                    repository.getAddressById(projection.getViolatorBirthAddressId()).ifPresent(dto::setViolatorBirthAddress);
                    repository.getAddressById(projection.getViolatorResidenceAddressId()).ifPresent(dto::setViolatorResidenceAddress);
                    repository.getAddressById(projection.getViolatorDocumentGivenAddressId()).ifPresent(dto::setDocumentGivenAddress);
                    return dto;
                });
        constructDocumentAndPhotoFiles(protocolProjection, eventType);
        if (AdmEventType.COURT_RESOLUTION_FILE_CREATE.equals(eventType)) {
            return;
        }
        constructEventsFromDtosAndSaveThem(dtoStream, IBDWebhookEventDataType.PROTOCOL);
    }

    private void constructDocumentAndPhotoFiles(List<IBDWebhookProtocolDecisionProjection> protocols, AdmEventType eventType) {
        switch (eventType) {
            case PROTOCOL_CREATE:
                constructEventsFromDtosAndSaveThem(protocols.stream()
                        .map(p -> new IBDFileDTO<>(p.getViolatorPinpp(), createFileUrl("files", p.getViolatorPhotoUri()))), IBDWebhookEventDataType.PHOTO_URI);
                break;
            case ORGAN_RESOLUTION_CREATE:
            case COURT_RESOLUTION_FILE_CREATE:
                constructEventsFromDtosAndSaveThem(protocols.stream()
                        .map(p -> new IBDFileDTO<>(p.getAdmCaseId(), createFileUrl("pdf/decision", String.valueOf(p.getDecisionId())))), IBDWebhookEventDataType.PDF_URI);
                break;
        }

    }

    private <T> void constructEventsFromDtosAndSaveThem(Stream<T> dtoStream, IBDWebhookEventDataType dataType) {
        List<IBDWebhookEvent> events = dtoStream.map(dto -> objectMapper.convertValue(dto, JsonNode.class))
                .map(jsonNode -> new IBDWebhookEvent(jsonNode, dataType))
                .collect(Collectors.toList());

        new HashSet<>(events).forEach(this.repository::save);
//        repository.saveAll(new HashSet<>(events));
    }

    private String createFileUrl(String path, String subUri) {
        if (subUri == null) return null;
        path = path.replace("\\", "/");
        subUri = subUri.replace("\\", "/");
        return String.format("%s/%s/%s", baseUrl, path, subUri);
    }
}

