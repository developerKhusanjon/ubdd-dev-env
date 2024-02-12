package uz.ciasev.ubdd_service.service.protocol;

//@Service
//@RequiredArgsConstructor
public class ProtocolDTOOldServiceImpl {

//    @Override
//    public List<ArticleResponseDTO> findAllProtocolArticles(Long protocolId) {
//        return protocolService.getAllProtocolArticles(protocolId)
//                .stream()
//                .map(protocolArticle -> new ArticleResponseDTO(
//                        protocolArticle,
//                        articlePartViolationTypeService.findByArticlePartIdAndViolationTypeId(protocolArticle)
//                )).collect(Collectors.toList());
//    }
//    @Override
//    public List<RegisteredProtocolListDTO> findAllByAdmCaseId(Long admCaseId) {
//        return protocolSearchService
//                .findAllSimpleProjectionByAdmCaseId(admCaseId)
//                .stream()
//                .map(RegisteredProtocolListDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Page<ProtocolFullListResponseDTO> globalSearchByFilter(Map<String, String> filterValues, Pageable pageable) {
//        return protocolSearchService.findAllFullProjectionByFilter(filterValues, pageable)
//                .map(ProtocolFullListResponseDTO::new);
//    }
//
//    @Override
//    public Page<ProtocolUbddListResponseDTO> findAllUserUbddListProjectionByFilter(User user, Map<String, String> filterValues, Pageable pageable) {
//        return protocolSearchService.findAllUserUbddListProjectionByFilter(user, filterValues, pageable)
//                .map(ProtocolUbddListResponseDTO::new);
//    }
//
//    @Override
//    public ProtocolDetailResponseDTO findDTOById(Long id) {
//        Protocol protocol = protocolService.getById(id);
//        return buildResponseDTO(protocol);
//    }
//
//    @Override
//    public ProtocolDetailResponseDTO findDTOByExternalId(User user, String id) {
//        Protocol protocol = protocolService.findByExternalId(user, id);
//        return buildResponseDTO(protocol);
//    }
//
//    @Override
//    public ProtocolDetailResponseDTO buildResponseDTO(Protocol protocol) {
//
//        Long id = protocol.getId();
//        ViolatorDetailResponseDTO violatorDetail = violatorDetailService.findDTOById(protocol.getViolatorDetailId());
//        AdmCaseResponseDTO admCase = new AdmCaseResponseDTO(admCaseService.getById(violatorDetail.getViolator().getAdmCaseId()));
//        JuridicDetailResponseDTO juridic = Optional.ofNullable(protocol.getJuridicId()).map(juridicService::findDetailById).orElse(null);
//        List<RepeatabilityResponseDTO> repeatability = repeatabilityRepository
//                .findAllByProtocolId(id)
//                .stream()
//                .map(RepeatabilityResponseDTO::new)
//                .collect(Collectors.toList());
//
//        ProtocolStatisticData statistic = protocolStatisticDataService.findByProtocolId(id).orElse(null);
//        ProtocolStatisticDataResponseDTO statisticDTO = protocolStatisticDataService.getResponseDTO(statistic);
//
//        // TODO До устарения апи старой убдд структур
//        ProtocolUbddDataView ubdd = oldStructureService.findByProtocolId(id).orElse(null);
//        ProtocolUbddDataResponseUbddDTO ubddDTO = protocolUbddDataService.getResponseUbddDTO(ubdd);
//        ProtocolUbddDataResponseTransportDTO transportDTO = protocolUbddDataService.getResponseTransportDTO(ubdd);
//
//        List<ArticleResponseDTO> protocolArticles = ConvertUtils.protocolArticleToDTO(protocolService.getProtocolAdditionArticles(protocol.getId()));
//
//        UbddDataToProtocolBindInternalDTO ubddDataBind = ubddDataToProtocolBindService.findInternalDTOByProtocolId(protocol.getId()).orElse(null);
//
//        return new ProtocolDetailResponseDTO(protocol, admCase, violatorDetail, juridic, protocolArticles, repeatability, statisticDTO, ubddDTO, transportDTO, ubddDataBind);
//    }
//
//    @Override
//    public ProtocolDetailResponseDTO buildSingleResponseDTO(Protocol protocol) {
//
//        ViolatorDetailResponseDTO violatorDetail = violatorDetailService.convertToDTO(protocol.getViolatorDetail());
//        AdmCaseResponseDTO admCase = new AdmCaseResponseDTO(admCaseService.getById(violatorDetail.getViolator().getAdmCaseId()));
//
//
//        JuridicDetailResponseDTO juridic = null;
//        List<RepeatabilityResponseDTO> repeatability = null;
//
//        ProtocolStatisticData statistic = null;
//        ProtocolStatisticDataResponseDTO statisticDTO = null;
//
//        ProtocolUbddData ubdd = null;
//        ProtocolUbddDataResponseUbddDTO ubddDTO = null;
//        ProtocolUbddDataResponseTransportDTO transportDTO = null;
//        List<ArticleResponseDTO> protocolArticles = null;
//
//        UbddDataToProtocolBindInternalDTO ubddDataBind = null;
//
//        return new ProtocolDetailResponseDTO(protocol, admCase, violatorDetail, juridic, protocolArticles, repeatability, statisticDTO, ubddDTO, transportDTO, ubddDataBind);
//    }
//
//    @Override
//    public List<ViolationResponseDTO> findViolations(Map<String, String> filters) {
//        return protocolSearchService.findAllViolationView(filters)
//                .stream()
//                .map(ViolationResponseDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Page<ProtocolLocationResponseDTO> findProtocolLocations(User user, Double latitude, Double
//            longitude, Double radius) {
//        return protocolService.findProtocolLocations(latitude, longitude, radius)
//                .map(pl -> new ProtocolLocationResponseDTO(pl.getLatitude(), pl.getLongitude()));
//    }
//
//    @Override
//    public Page<ProtocolLocationResponseDTO> findProtocolLocations(User user, Long regionId, Double latMin, Double
//            lonMin, Double latMax, Double lonMax, LocalDateTime createdFrom, Pageable pageable) {
//        return protocolService.findProtocolLocations(regionId, latMin, lonMin, latMax, lonMax, createdFrom, pageable)
//                .map(pl -> new ProtocolLocationResponseDTO(pl.getLatitude(), pl.getLongitude()));
//    }
}
