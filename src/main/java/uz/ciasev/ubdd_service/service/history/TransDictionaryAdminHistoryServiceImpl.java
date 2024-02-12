package uz.ciasev.ubdd_service.service.history;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.history.TransDictAdminHistoricAction;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;
import uz.ciasev.ubdd_service.utils.ReflectionUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransDictionaryAdminHistoryServiceImpl implements TransDictionaryAdminHistoryService {

    private final ObjectMapper objectMapper;
    private final HistoryService historyService;


    @Override
    public void register(AbstractTransEntity entity, TransDictAdminHistoricAction action) {
        Map<String, Object> detail = action.isHideDetail() ? ReflectionUtils.mapEntityToMapOfFields(entity, objectMapper) : null;

        historyService.registerTransDictionaryAdminAction(
                entity,
                action,
                detail
        );
    }
}
