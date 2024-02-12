package uz.ciasev.ubdd_service.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.specifications.ProtocolSpecifications;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Primary
@Service
@RequiredArgsConstructor
public class ProtocolUniquenessValidationServiceImpl implements ProtocolUniquenessValidationService {

    private final ProtocolRepository protocolRepository;
    private final ProtocolSpecifications protocolSpecifications;

    @Override
    public void validate(User user, Person person, ProtocolRequestDTO protocol) {
        Optional<Specification<Protocol>> specificationOptional = getSpecification(user, person, protocol);
        if (specificationOptional.isEmpty()) {
            return;
        }

        List<Long> sameProtocols = protocolRepository.findAllId(specificationOptional.get());

        if (!sameProtocols.isEmpty()) {
            throw new ValidationException(ErrorCode.DUPLICATE_PROTOCOL_ERROR);
        }
    }

    private boolean isDoNotCheckUniqueness(ProtocolRequestDTO protocol) {
        return protocol.getArticleViolationType() != null && protocol.getArticleViolationType().getDontCheckUniqueness();
    }

    private Optional<Specification<Protocol>> getSpecification(User user, Person person, ProtocolRequestDTO protocol) {
        if (user.getOrgan().isGai()) {
            return getGaiSpecification(user, person, protocol);
        }


        return getDefaultSpecification(user, person, protocol);
    }

    private Optional<Specification<Protocol>> getGaiSpecification(User user, Person person, ProtocolRequestDTO protocol) {
        if (isDoNotCheckUniqueness(protocol)) {
            return Optional.empty();
        }

        Long articleViolationTypeId = Optional.ofNullable(protocol.getArticleViolationType()).map(ArticleViolationType::getId).orElse(null);
//        String vehicleNumber = Optional.ofNullable(protocol.getAdditional()).map(ProtocolRequestAdditionalDTO::getUbdd).map(ProtocolUbddDataRequestDTO::getVehicleNumber).orElse(null);
        String vehicleNumber = protocol.getVehicleNumber();

        return Optional.of(SpecificationsCombiner.andAll(
                protocolSpecifications.withViolatorPersonId(person.getId()),
                protocolSpecifications.withUserId(user.getId()),
                protocolSpecifications.withArticlePartId(protocol.getArticlePart().getId()),
                protocolSpecifications.withArticleViolationTypeIdExactly(articleViolationTypeId),
                protocolSpecifications.registeredAfter(LocalDateTime.now().minusHours(1)),
                protocolSpecifications.withUbddVehicleNumberExactly(vehicleNumber)
        ));
    }

    private Optional<Specification<Protocol>> getDefaultSpecification(User user, Person person, ProtocolRequestDTO protocol) {
        if (isDoNotCheckUniqueness(protocol)) {
            return Optional.empty();
        }

        Long articleViolationTypeId = Optional.ofNullable(protocol.getArticleViolationType()).map(ArticleViolationType::getId).orElse(null);

        return Optional.of(SpecificationsCombiner.andAll(
                protocolSpecifications.withViolatorPersonId(person.getId()),
                protocolSpecifications.withUserId(user.getId()),
                protocolSpecifications.withArticlePartId(protocol.getArticlePart().getId()),
                protocolSpecifications.withArticleViolationTypeIdExactly(articleViolationTypeId),
                protocolSpecifications.registeredAfter(LocalDate.now())
        ));
    }
}
