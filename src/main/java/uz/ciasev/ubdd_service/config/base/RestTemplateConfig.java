package uz.ciasev.ubdd_service.config.base;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RestTemplateConfig {

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    @Autowired
    public RestTemplateConfig(MappingJackson2HttpMessageConverter jackson2HttpMessageConverter) {
        this.jackson2HttpMessageConverter = jackson2HttpMessageConverter;
    }

    @Bean
    @Primary
    public RestTemplate defaultRestTemplate() {
        return createRestTemplate(100, 5_000);
    }

    @Bean(name = "f1RestTemplate")
    public RestTemplate f1RestTemplate() {
        return createRestTemplate(100, 5_000);
    }

    @Bean(name = "mehnatApiRestTemplate")
    public RestTemplate mehnatApiRestTemplate() {
        return createRestTemplate(100, 10_000);
    }

    @Bean(name = "manzilRestTemplate")
    public RestTemplate manzilRestTemplate() {
        return createRestTemplate(100, 5_000);
    }

    @Bean(name = "billingRestTemplate")
    public RestTemplate billingRestTemplate() {
        return createRestTemplate(100, 2_000, 1_000, 10_000);
    }

    @Bean(name = "pdfRestTemplate")
    public RestTemplate pdfRestTemplate() {
        return createRestTemplate(200, 60_000);
    }

    @Bean(name = "wantedServiceRestTemplate")
    public RestTemplate wantedServiceRestTemplate() {
        return createRestTemplate(100, 90_000);
    }

    @Bean(name = "texPassServiceRestTemplate")
    public RestTemplate texPassServiceRestTemplate() {
        return createRestTemplate(100, 30_000);
    }

    @Bean(name = "mibRestTemplate")
    public RestTemplate mibRestTemplate() {
        // proxy nginx timeout 65s
        return createRestTemplate(100, 60_000);
    }

    @Bean(name = "sverkaRestTemplate")
    public RestTemplate sverkaRestTemplate() {
        return createRestTemplate(300, 10_000, 10_000,60_000);
    }

    @Bean(name = "courtRestTemplate")
    // proxy nginx timeout 120s
    public RestTemplate courtRestTemplate() {
        return createRestTemplate(200, 130_000);
    }

    @Bean(name = "publicApiWebhookWorkerRestTemplate")
    public RestTemplate publicApiWebhookWorkerRestTemplate(@Value("${mvd-ciasev.public-api.webhook.worker-pool-size}") int poolSize) {
        return createRestTemplate(poolSize, 30_000);
    }

    @Bean(name = "autoconRestTemplate")
    public RestTemplate autoconRestTemplate() {
        return createRestTemplate(100, 60_000);
    }

    @Bean(name = "techpassRestTemplate")
    public RestTemplate techpassRestTemplate() {
        return createRestTemplate(200, 10_000);
    }

    @Bean(name = "customsRestTemplate")
    public RestTemplate customsRestTemplate() {
        return createRestTemplate(50, 10_000);
    }

    @Bean(name = "violationEventRestTemplate")
    public RestTemplate violationEventRestTemplate() {
        return createRestTemplate(50, 10_000);
    }

    private RestTemplate createRestTemplate(int maxConnTotal, int timeout) {
        return createRestTemplate(maxConnTotal, 2_000, 2_000, timeout);
    }


    private RestTemplate createRestTemplate(int maxConnTotal, int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        RequestConfig config = RequestConfig
                .custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .build();

        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(maxConnTotal);
        poolingConnectionManager.setDefaultMaxPerRoute(maxConnTotal);

        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .setConnectionManager(poolingConnectionManager)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
        factory.setReadTimeout(socketTimeout);
        factory.setConnectTimeout(connectTimeout);
        factory.setConnectionRequestTimeout(connectionRequestTimeout);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters()
                .stream()
                .filter(c -> !(c instanceof MappingJackson2HttpMessageConverter))
                .collect(Collectors.toList());
        messageConverters.add(jackson2HttpMessageConverter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
}
