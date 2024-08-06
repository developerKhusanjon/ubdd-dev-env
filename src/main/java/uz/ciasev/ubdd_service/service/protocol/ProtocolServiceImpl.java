package uz.ciasev.ubdd_service.service.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.AdmCaseArticlesDTO;
import uz.ciasev.ubdd_service.entity.Inspector;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolArticleRepository;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.service.generator.ProtocolNumberGeneratorService;
import uz.ciasev.ubdd_service.service.user.SystemUserService;
import uz.ciasev.ubdd_service.specifications.ProtocolSpecifications;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProtocolServiceImpl implements ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final ProtocolNumberGeneratorService generatorService;
    private final ProtocolArticleRepository protocolArticleRepository;
    private final ProtocolSpecifications protocolSpecifications;
    private final SystemUserService systemUserService;


    @Override
    @Transactional
    public Protocol create(Inspector user, ViolatorDetail violatorDetail, ProtocolCreateRequest protocol, List<ProtocolArticle> additionArticles) {

        protocol.setCreatedUser(systemUserService.getCurrentUser());

        protocol.setUser(user.getUser());
        protocol.setInspectorInfo(protocol.getInspectorInfo());
        protocol.setInspectorFio(protocol.getInspectorFio());
        protocol.setInspectorRegion(protocol.getInspectorRegion());
        protocol.setInspectorDistrict(protocol.getInspectorDistrict());
        protocol.setInspectorRank(user.getRank());
        protocol.setInspectorPosition(user.getPosition());
        protocol.setInspectorWorkCertificate(user.getWorkCertificate());
        protocol.setOrgan(user.getOrgan());
        protocol.setDepartment(user.getDepartment());
        protocol.setViolatorDetail(violatorDetail);

        String number = generatorService.generateNumber(protocol);
        String series = generatorService.generateSeries(protocol);

        protocol.setSeries(series);
        protocol.setNumber(number);

        Protocol savedProtocol = protocolRepository.save(new Protocol(protocol));
        saveAdditionArticles(savedProtocol, additionArticles);

        return savedProtocol;
    }

    @Override
    @Transactional
    public Protocol update(Protocol protocol, List<ProtocolArticle> additionArticles) {
        Protocol savedProtocol = protocolRepository.save(protocol);
        saveAdditionArticles(savedProtocol, additionArticles);
        return savedProtocol;
    }

    @Override
    @Transactional
    public void editMainArticle(Protocol protocol, ProtocolArticle mainArticle) {
        protocolArticleRepository.setMainToFalseForAllByProtocolId(protocol.getId());
        protocolArticleRepository.makeMain(mainArticle);

        protocol.setMainArticle(mainArticle);
        protocolRepository.save(protocol);
    }

    @Override
    public Protocol findById(Long id) {
        return protocolRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Protocol.class, id));
    }

    @Override
    public Protocol findByExternalId(User user, String id) {
        if (user.getOrgan() == null) {
            throw new ValidationException("USER_HAS_NO_ORGAN");
        }

        return protocolRepository
                .findByExternalIdAndOrganId(id, user.getOrganId())
                .orElseThrow(() -> new EntityByParamsNotFound(Protocol.class, "externalId", id, "organId", user.getOrganId()));
    }

    @Override
    public Protocol findSingleMainByAdmCaseId(Long admCaseId) {
        List<Protocol> protocols = protocolRepository.findAll(
                protocolSpecifications.withAdmCaseId(admCaseId)
                        .and(protocolSpecifications.withIsMain(true))
        );

        if (protocols.isEmpty())
            throw new AdmCaseNotContainMainProtocolsException();

        if (protocols.size() > 1)
            throw new AdmCaseNotSingleViolatorException();

        return protocols.get(0);
    }

    @Override
    public List<Protocol> findAllByViolatorsId(List<Long> violatorsId) {
        return protocolRepository.findAll(protocolSpecifications.withViolatorIdInExactly(violatorsId));
    }

    @Override
    public List<Protocol> findAllByViolatorId(Long violatorId) {
        return protocolRepository.findAll(protocolSpecifications.withViolatorIdExactly(violatorId));
    }

    @Override
    public List<Long> findAllIdByViolatorId(Long violatorId) {
        return protocolRepository.findAllId(protocolSpecifications.withViolatorIdExactly(violatorId));
    }

    @Override
    public List<Long> findAllIdByFromDate(LocalDateTime fromDate) {
        return protocolRepository.findAllByCreatedTimeAfter(fromDate);
    }

    @Override
    public AdmCaseArticlesDTO findAllArticleByAdmCase(Long admCaseId) {
        return new AdmCaseArticlesDTO(protocolArticleRepository.findAllByAdmCaseId(admCaseId));
    }

    @Override
    public ProtocolArticle getProtocolArticleById(Long id) {
        return protocolArticleRepository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(ProtocolArticle.class, id));
    }

    @Override
    public List<ProtocolArticle> getProtocolAdditionArticles(Long protocolId) {
        return protocolArticleRepository.findAllByProtocolId(protocolId).stream().filter(p -> !p.getIsMain()).collect(toList());
    }

    @Override
    public List<ProtocolArticle> getAllProtocolArticles(Long protocolId) {
        return protocolArticleRepository.findAllByProtocolId(protocolId);
    }

    @Override
    public List<ProtocolArticle> getViolatorArticles(Violator violator) {
        return protocolArticleRepository.findAllByViolatorId(violator.getId());
    }

    @Override
    public List<ArticlePart> getViolatorArticleParts(Violator violator) {
        return protocolArticleRepository.findAllArticlePartsByViolatorId(violator.getId());
    }

    @Override
    public ArticlePart getViolatorMainArticle(Violator violator) {
        return protocolArticleRepository.findMainByViolatorId(violator.getId())
                .orElseThrow(ViolatorMainArticlePartNotFoundException::new);
    }

    @Override
    public List<ArticlePart> findMainArticlePartsByAdmCase(AdmCase admCase) {
        return protocolArticleRepository.findAllMainArticlePatsByAdmCaseId(admCase.getId());
    }

