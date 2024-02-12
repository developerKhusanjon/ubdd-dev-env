package uz.ciasev.ubdd_service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("citizen-api")
@Configuration
@ComponentScan(basePackages="uz.ciasev.ubdd_service.mvd_core.citizenapi")
public class CitizenApiComponentScanConfig {

        public CitizenApiComponentScanConfig() {
                System.out.println("CitizenApiComponentScanConfig will add package 'uz.ciasev.ubdd_service.mvd_core.citizenapi'");
        }
}
