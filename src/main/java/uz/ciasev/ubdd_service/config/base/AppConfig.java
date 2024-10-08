package uz.ciasev.ubdd_service.config.base;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.github.cage.GCage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = {"uz.ciasev.ubdd_service.repository"})
@EntityScan(basePackages = "uz.ciasev.ubdd_service.entity", basePackageClasses = Jsr310JpaConverters.class)
@EnableJpaAuditing
@EnableTransactionManagement(order = 1000)
public class AppConfig {

    private final String dbSchema;
    private final int dbTransactionTimeout;

    private final String s3AccessKeyId;
    private final String s3SecretKey;
    private final String s3Endpoint;

    private final String encryptionKey;

    @Autowired
    public AppConfig(@Value("${spring.jpa.properties.hibernate.default_schema}") String dbSchema,
                     @Value("${mvd-ciasev.db.default-transaction-timeout-seconds}") int dbTransactionTimeout,
                     @Value("${mvd-ciasev.files.s3.access-key-id}") String s3AccessKeyId,
                     @Value("${mvd-ciasev.files.s3.secret-key}") String s3SecretKey,
                     @Value("${mvd-ciasev.files.s3.endpoint}") String s3Endpoint,
                     @Value("${mvd-ciasev.encryption.key}") String encryptionKey) {
        this.dbSchema = dbSchema;
        this.dbTransactionTimeout = dbTransactionTimeout;

        this.s3AccessKeyId = s3AccessKeyId;
        this.s3SecretKey = s3SecretKey;
        this.s3Endpoint = s3Endpoint;

        this.encryptionKey = encryptionKey;
    }

    @Bean
    public SecretKeySpec secretKey() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] keyBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, "AES");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("classpath:/messages/messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("uz.ciasev.ubdd_service")
                .properties(Map.of(
                        "hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName(),
                        "hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName()))
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setDefaultTimeout(dbTransactionTimeout);
        txManager.setEntityManagerFactory(entityManagerFactory);

        return txManager;
    }

    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(s3AccessKeyId, s3SecretKey);
        return AmazonS3Client.builder()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Endpoint, "us-east-1"))
                .withClientConfiguration(new ClientConfiguration().withProtocol(Protocol.HTTP))
                .build();
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    @Bean
    public GCage gCage(){
        return new GCage();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
