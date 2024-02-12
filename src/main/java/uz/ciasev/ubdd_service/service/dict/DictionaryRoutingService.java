package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.response.dict.AliasDescriptionResponseDTO;

import java.util.List;
import java.util.Map;

public interface DictionaryRoutingService {

    <T> Page<Object> findAll(String path, Map<String, String> filters, Pageable pageable);

    <T, D> List<Object> create(String path, List<JsonNode> body);

    <T> Object getById(String path, Long id);

    <T, U> Object updateById(String path, Long id, JsonNode body);

    <T> void open(String path, Long id);

    <T> void close(String path, Long id);

    <T, A extends Enum<A>> List<AliasDescriptionResponseDTO> getAliasList(String path);
}