//    @Override
//    @Transactional
//    public void saveAll(List<Protocol> protocols) {
//        protocolRepository.saveAll(protocols);
//    }

    @Override
    public List<Protocol> findAllProtocolsInAdmCase(Long admCaseId) {
        return protocolRepository
                .findAllByAdmCaseId(admCaseId);
    }

    @Override
    public List<Long> findAllProtocolsIdInAdmCase(Long admCaseId) {
        return protocolRepository.findAllId(protocolSpecifications.withAdmCaseId(admCaseId));
    }

    private void saveAdditionArticles(Protocol protocol, List<ProtocolArticle> additionArticles) {
        protocolArticleRepository.deleteByProtocolId(protocol.getId());

        ProtocolArticle mainArticle = new ProtocolArticle();
        mainArticle.setProtocol(protocol);
        mainArticle.setArticle(protocol.getArticle());
        mainArticle.setArticlePart(protocol.getArticlePart());
        mainArticle.setArticleViolationType(protocol.getArticleViolationType());
        mainArticle.setIsMain(true);

        List<ProtocolArticle> protocolArticles = new ArrayList<>();
        protocolArticles.add(mainArticle);

        additionArticles.stream().peek(article -> {
            article.setProtocol(protocol);
            article.setIsMain(false);
        }).forEach(protocolArticles::add);

        protocolArticleRepository.saveAll(protocolArticles);
    }

    @Override
    public boolean existsJuvenileByAdmCaseId(Long admCaseId) {
        return !protocolRepository.findAllId(SpecificationsCombiner.andAll(
                protocolSpecifications.withAdmCaseId(admCaseId),
                protocolSpecifications.withJuvenileViolator()
        )).isEmpty();
    }

    @Override
    public boolean existsProtocolAdditionArticles(Protocol protocol) {
        return !protocolArticleRepository.findAllAdditionalArticlePartsId(protocol.getId()).isEmpty();
    }

    @Override
    public Protocol getMainByViolatorId(Long violatorId) {
        List<Protocol> protocols = protocolRepository.findAll(
                protocolSpecifications.withViolatorIdExactly(violatorId)
                        .and(protocolSpecifications.withIsMain(true))
        );

        if (protocols.isEmpty()) throw new ViolatorMainProtocolNotFoundException();

        if (protocols.size() > 1) throw new LogicalException(String.format("Found %s main protocols for violator %s", protocols.size(), violatorId));

        return protocols.get(0);

    }

    @Override
    public Protocol findEarliestProtocolInAdmCase(Long admCaseId) {

        List<Protocol> protocolList = protocolRepository.findFirstByAdmCaseIdSorted(
                admCaseId,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "createdTime"))
        );

        if (protocolList.isEmpty()) {
            throw new ValidationException(ErrorCode.PROTOCOL_NOT_FOUND_IN_ADM_CASE);
        }

        return protocolList.get(0);
    }




//    @Override
//    public Page<ProtocolLocationProjection> findProtocolLocations(Double latitude, Double longitude, Double radius) {
//        List<Pair<Double, Double>> minMax = DistanceCalculator.minMaxCoordinates(latitude, longitude, radius);
//        return protocolRepository.findProtocolLocations(
//                0L,
//                minMax.get(0).getFirst(),
//                minMax.get(0).getSecond(),
//                minMax.get(1).getFirst(),
//                minMax.get(1).getSecond(),
//                LocalDateTime.MIN,
//                Pageable.unpaged()
//        );
//    }
//
//    @Override
//    public Page<ProtocolLocationProjection> findProtocolLocations(Long regionId,
//                                                        Double latMin,
//                                                        Double lonMin,
//                                                        Double latMax,
//                                                        Double lonMax,
//                                                        LocalDateTime createdFrom,
//                                                        Pageable pageable) {
//        return protocolRepository.findProtocolLocations(
//                Optional.ofNullable(regionId).orElse(0L),
//                latMin,
//                lonMin,
//                latMax,
//                lonMax,
//                createdFrom,
//                pageable
//        );
//    }
}
