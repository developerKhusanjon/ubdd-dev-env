package uz.ciasev.ubdd_service.utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.exception.DictionaryDeserializationError;
import uz.ciasev.ubdd_service.exception.dict.AliasDoesNotExistException;

import java.io.IOException;

public abstract class AbstractAliasDeserializer<A extends Enum<A>> extends StdDeserializer<A> {

    private final Class<A> clazz;

    @Autowired
    protected AbstractAliasDeserializer(Class<A> vc) {
        super(vc);
        this.clazz = vc;
    }

    @Override
    public A deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        String value = ((JsonNode) jp
                .getCodec()
                .readTree(jp)).asText();

        try {
            return A.valueOf(clazz, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AliasDoesNotExistException(clazz, value);
        } catch (Exception e) {
            throw new DictionaryDeserializationError(handledType(), value, e);
        }
    }
}