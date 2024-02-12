package uz.ciasev.ubdd_service.mvd_core.api.manzil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.ExternalAddressService;
import uz.ciasev.ubdd_service.mvd_core.api.ExternalApiAddress;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;

import java.util.Optional;

@Slf4j
@Service
public class ManzilServiceImpl implements ManzilService {

    private final String urlTemplate = "%s/internal/manzil-api/person/%s/permanent-registration";
    private final String host;
    private final RestTemplate restTemplate;
    private final ExternalAddressService externalAddressService;

    public ManzilServiceImpl(@Value("${manzil-api.host}") String host,
                             @Qualifier("manzilRestTemplate") RestTemplate restTemplate,
                             ExternalAddressService externalAddressService) {
        this.host = host;
        this.restTemplate = restTemplate;
        this.externalAddressService = externalAddressService;
    }

    @Override
    public Optional<Address> findAddressByPinpp(String pinpp) {
        return requestAddressNotThrow(pinpp)
                .map(externalAddressService::buildAddress);
    }

    @Override
    public Optional<AddressResponseDTO> findAddressDTOByPinpp(String pinpp) {
        return requestAddressNotThrow(pinpp).map(AddressResponseDTO::new);
    }

    private Optional<ExternalApiAddress> requestAddressNotThrow(String pinpp) {
        try {
            ExternalApiAddress response = restTemplate.getForObject(
                    String.format(urlTemplate, host, pinpp),
                    ExternalApiAddress.class
            );
            return Optional.ofNullable(response);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            log.error("MANZIL ERROR FOR PINPP {}: {}", pinpp, e.getMessage());
            return Optional.empty();
        }
    }
}
