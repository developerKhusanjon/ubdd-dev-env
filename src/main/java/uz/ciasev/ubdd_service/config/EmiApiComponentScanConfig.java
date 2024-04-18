package uz.ciasev.ubdd_service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"ubdd-api", "publicapi"})
@Configuration
@ComponentScan(
        basePackages={
                "uz.ciasev.ubdd_service.controller_ubdd"
        }
)
public class EmiApiComponentScanConfig {

        public EmiApiComponentScanConfig() {
                System.out.println("EmiApiComponentScanConfig will add package 'uz.ciasev.ubdd_service.controller_ubdd'");
        }
}
