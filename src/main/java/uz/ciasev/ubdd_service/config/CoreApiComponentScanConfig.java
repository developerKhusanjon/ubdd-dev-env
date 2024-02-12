package uz.ciasev.ubdd_service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("core-api")
@Configuration
@ComponentScan(basePackages="uz.ciasev.ubdd_service.coreapi")
public class CoreApiComponentScanConfig {

        public CoreApiComponentScanConfig() {
                System.out.println("CoreApiComponentScanConfig will add package 'uz.ciasev.ubdd_service.coreapi'");
        }
}
