package uz.ciasev.ubdd_service.service.wanted;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.wanted.WantedDTO;
import uz.ciasev.ubdd_service.entity.wanted.WantedProtocol;
import uz.ciasev.ubdd_service.entity.wanted.WantedProtocolClosed;
import uz.ciasev.ubdd_service.repository.wanted.WantedProtocolClosedRepository;
import uz.ciasev.ubdd_service.repository.wanted.WantedProtocolRepository;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.service.user.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WantedProtocolServiceImpl implements WantedProtocolService {

    private final WantedProtocolRepository wantedProtocolRepository;
    private final WantedProtocolClosedRepository wantedProtocolClosedRepository;
    private final AdmEventService eventService;
    private final UserService userService;

    @Override
    @Transactional
    public void registerWanted(Protocol protocol, List<WantedDTO> dtoList) {
        String inspectorPinpp = null;
        try {
            inspectorPinpp = userService.findById(protocol.getUserId()).getPerson().getPinpp();
        } catch (EntityByIdNotFound exception) {
            log.error("Protocol with id: {}, Inspector pinpp not found", exception, protocol.getId());
        }
        String pinpp = inspectorPinpp;
        List<WantedProtocol> wanteds = dtoList.stream()
                .filter(d -> !wantedProtocolRepository.existsByProtocolIdAndExtId(protocol.getId(), d.getId()))
                .map(d -> new WantedProtocol(protocol, d, pinpp))
                .map(wantedProtocolRepository::saveAndFlush)
                .collect(Collectors.toList());

        eventService.fireEvent(AdmEventType.WANTED_PERSON_DETECTED, wanteds);
    }

    @Override
    public WantedProtocol findById(Long id) {
        return wantedProtocolRepository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(WantedProtocol.class, id));
    }

    @Override
    public List<WantedProtocol> findAllByProtocolId(Long id) {

        return wantedProtocolRepository.findAllByProtocolId(id);
    }

    @Override
    public List<WantedDTO> findAllDTOByProtocolId(Long id) {

        return wantedProtocolRepository.findAllByProtocolId(id)
                .stream()
                .map(WantedProtocol::buildDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void closeWanted(Long id) {

        WantedProtocol wantedProtocol = findById(id);

        wantedProtocolClosedRepository.save(new WantedProtocolClosed(wantedProtocol));
    }
}
