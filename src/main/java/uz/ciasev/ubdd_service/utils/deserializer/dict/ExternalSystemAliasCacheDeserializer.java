package uz.ciasev.ubdd_service.utils.deserializer.dict;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystem;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.DictionaryDeserializationError;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;

import java.io.IOException;

public class ExternalSystemAliasCacheDeserializer extends StdDeserializer<ExternalSystemAlias> {
    private final DictionaryService<ExternalSystem> abstractDictionaryService;

    @Autowired
    public ExternalSystemAliasCacheDeserializer(DictionaryService<ExternalSystem> abstractDictionaryService) {
        super(ExternalSystem.class);
        this.abstractDictionaryService = abstractDictionaryService;
    }

    @Override
    public ExternalSystemAlias deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        String value = ((JsonNode) jp
                .getCodec()
                .readTree(jp)).asText();

        try {
            Long id = Long.valueOf(value);
            return abstractDictionaryService.getById(id).getAlias();
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new DictionaryDeserializationError(handledType(), value, e);
        }
    }
}
