package uz.ciasev.ubdd_service.service.history;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.history.DictAdminHistoricAction;
import uz.ciasev.ubdd_service.utils.ReflectionUtils;

import javax.annotation.Nullable;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DictionaryAdminHistoryServiceImpl implements DictionaryAdminHistoryService {

    private final ObjectMapper objectMapper;
    private final HistoryService historyService;

    @Override
    public void register(AbstractDict entity, DictAdminHistoricAction action) {
        register(entity, action, null);
    }

    @Override
    public void register(AbstractDict entity, DictAdminHistoricAction action, Map<String, Object> extraDetail) {
        Map<String, Object> entityDetail = action.isHeedDetail() ? ReflectionUtils.mapEntityToMapOfFields(entity, objectMapper) : null;
        Map<String, Object> detail = addExtra(entityDetail, extraDetail);

        historyService.registerDictionaryAdminAction(
                entity,
                action,
                detail
        );
    }

    private Map<String, Object> addExtra(@Nullable Map<String, Object> entityDetail, @Nullable Map<String, Object> extraDetail) {
        if (extraDetail == null) {
            return entityDetail;
        }

        if (entityDetail == null) {
            return Map.of("extra", extraDetail);
        }

        entityDetail.put("extra", extraDetail);
        return entityDetail;
    }
}