package uz.ciasev.ubdd_service.config.controller_advice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver() {
            @Override
            public Pageable resolveArgument(MethodParameter methodParameter,
                                            ModelAndViewContainer mavContainer,
                                            NativeWebRequest webRequest,
                                            WebDataBinderFactory binderFactory) {
                if ("unpaged".equals(webRequest.getParameter(getParameterNameToUse("page", methodParameter)))) {
                    return Pageable.unpaged();
                }
                return super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
            }
        };


        pageableResolver.setMaxPageSize(1000000);
        resolvers.add(pageableResolver);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jacksonMessageConverter = (MappingJackson2HttpMessageConverter) converter;
                jacksonMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
                jacksonMessageConverter.getObjectMapper().registerModule(new StringTrimModule());
                break;
            }
        }
    }

//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
////        configurer.setTaskExecutor(mvcTaskExecutor());
//
//        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
//        t.setThreadNamePrefix("mvc-task-");
//        t.setCorePoolSize(1);
//        t.setMaxPoolSize(2);
//        t.setQueueCapacity(1);
////        t.setAllowCoreThreadTimeOut(true);
//        t.setKeepAliveSeconds(5);
//        t.initialize();
//
//        configurer.setTaskExecutor(t);
//    }



    static class StringTrimModule extends SimpleModule {

        public StringTrimModule() {
            addDeserializer(String.class, new StdScalarDeserializer<String>(String.class) {
                @Override
                public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                    return StringUtils.trimWhitespace(jp.getValueAsString());
                }
            });
        }
    }

}
