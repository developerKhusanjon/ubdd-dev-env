package uz.ciasev.ubdd_service.service.autocon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;
import uz.ciasev.ubdd_service.entity.dict.autocon.AutoconSendingStatusAlias;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending_;
import uz.ciasev.ubdd_service.repository.autocon.AutoconSendingRepository;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoconSendingOrderServiceImpl implements AutoconSendingOrderService {

    private final AutoconSendingRepository repository;
    private final AutoconSendingHandleService handleService;

    @Override
    public List<Long> getNextForOpen(Long n) {
        return repository.findNextToSend(
                AutoconSendingStatusAlias.AWAIT_OPEN,
                PageUtils.top(n.intValue(), AutoconSending_.editedTime) // те, каторые давно не обрабатывались - вперед очереди
        );
    }

    @Override
    public List<Long> getNextForClose(Long n) {
        return repository.findNextToSend(
                AutoconSendingStatusAlias.AWAIT_CLOSE,
                PageUtils.top(n.intValue(), AutoconSending_.editedTime) // те, каторые давно не обрабатывались - вперед очереди
        );
    }

    @Override
    public void handle(Long sendingId) {
        AutoconSending sending = repository.findById(sendingId).get();

        try {
            handleService.handle(sending);
            sending.setIsLastProcessError(false);
        } catch (Exception e) {
            sending.setIsLastProcessError(true);
            sending.setLastErrorText(e.getMessage());

        }
        sending.setLastProcessTime(LocalDateTime.now());
        sending.incrementProcessCount();

        repository.save(sending);
    }
}
