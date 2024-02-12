package uz.ciasev.ubdd_service.service.autocon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;
import uz.ciasev.ubdd_service.entity.dict.autocon.AutoconSendingStatusAlias;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.autocon.AutoconSendingRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutoconSendingServiceImpl implements AutoconSendingService {

    private final AutoconSendingRepository repository;
    private final AutoconSendingStatusLogService statusLogService;

    @Override
    @Transactional
    public void create(Punishment punishment) {

        save(AutoconSending.of(punishment));
    }

    @Transactional
    @Override
    public void prepareClose(AutoconSending sending) {

        if (AutoconSendingStatusAlias.AWAIT_OPEN.equals(sending.getStatus())) {
            sending.setStatus(AutoconSendingStatusAlias.OPEN_CANCELED);
        } else if (AutoconSendingStatusAlias.OPENED.equals(sending.getStatus())) {
            sending.setStatus(AutoconSendingStatusAlias.AWAIT_CLOSE);
        } else {
            return;
        }

        save(sending);
    }

    @Transactional
    @Override
    public void closed(AutoconSending sending) {
        sending.setStatus(AutoconSendingStatusAlias.CLOSED);
        sending.setClosedTime(LocalDateTime.now());
        save(sending);
    }

    @Transactional
    @Override
    public void opened(AutoconSending sending) {
        sending.setStatus(AutoconSendingStatusAlias.OPENED);
        sending.setOpenedTime(LocalDateTime.now());
        save(sending);
    }

    @Override
    public AutoconSending getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(AutoconSending.class, id));
    }

    @Override
    public Optional<AutoconSending> findByPunishmentId(Long punishmentId) {
        return repository.findByPunishmentId(punishmentId);
    }

    private void save(AutoconSending sending) {
        repository.save(sending);
        statusLogService.save(sending);
    }
}
