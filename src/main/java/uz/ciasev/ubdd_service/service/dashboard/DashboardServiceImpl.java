package uz.ciasev.ubdd_service.service.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.dasboard.*;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.dashboard.*;
import uz.ciasev.ubdd_service.repository.dashboard.projections.*;
import uz.ciasev.ubdd_service.repository.dict.DistrictRepository;
import uz.ciasev.ubdd_service.repository.dict.RegionRepository;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

@Primary
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;

    @Override
    public DashboardDTO buildDTO(User user, LocalDate dateFrom, LocalDate dateTo, DashboardFilterDTO filter) {

        LocalDateTime dateTimeFrom = from(dateFrom);
        LocalDateTime dateTimeTo = to(dateTo);

        DashboardDTO rsl = new DashboardDTO();

        rsl.setNumbers(getNumbers(dateTimeFrom, dateTimeTo, user, filter));
        rsl.setLineChart(getLineChart(dateTimeFrom, dateTimeTo, user, filter));

        DashboardCalculatedPieChartDTO calc = getDataAndCalculatePieChart(dateTimeFrom, dateTimeTo, user, filter);

        //rsl.setPieChart(getPieChart(calc));
        rsl.setMoney(makeMoney(getPieChart(calc)));
        rsl.setMonthProtocols(getBarChart(calc));

        rsl.setOrganProtocols(getProtocolPieChart(dateTimeFrom, dateTimeTo, user, filter));
        rsl.setProtocolByRegions(getProtocolBarChart(dateTimeFrom, dateTimeTo, user, filter));

        // REPEATABILITY
        rsl.setOrganRepeatability(getRepeatabilityByOrgan(dateTimeFrom, dateTimeTo, user, filter));
        rsl.setTotalRepeatability(getRepeatabilityByCount(dateTimeFrom, dateTimeTo, user, filter));

        return rsl;
    }

    private DashboardByOrganChartDTO getRepeatabilityByOrgan(LocalDateTime dateFrom, LocalDateTime dateTo, User user, DashboardFilterDTO filter) {

        List<RepeatabilityByOrganProjection> projections = dashboardRepository.protocolRepeatabilityByOrgan(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));

        List<Long> id = new LinkedList<>();
        List<Long> data = new LinkedList<>();

        projections.forEach(p -> {
            id.add(p.getOrganId());
            data.add(p.getCountValue());
        });

        DashboardByOrganChartDTO rsl = new DashboardByOrganChartDTO();

        rsl.setId(id);
        rsl.setData(data);

        return rsl;
    }

    private List<Long> getRepeatabilityByCount(LocalDateTime dateFrom, LocalDateTime dateTo, User user, DashboardFilterDTO filter) {

        List<RepeatabilityByCountProjection> projections = dashboardRepository.protocolRepeatabilityByCount(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));

        List<Long> rsl = new ArrayList<>();

        long maxIdx = projections.stream().map(RepeatabilityByCountProjection::getRepeatCount).max(Long::compareTo).orElse(0L) - 2L;

        for (long l = 0; l <= maxIdx; l++) {
            rsl.add(null);
        }

        projections.forEach(p -> {
            int idx = (int) (p.getRepeatCount() - 2L);
            rsl.set(idx, p.getCountValue());
        });

        return rsl;
    }

    private DashboardByOrganChartDTO getProtocolPieChart(LocalDateTime dateFrom, LocalDateTime dateTo, User user, DashboardFilterDTO filter) {

        List<ProtocolCountByOrganProjection> projections = dashboardRepository.protocolCountByOrgan(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));

        List<Long> id = new LinkedList<>();
        List<Long> data = new LinkedList<>();

        projections.forEach(p -> {
            id.add(p.getOrganId());
            data.add(p.getCountValue());
        });

        DashboardByOrganChartDTO rsl = new DashboardByOrganChartDTO();

        rsl.setId(id);
        rsl.setData(data);

        return rsl;
    }

    private DashboardByRegionChartDTO getProtocolBarChart(LocalDateTime dateFrom, LocalDateTime dateTo, User user, DashboardFilterDTO filter) {

        List<ProtocolCountByGeographyProjection> projections = dashboardRepository.protocolCountByGeography(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));

        DashboardByRegionChartDTO rsl = new DashboardByRegionChartDTO();

        List<Pair<MultiLanguage, Long>> namedResult = new LinkedList<>();

        if (filter.getRegionId() == null) {

            Map<Long, Long> groupedResult = projections.stream()
                    .collect(
                            groupingBy(
                                    ProtocolCountByGeographyProjection::getRegionId,
                                    summingLong(ProtocolCountByGeographyProjection::getCountValue)
                            )
                    );

            List<Region> regions = regionRepository.findAllActive();
            regions.forEach(r -> {
                namedResult.add(Pair.of(r.getName(), groupedResult.getOrDefault(r.getId(), 0L)));
            });

        } else {

            Map<Long, Long> groupedResult = projections.stream()
                    .collect(
                            groupingBy(
                                    ProtocolCountByGeographyProjection::getDistrictId,
                                    summingLong(ProtocolCountByGeographyProjection::getCountValue)
                            )
                    );

            List<District> districts = districtRepository.findAllByRegionId(filter.getRegionId());
            districts.forEach(r -> {
                namedResult.add(Pair.of(r.getName(), groupedResult.getOrDefault(r.getId(), 0L)));
            });
        }

        namedResult.forEach((p) -> {
            rsl.getLabelRu().add(p.getFirst().getRu());
            rsl.getLabelKir().add(p.getFirst().getKir());
            rsl.getLabelLat().add(p.getFirst().getLat());
            rsl.getData().add(p.getSecond());
        });

        return rsl;
    }

    private DashboardNumbersDTO getNumbers(LocalDateTime dateFrom, LocalDateTime dateTo, User user, DashboardFilterDTO filter) {

        List<CountByAdmStatusProjection> admCasesByStatus = dashboardRepository.admCaseCountByStatus(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));
        List<CountByAdmStatusProjection> decisionsByStatus = dashboardRepository.decisionCountByStatus(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));

        Long totalAdmCases = dashboardRepository.admCaseCount(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId())).orElse(0L);
        Long totalProtocols = dashboardRepository.protocolCount(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId())).orElse(0L);
        Long totalViolators = dashboardRepository.violatorCount(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId())).orElse(0L);
        Long totalVictims = dashboardRepository.victimCount(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId())).orElse(0L);
        Long totalResolutions = dashboardRepository.decisionCount(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId())).orElse(0L);

        TerminationProjection terminationProjection = dashboardRepository.terminationCount(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));
        Long totalTermination271 = Optional.ofNullable(terminationProjection.getCountValue()).orElse(0L);
        Long totalTermination21 = Optional.ofNullable(terminationProjection.getCountValue21()).orElse(0L);

        ProtocolCountByDevice protocolCountByDevice = dashboardRepository.protocolCountByDevice(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));

        Long totalPhotoVideo = 0L;
        Long byTablet = Optional.ofNullable(protocolCountByDevice.getCountTablet()).orElse(0L);
        Long byDesktop = Optional.ofNullable(protocolCountByDevice.getCountDesktop()).orElse(0L);

        //

        DashboardNumbersDTO rsl = new DashboardNumbersDTO();

        rsl.setTotalAdmCases(totalAdmCases);
        rsl.setTotalProtocols(totalProtocols);
        rsl.setTotalViolators(totalViolators);
        rsl.setTotalVictims(totalVictims);
        rsl.setTotalResolutions(totalResolutions);
        rsl.setCanceled271(totalTermination271);
        rsl.setCanceled21(totalTermination21);

        rsl.setTotalPhotoVideo(totalPhotoVideo);
        rsl.setByTablet(byTablet);
        rsl.setByDesktop(byDesktop);

        rsl.setAdmCaseRegistered(filterCountByAlias(admCasesByStatus, AdmStatusAlias.REGISTERED));
        rsl.setAdmCaseConsidered(filterCountByAlias(admCasesByStatus, AdmStatusAlias.CONSIDERING));
        rsl.setAdmCaseMerged(filterCountByAlias(admCasesByStatus, AdmStatusAlias.MERGED));
        rsl.setAdmCasePreparingToCourt(filterCountByAlias(admCasesByStatus, AdmStatusAlias.PREPARE_FOR_COURT));
        rsl.setAdmCaseSendToCourt(filterCountByAlias(admCasesByStatus, AdmStatusAlias.SENT_TO_COURT));
        rsl.setAdmCaseBackFromCourt(filterCountByAlias(admCasesByStatus, AdmStatusAlias.RETURN_FROM_COURT));
        rsl.setAdmCaseSendToOrgan(filterCountByAlias(admCasesByStatus, AdmStatusAlias.SENT_TO_ORGAN));
        rsl.setAdmCaseBackFromOrgan(filterCountByAlias(admCasesByStatus, AdmStatusAlias.RETURN_FROM_ORGAN));
        rsl.setAdmCaseExecutionAwaits(filterCountByAlias(admCasesByStatus, AdmStatusAlias.DECISION_MADE));
        // TODO: 06.11.2023  
        rsl.setAdmCaseInExecution(filterCountByAlias(admCasesByStatus, AdmStatusAlias.IN_EXECUTION_PROCESS));
        rsl.setAdmCaseExecuted(filterCountByAlias(admCasesByStatus, AdmStatusAlias.EXECUTED));

        rsl.setResolutionExecutionAwaits(filterCountByAlias(decisionsByStatus, AdmStatusAlias.DECISION_MADE));
        // TODO: 06.11.2023
        rsl.setResolutionInExecution(filterCountByAlias(decisionsByStatus, AdmStatusAlias.IN_EXECUTION_PROCESS));
        rsl.setResolutionExecuted(filterCountByAlias(decisionsByStatus, AdmStatusAlias.EXECUTED));
        rsl.setResolutionSendToMib(filterCountByAlias(decisionsByStatus, AdmStatusAlias.SEND_TO_MIB));
        rsl.setResolutionBackFromMib(filterCountByAlias(decisionsByStatus, AdmStatusAlias.RETURN_FROM_MIB));
        rsl.setResolutionJudicialAppeal(filterCountByAlias(decisionsByStatus, AdmStatusAlias.IN_REVIEW_PROCESS));

        return rsl;
    }

    private Long filterCountByAlias(List<CountByAdmStatusProjection> list, AdmStatusAlias alias) {
        return list
                .stream()
                .filter((ac) -> alias.toString().equals(ac.getAdmStatusAlias()))
                .map(CountByAdmStatusProjection::getCountValue)
                .reduce(Long::sum)
                .orElse(0L);
    }

    private List<Long> getLineChart(LocalDateTime dateFrom,
                                    LocalDateTime dateTo,
                                    User user,
                                    DashboardFilterDTO filter) {

        List<Long> rsl = new ArrayList<>();

        List<ProtocolCountByMonthProjection> projections = dashboardRepository.protocolCountByMonth(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));

        for (int i = 1; i <= dateTo.minusDays(1).getMonthValue(); i++) {
            final int monNum = i;
            rsl.add(
                projections
                        .stream()
                        .filter(p -> p.getLocalMonthDate().getMonthValue() == monNum)
                        .map(ProtocolCountByMonthProjection::getCountValue)
                        .reduce(Long::sum)
                        .orElse(0L)
            );
        }

        return rsl;
    }

    private DashboardPieChartDTO getPieChart(DashboardCalculatedPieChartDTO calc) {

        DashboardPieChartDTO rsl = new DashboardPieChartDTO();

        rsl.setSendToMib(calc.getSendToMib());
        rsl.setSendToCourt(calc.getSendToCourt());

        rsl.setTotalAmount(calc.getTotalAmount());
        rsl.setExecuted(calc.getExecuted());
        rsl.setDiscount(calc.getDiscount());
        rsl.setAwaitExecution(calc.getAwaitExecution() - calc.getSendToMib());

        return rsl;
    }

    private List<Long> makeMoney(DashboardPieChartDTO dto) {

        List<Long> rsl = new LinkedList<>();

        rsl.add(dto.getExecuted());
        rsl.add(dto.getAwaitExecution());
        rsl.add(dto.getDiscount());
        rsl.add(dto.getSendToMib());
        rsl.add(dto.getSendToCourt());

        return rsl;
    }

    private DashboardBarChartDTO getBarChart(DashboardCalculatedPieChartDTO calc) {

        DashboardBarChartDTO rsl = new DashboardBarChartDTO();

        //rsl.setTotalAmount(calc.getTotalAmountByMonth());
        rsl.setExecuted(calc.getExecutedByMonth());
        rsl.setAwaitExecution(calc.getAwaitExecutionByMonth());
        rsl.setDiscount(calc.getDiscountByMonth());
        rsl.setSendToMib(calc.getSendToMibByMonth());
        rsl.setSendToCourt(calc.getSendToCourtByMonth());

        return rsl;
    }

    private DashboardCalculatedPieChartDTO getDataAndCalculatePieChart(LocalDateTime dateFrom,
                                                                       LocalDateTime dateTo,
                                                                       User user,
                                                                       DashboardFilterDTO filter) {

        DownsideData data = getDashboardDownsideData(dateFrom, dateTo, user, filter);
        return calculatePieChart(data.penalty, data.mib, data.court);
    }

    private DashboardCalculatedPieChartDTO calculatePieChart(List<PenaltyChartProjection> penalty,
                                                             List<AmountByMonthProjection> mib,
                                                             List<AmountByMonthProjection> court) {

        DashboardCalculatedPieChartDTO rsl = new DashboardCalculatedPieChartDTO();

        penalty.forEach((p) -> {

            boolean paymentWithDiscount = Optional.ofNullable(p.getIsDiscountPayDate()).orElse(false);

            long balance = 0L;
            long discount = 0L;
            long amount = Optional.ofNullable(p.getAmount()).orElse(0L);
            long paidAmount = Optional.ofNullable(p.getPaidAmount()).orElse(0L);
            long discountAmount = Optional.ofNullable(p.getDiscountAmount()).orElse(0L);
            if (p.getIsDiscount() && paymentWithDiscount) {
                balance = discountAmount - paidAmount;
                if (p.getAmount() > p.getPaidAmount()) {
                    discount = amount - discountAmount;
                }
            } else {
                balance = amount - paidAmount;
            }
            balance = Math.max(balance, 0L);

            rsl.addTotalAmount(amount, p.getLocalMonthDate());
            rsl.addExecuted(paidAmount, p.getLocalMonthDate());
            rsl.addAwaitExecution(balance, p.getLocalMonthDate());
            rsl.addDiscount(discount, p.getLocalMonthDate());
        });

        mib.forEach((m) -> {
            rsl.addSendToMib(m.getAmount(), m.getLocalMonthDate());
        });

        court.forEach((m) -> {
            rsl.addSendToCourt(m.getAmount(), m.getLocalMonthDate());
        });

        rsl.calculateTotals();

        return rsl;
    }

    private class DownsideData {

        List<PenaltyChartProjection> penalty;
        List<AmountByMonthProjection> mib;
        List<AmountByMonthProjection> court;
    }

    private DownsideData getDashboardDownsideData(LocalDateTime dateFrom,
                                                  LocalDateTime dateTo,
                                                  User user,
                                                  DashboardFilterDTO filter) {

        DownsideData rsl = new DownsideData();

        rsl.penalty = dashboardRepository.penaltyChart(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));
        rsl.mib = dashboardRepository.mibAmountByMonth(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));
        rsl.court = dashboardRepository.courtAmountByMonth(dateFrom, dateTo, user.getId(), p(filter.getOrganId()), p(filter.getRegionId()), p(filter.getDistrictId()));

        return rsl;
    }

    private LocalDateTime from(LocalDate date) {
        return date.atStartOfDay();
    }

    private LocalDateTime to(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }

    private long p(Long value) {
        return Optional.ofNullable(value).orElse(0L);
    }
}
