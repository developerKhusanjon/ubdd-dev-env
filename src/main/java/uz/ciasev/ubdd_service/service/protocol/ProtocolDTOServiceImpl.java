package uz.ciasev.ubdd_service.service.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.adm.ViolationResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.JuridicDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.*;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataToProtocolBindInternalDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.*;
import uz.ciasev.ubdd_service.entity.ubdd_data.old_structure.ProtocolUbddDataView;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.repository.protocol.RepeatabilityRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartViolationTypeService;
import uz.ciasev.ubdd_service.service.juridic.JuridicService;
import uz.ciasev.ubdd_service.service.ubdd_data.UbddDataToProtocolBindService;
import uz.ciasev.ubdd_service.service.ubdd_data.old_structure.UbddOldStructureService;
import uz.ciasev.ubdd_service.service.violator.ViolatorDetailService;
import uz.ciasev.ubdd_service.utils.ConvertUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProtocolDTOServiceImpl implements ProtocolDTOService {

    private final ProtocolService protocolService;
    private final AdmCaseService admCaseService;
    private final JuridicService juridicService;
    private final ViolatorDetailService violatorDetailService;
    private final ProtocolStatisticDataService protocolStatisticDataService;
    private final ProtocolUbddDataService protocolUbddDataService;
    private final UbddOldStructureService oldStructureService;
    private final RepeatabilityRepository repeatabilityRepository;
    private final UbddDataToProtocolBindService ubddDataToProtocolBindService;
    private final ArticlePartViolationTypeService articlePartViolationTypeService;
    private final AdmCaseAccessService admCaseAccessService;
    private final AdmEventService notificatorService;


    @Override
    @Transactional
    public ProtocolDetailResponseDTO buildDetail(User user, Supplier<Protocol> supplier) {
        return convertToDetailDTO(user, supplier.get());
    }

    @Override
    public ProtocolDetailResponseDTO buildDetailForCreateProtocol(User user, Supplier<Protocol> supplier) {
        return convertToDetailDTOForCreateProtocol(user, supplier.get());

    }

    @Override
    public List<RegisteredProtocolListDTO> buildRegisteredList(Supplier<List<ProtocolSimpleListProjection>> supplier) {
        return supplier.get()
                .stream()
                .map(RegisteredProtocolListDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProtocolFullListResponseDTO> buildFullList(Supplier<List<ProtocolFullListProjection>> supplier) {
        return supplier.get()
                .stream()
                .map(ProtocolFullListResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProtocolFullListResponseDTO> buildFullPage(Supplier<Page<ProtocolFullListProjection>> supplier) {
        return supplier.get()
                .map(ProtocolFullListResponseDTO::new);
    }

    @Override
    public Page<ProtocolUbddListResponseDTO> buildUbddPage(Supplier<Page<ProtocolUbddListView>> supplier) {
        return supplier.get()
                .map(ProtocolUbddListResponseDTO::new);
    }

    @Override
    public List<ViolationResponseDTO> buildViolationList(Supplier<List<ViolationListView>> supplier) {
        return supplier.get()
                .stream()
                .map(ViolationResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleResponseDTO> buildArticleList(Supplier<List<ProtocolArticle>> supplier) {
        return supplier.get()
                .stream()
                .map(protocolArticle -> new ArticleResponseDTO(
                        protocolArticle,
                        articlePartViolationTypeService.findByArticlePartIdAndViolationTypeId(protocolArticle)
                )).collect(Collectors.toList());
    }

    @Override
    public ProtocolDetailResponseDTO convertToDetailDTO(User user, Protocol protocol) {
        Long id = protocol.getId();
        ViolatorDetailResponseDTO violatorDetail = violatorDetailService.findDTOById(protocol.getViolatorDetailId());
        AdmCase admCase = admCaseService.getById(violatorDetail.getViolator().getAdmCaseId());
        JuridicDetailResponseDTO juridic = Optional.ofNullable(protocol.getJuridicId()).map(juridicService::findDetailById).orElse(null);
        List<RepeatabilityResponseDTO> repeatability = repeatabilityRepository
                .findAllByProtocolId(id)
                .stream()
                .map(RepeatabilityResponseDTO::new)
                .collect(Collectors.toList());

        List<ActionAlias> permittedActions = admCaseAccessService.calculatePermittedActions(user, admCase);

        ProtocolStatisticData statistic = protocolStatisticDataService.findByProtocolId(id).orElse(null);
        ProtocolStatisticDataResponseDTO statisticDTO = protocolStatisticDataService.getResponseDTO(statistic);

        ProtocolUbddDataView ubdd = oldStructureService.findByProtocolId(id).orElse(null);
        ProtocolUbddDataResponseUbddDTO ubddDTO = protocolUbddDataService.getResponseUbddDTO(ubdd);
        ProtocolUbddDataResponseTransportDTO transportDTO = protocolUbddDataService.getResponseTransportDTO(ubdd);

        List<ArticleResponseDTO> protocolArticles = ConvertUtils.protocolArticleToDTO(protocolService.getProtocolAdditionArticles(protocol.getId()));

        UbddDataToProtocolBindInternalDTO ubddDataBind = ubddDataToProtocolBindService.findInternalDTOByProtocolId(protocol.getId()).orElse(null);


        return new ProtocolDetailResponseDTO(protocol, new AdmCaseResponseDTO(admCase), violatorDetail, juridic, protocolArticles, repeatability, statisticDTO, ubddDTO, transportDTO, ubddDataBind, permittedActions);
    }

    @Override
    public ProtocolDetailResponseDTO convertToDetailDTOForCreateProtocol(User user, Protocol protocol) {

        Long id = protocol.getId();
        ViolatorDetailResponseDTO violatorDetail = violatorDetailService.findDTOById(protocol.getViolatorDetailId());
        AdmCase admCase = admCaseService.getById(violatorDetail.getViolator().getAdmCaseId());
        JuridicDetailResponseDTO juridic = Optional.ofNullable(protocol.getJuridicId()).map(juridicService::findDetailById).orElse(null);

        List<RepeatabilityResponseDTO> repeatability = repeatabilityRepository
                .findAllByProtocolId(id)
                .stream()
                .map(RepeatabilityResponseDTO::new)
                .collect(Collectors.toList());

        List<ActionAlias> permittedActions = admCaseAccessService.calculatePermittedActions(user, admCase);

        ProtocolStatisticData statistic = protocolStatisticDataService.findByProtocolId(id).orElse(null);
        ProtocolStatisticDataResponseDTO statisticDTO = protocolStatisticDataService.getResponseDTO(statistic);

        ProtocolUbddDataView ubdd = oldStructureService.findByProtocolId(id).orElse(null);
        ProtocolUbddDataResponseUbddDTO ubddDTO = protocolUbddDataService.getResponseUbddDTO(ubdd);
        ProtocolUbddDataResponseTransportDTO transportDTO = protocolUbddDataService.getResponseTransportDTO(ubdd);

        List<ArticleResponseDTO> protocolArticles = ConvertUtils.protocolArticleToDTO(protocolService.getProtocolAdditionArticles(protocol.getId()));

        UbddDataToProtocolBindInternalDTO ubddDataBind = ubddDataToProtocolBindService.findInternalDTOByProtocolId(protocol.getId()).orElse(null);

        notificatorService.fireEvent(AdmEventType.PROTOCOL_CREATE, protocol);

        return new ProtocolDetailResponseDTO(protocol, new AdmCaseResponseDTO(admCase), violatorDetail, juridic, protocolArticles, repeatability, statisticDTO, ubddDTO, transportDTO, ubddDataBind, permittedActions);

    }

    @Override
    public ProtocolDetailResponseDTO convertToSingleResponseDTO(Protocol protocol) {
        ViolatorDetailResponseDTO violatorDetail = violatorDetailService.convertToDTO(protocol.getViolatorDetail());
        AdmCaseResponseDTO admCase = new AdmCaseResponseDTO(admCaseService.getById(violatorDetail.getViolator().getAdmCaseId()));


        JuridicDetailResponseDTO juridic = null;
        List<RepeatabilityResponseDTO> repeatability = null;

        ProtocolStatisticData statistic = null;
        ProtocolStatisticDataResponseDTO statisticDTO = null;

        ProtocolUbddData ubdd = null;
        ProtocolUbddDataResponseUbddDTO ubddDTO = null;
        ProtocolUbddDataResponseTransportDTO transportDTO = null;
        List<ArticleResponseDTO> protocolArticles = null;

        UbddDataToProtocolBindInternalDTO ubddDataBind = null;

        return new ProtocolDetailResponseDTO(protocol, admCase, violatorDetail, juridic, protocolArticles, repeatability, statisticDTO, ubddDTO, transportDTO, ubddDataBind, List.of());
    }
}
