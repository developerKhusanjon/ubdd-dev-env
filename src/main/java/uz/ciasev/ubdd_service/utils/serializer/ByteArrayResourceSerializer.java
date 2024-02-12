package uz.ciasev.ubdd_service.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

public class ByteArrayResourceSerializer extends StdSerializer<ByteArrayResource> {

    public ByteArrayResourceSerializer() {
        super(ByteArrayResource.class);
    }

    @Override
    public void serialize(ByteArrayResource value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        byte[] byteArray = value.getByteArray();
        gen.writeBinary(byteArray);
    }
}