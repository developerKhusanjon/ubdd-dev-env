package uz.ciasev.ubdd_service.service.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.RepeatabilityResponseDTO;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.Repeatability;
import uz.ciasev.ubdd_service.entity.protocol.RepeatabilityPdfProjection;
import uz.ciasev.ubdd_service.entity.protocol.ViolationListView;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.FiltersNotSetException;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.protocol.RepeatabilityRepository;
import uz.ciasev.ubdd_service.repository.protocol.ViolationListViewRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepeatabilityServiceImpl implements RepeatabilityService {

    private final RepeatabilityRepository repeatabilityRepository;
    private final ViolationListViewRepository violationListViewRepository;

    @Override
    @Transactional
    public List<Repeatability> replace(User user, Protocol protocol, List<Long> protocolsId) {
        repeatabilityRepository.deleteByProtocol(protocol);

        return create(protocolsId, violation -> new Repeatability(user, protocol, violation));
    }

    @Override
    @Transactional
    public List<Repeatability> create(User user, Decision decision, List<Long> protocolsId) {
        return create(protocolsId, violation -> new Repeatability(user, decision, violation));
    }

    @Override
    public List<RepeatabilityPdfProjection> findRepeatabilityForProtocolPdf(Protocol protocol) {
        return repeatabilityRepository.findRepeatabilityArticlePartsByProtocol(protocol);
    }

    @Override
    public List<RepeatabilityPdfProjection> findRepeatabilityForDecisionPdf(Decision decision, Protocol mainProtocol) {
        Set<Long> presentedFromProtocols = new HashSet<>();

        return repeatabilityRepository
                .findRepeatabilityArticlePartsByDecisionId(decision, mainProtocol)
                .stream().filter(r -> presentedFromProtocols.add(r.getFromProtocolId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RepeatabilityResponseDTO> findRepeatability(Long protocolId, Long decisionId) {
        if (protocolId == null && decisionId == null) throw FiltersNotSetException.onOfRequired("protocolId", "decisionId");
        if (protocolId != null && decisionId != null)  throw new ValidationException(ErrorCode.FILTERS_PRESENT_TOGETHER);

        return (
                protocolId != null
                        ? repeatabilityRepository.findAllByProtocolId(protocolId)
                        : repeatabilityRepository.findAllByDecisionId(decisionId)
        )
                .stream()
                .map(RepeatabilityResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getProtocolRepeatabilityProtocolId(Long protocolId) {
        return repeatabilityRepository
                .getRepeatabilityProtocolsIdByProtocol(protocolId);
    }

    @Override
    public boolean hasProtocolRepeatability(Protocol protocol) {
        return repeatabilityRepository.existsByProtocolId(protocol.getId());
    }

    private List<Repeatability> create(List<Long> protocolsId, Function<ViolationListView, Repeatability> builder) {
        if (protocolsId == null || protocolsId.isEmpty())
            return List.of();

        List<Repeatability> protocolRepeatabilityList = violationListViewRepository
                .findAllByIdIn(protocolsId)
                .stream()
                .map(builder::apply)
                .collect(Collectors.toList());

        return repeatabilityRepository.saveAll(protocolRepeatabilityList);
    }

}
