package uz.ciasev.ubdd_service.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uz.ciasev.ubdd_service.entity.dict.ubdd.RelationDegree;

import java.io.IOException;
import java.util.Optional;

public class RelationDegreeSerializer extends StdSerializer<RelationDegree> {

    public RelationDegreeSerializer() {

        super(RelationDegree.class);
    }

    @Override
    public void serialize(RelationDegree value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        long result = Optional.ofNullable(value).map(RelationDegree::getId).orElse(999L);

        gen.writeNumber(result);
    }
}