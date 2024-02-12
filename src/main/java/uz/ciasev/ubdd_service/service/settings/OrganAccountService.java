package uz.ciasev.ubdd_service.service.settings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.response.settings.OrganAccountSettingsResponseDTO;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrganAccountService {

    OrganAccountSettings getById(UUID id);

    Optional<OrganAccountSettings> findByPlace(Place place, ArticlePart articlePart, Long bankAccountType);

    OrganAccountSettings getByPlace(Place place, @Nullable ArticlePart articlePart, Long bankAccountType);

    Page<OrganAccountSettings> findAll(Map<String, String> filters, Pageable pageable);

    OrganAccountSettingsResponseDTO buildDTO(OrganAccountSettings entity);
}
