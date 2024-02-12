package uz.ciasev.ubdd_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.context.annotation.FilterType.REGEX;

@EnableAutoConfiguration
@Configuration
@ComponentScan(
        basePackages = "uz.ciasev.ubdd_service",
        excludeFilters = {
                @ComponentScan.Filter(type = REGEX, pattern = "uz.ciasev.ubdd_service.controller_ubdd.*"),
                @ComponentScan.Filter(type = REGEX, pattern = "uz.ciasev.ubdd_service.mvd_core.mvd_core.citizenapi.*")

        })
public class UbddServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UbddServiceApplication.class, args);
    }
}
