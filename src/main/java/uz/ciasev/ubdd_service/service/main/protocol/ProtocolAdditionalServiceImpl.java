package uz.ciasev.ubdd_service.service.main.protocol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolDataDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestAdditionalDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolStatisticData;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolDataService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolStatisticDataService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolUbddDataService;

import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.EDIT_PROTOCOL_ADDITIONAL;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProtocolAdditionalServiceImpl implements ProtocolAdditionalService {

    private final AdmCaseAccessService admCaseAccessService;
    private final AdmCaseService admCaseService;
    private final ProtocolStatisticDataService protocolStatisticDataService;
    private final ProtocolUbddDataService protocolUbddDataService;
    private final ProtocolRepository protocolRepository;


    @Override
    @Transactional
    public void createProtocolAdditional(User user, Protocol protocol, ProtocolRequestAdditionalDTO additional) {
        addStatistic(protocol, additional);
        addUbdd(protocol, additional);
        addTransport(protocol, additional);
    }


    @Override
    public Protocol updateProtocolAdditional(User user, Long protocolId, ProtocolRequestAdditionalDTO requestDTO) {

        AdmCase admCase = admCaseService.getByProtocolId(protocolId);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, EDIT_PROTOCOL_ADDITIONAL, admCase);

        Protocol protocol = protocolRepository.findById(protocolId).orElseThrow(() -> new EntityByIdNotFound(Protocol.class, protocolId));
        addUbdd(protocol, requestDTO);
        addTransport(protocol, requestDTO);
        addStatistic(protocol, requestDTO);

        return protocol;
    }

    private void addUbdd(Protocol protocol, ProtocolRequestAdditionalDTO additionalDTO) {
        Optional<ProtocolUbddData> storedData = protocolUbddDataService.findByProtocolId(protocol.getId());
        add(
                protocol,
                Optional.ofNullable(additionalDTO).map(ProtocolRequestAdditionalDTO::getUbdd).orElse(null),
                protocolUbddDataService,
                storedData.orElse(new ProtocolUbddData())
        );
    }

    private void addTransport(Protocol protocol, ProtocolRequestAdditionalDTO additionalDTO) {
        Optional<ProtocolUbddData> storedData = protocolUbddDataService.findByProtocolId(protocol.getId());
        add(
                protocol,
                Optional.ofNullable(additionalDTO).map(ProtocolRequestAdditionalDTO::getTransport).orElse(null),
                protocolUbddDataService,
                storedData.orElse(new ProtocolUbddData())
        );
    }

    private void addStatistic(Protocol protocol, ProtocolRequestAdditionalDTO additionalDTO) {
        Optional<ProtocolStatisticData> storedData = protocolStatisticDataService.findByProtocolId(protocol.getId());
        add(
                protocol,
                Optional.ofNullable(additionalDTO).map(ProtocolRequestAdditionalDTO::getStatistic).orElse(null),
                protocolStatisticDataService,
                storedData.orElse(new ProtocolStatisticData())
        );
    }

    private <T> void add(Protocol protocol, ProtocolDataDTO<T> dto, ProtocolDataService<T> service, T data) {
        if (dto == null) {
            return;
        }
        service.save(dto.apply(data, protocol));
    }
}