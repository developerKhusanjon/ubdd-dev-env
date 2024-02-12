package uz.ciasev.ubdd_service.service.ubdd_data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataBind;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataToProtocolBindDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataToProtocolBindInternalDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddDataToProtocolBindRepository;
import uz.ciasev.ubdd_service.service.history.HistoryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbddDataToProtocolBindServiceImpl implements UbddDataToProtocolBindService {

    private final UbddDataToProtocolBindRepository repository;
    private final HistoryService historyService;

    @Override
    @Transactional
    public void save(User user, UbddDataToProtocolBindDTO dto) {

        save(user, dto.getProtocolId(), dto);
    }

    @Override
    @Transactional
    public void save(User user, Long protocolId, UbddDataBind dto) {

        if (dto == null) return;

        UbddDataToProtocolBind bind = repository.findByProtocolId(protocolId).orElse(new UbddDataToProtocolBind(protocolId));

        historyService.registerUbddDataBind(user, protocolId, bind, dto);

        bind.apply(dto);

        bind.setUserId(user.getId());

        repository.save(bind);
    }

    @Override
    public Optional<UbddDataToProtocolBind> findByProtocolId(Long protocolId) {

        return repository.findByProtocolId(protocolId);
    }

    @Override
    public Optional<UbddDataToProtocolBindDTO> findDTOByProtocolId(Long protocolId) {

        return findByProtocolId(protocolId)
                .map(UbddDataToProtocolBindDTO::new);
    }

    @Override
    public Optional<UbddDataToProtocolBindInternalDTO> findInternalDTOByProtocolId(Long protocolId) {

        return findByProtocolId(protocolId)
                .map(UbddDataToProtocolBindInternalDTO::new);
    }
}
