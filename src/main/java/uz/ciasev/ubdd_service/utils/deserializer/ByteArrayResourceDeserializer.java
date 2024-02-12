package uz.ciasev.ubdd_service.utils.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

public class ByteArrayResourceDeserializer extends JsonDeserializer<ByteArrayResource> {

    @Override
    public ByteArrayResource deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        byte[] byteArray = p.getBinaryValue();
        return new ByteArrayResource(byteArray);
    }
}