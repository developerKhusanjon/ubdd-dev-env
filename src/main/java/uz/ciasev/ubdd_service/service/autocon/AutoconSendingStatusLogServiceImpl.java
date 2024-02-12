package uz.ciasev.ubdd_service.service.autocon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSendingStatusLog;
import uz.ciasev.ubdd_service.repository.autocon.AutoconSendingStatusLogRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutoconSendingStatusLogServiceImpl implements AutoconSendingStatusLogService {

    private final AutoconSendingStatusLogRepository repository;

    @Override
    public void save(AutoconSending sending) {

        repository.save(AutoconSendingStatusLog.of(sending));
    }

    @Override
    public void saveAll(List<AutoconSending> sendingList) {

        repository.saveAll(
                sendingList.stream().map(AutoconSendingStatusLog::of).collect(Collectors.toList())
        );
    }
}
