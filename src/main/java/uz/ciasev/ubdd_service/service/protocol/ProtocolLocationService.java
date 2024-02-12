package uz.ciasev.ubdd_service.service.protocol;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.*;
import uz.ciasev.ubdd_service.entity.user.User;

import java.time.LocalDateTime;

@Validated
public interface ProtocolLocationService {

    Page<ProtocolLocationResponseDTO> findProtocolLocations(User user,
                                                            Double latitude,
                                                            Double longitude,
                                                            Double radius);

    Page<ProtocolLocationResponseDTO> findProtocolLocations(User user,
                                                            Long regionId,
                                                            Double latMin,
                                                            Double lonMin,
                                                            Double latMax,
                                                            Double lonMax,
                                                            LocalDateTime createdFrom,
                                                            Pageable pageable);
}
