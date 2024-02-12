package uz.ciasev.ubdd_service.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;

import java.io.IOException;


public class MibResponseCodeSerializer extends StdSerializer<MibSendStatus> {

    public MibResponseCodeSerializer() {
        super(MibSendStatus.class);
    }

    @Override
    public void serialize(MibSendStatus value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.getCode());
    }
}