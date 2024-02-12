package uz.ciasev.ubdd_service.service.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.*;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolLocationProjection;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.utils.DistanceCalculator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProtocolLocationServiceImpl implements ProtocolLocationService {

    private final ProtocolRepository protocolRepository;


    @Override
    public Page<ProtocolLocationResponseDTO> findProtocolLocations(User user, Double latitude, Double
            longitude, Double radius) {
        return findProtocolLocationsInner(latitude, longitude, radius)
                .map(pl -> new ProtocolLocationResponseDTO(pl.getLatitude(), pl.getLongitude()));
    }

    @Override
    public Page<ProtocolLocationResponseDTO> findProtocolLocations(User user, Long regionId, Double latMin, Double
            lonMin, Double latMax, Double lonMax, LocalDateTime createdFrom, Pageable pageable) {
        return findProtocolLocationsInner(regionId, latMin, lonMin, latMax, lonMax, createdFrom, pageable)
                .map(pl -> new ProtocolLocationResponseDTO(pl.getLatitude(), pl.getLongitude()));
    }



    private Page<ProtocolLocationProjection> findProtocolLocationsInner(Double latitude, Double longitude, Double radius) {
        List<Pair<Double, Double>> minMax = DistanceCalculator.minMaxCoordinates(latitude, longitude, radius);
        return protocolRepository.findProtocolLocations(
                0L,
                minMax.get(0).getFirst(),
                minMax.get(0).getSecond(),
                minMax.get(1).getFirst(),
                minMax.get(1).getSecond(),
                LocalDateTime.MIN,
                Pageable.unpaged()
        );
    }

    private Page<ProtocolLocationProjection> findProtocolLocationsInner(Long regionId,
                                                                        Double latMin,
                                                                        Double lonMin,
                                                                        Double latMax,
                                                                        Double lonMax,
                                                                        LocalDateTime createdFrom,
                                                                        Pageable pageable) {
        return protocolRepository.findProtocolLocations(
                Optional.ofNullable(regionId).orElse(0L),
                latMin,
                lonMin,
                latMax,
                lonMax,
                createdFrom,
                pageable
        );
    }
}
