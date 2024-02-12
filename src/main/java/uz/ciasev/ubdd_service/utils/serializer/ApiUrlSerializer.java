package uz.ciasev.ubdd_service.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import uz.ciasev.ubdd_service.utils.types.ApiUrl;

import java.io.IOException;
import java.util.Optional;


public class ApiUrlSerializer extends StdSerializer<ApiUrl> {

    private final String baseUrl;

    @Autowired
    public ApiUrlSerializer(@Value("${mvd-ciasev.host}") String host, @Value("${mvd-ciasev.url-v0}") String versionUrl) {
        super(ApiUrl.class);
        this.baseUrl = host + versionUrl;
    }

    @Override
    public void serialize(ApiUrl value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String url = Optional.ofNullable(value)
                .map(ApiUrl::getUri)
                .filter(u -> !u.isBlank())
                .map(this::buildUrl)
                .orElse(null);
        gen.writeString(url);
    }

    private String buildUrl(String uri) {
        return baseUrl + uri;
    }
}