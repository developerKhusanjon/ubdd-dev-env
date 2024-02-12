package uz.ciasev.ubdd_service.utils.deserializer.dict;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.service.dict.mib.MibCaseStatusService;

import java.io.IOException;

public class MibCaseStatusCacheDeserializer extends StdDeserializer<MibCaseStatus> {

    private final MibCaseStatusService mibCaseStatusService;

    @Autowired
    public MibCaseStatusCacheDeserializer(MibCaseStatusService mibResultService) {
        super(MibCaseStatus.class);
        this.mibCaseStatusService = mibResultService;
    }

    @Override
    public MibCaseStatus deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
            String code = ((JsonNode) jp
            .getCodec()
            .readTree(jp)).asText();

            return mibCaseStatusService.findByCode(code);
    }
}