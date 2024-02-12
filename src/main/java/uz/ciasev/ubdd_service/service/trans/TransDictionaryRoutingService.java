package uz.ciasev.ubdd_service.service.trans;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TransDictionaryRoutingService {

    <T> Page<Object> findAll(String path, Map<String, String> filters, Pageable pageable);

    <T, D> List<Object> create(String path, List<JsonNode> body);

    <T> Object getById(String path, Long id);

//    <T, U> Object updateById(String path, Long id, JsonNode body);

    <T> void delete(String path, Long id);
}
