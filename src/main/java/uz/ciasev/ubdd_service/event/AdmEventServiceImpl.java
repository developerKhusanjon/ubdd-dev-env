package uz.ciasev.ubdd_service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.event.subscribers.AdmEventSubscriber;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdmEventServiceImpl implements AdmEventService {

    private final List<AdmEventSubscriber> subscribers;

    @Override
    public void fireEvent(AdmEventType type, Object data) {
        new Thread(() -> {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            subscribers.parallelStream().filter(s -> s.canAccept(type, data)).forEach(s -> s.accept(data));
        }).start();
    }
}
