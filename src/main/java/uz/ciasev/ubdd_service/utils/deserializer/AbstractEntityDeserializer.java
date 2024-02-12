package uz.ciasev.ubdd_service.utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.DictionaryDeserializationError;

import java.io.IOException;
import java.util.function.Function;

public abstract class AbstractEntityDeserializer<T> extends StdDeserializer<T> {

    private final Function<Long, T> entityGetter;

    @Autowired
    public AbstractEntityDeserializer(Class<T> vc, Function<Long, T> entityGetter) {
        super(vc);
        this.entityGetter = entityGetter;
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        String value = ((JsonNode) jp
                .getCodec()
                .readTree(jp)).asText();

        try {
            Long id = Long.valueOf(value);
            return entityGetter.apply(id);
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new DictionaryDeserializationError(handledType(), value, e);
        }
    }
}