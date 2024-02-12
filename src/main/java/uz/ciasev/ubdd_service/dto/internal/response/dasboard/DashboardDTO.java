package uz.ciasev.ubdd_service.dto.internal.response.dasboard;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class DashboardDTO {

    private DashboardNumbersDTO numbers;

    private List<Long> lineChart;

    //private DashboardPieChartDTO pieChart;
    private List<Long> money;

    private DashboardBarChartDTO monthProtocols;

    private DashboardByOrganChartDTO organProtocols;

    private DashboardByRegionChartDTO protocolByRegions;

    private DashboardByOrganChartDTO organRepeatability;

    private List<Long> totalRepeatability;

    public DashboardDTO() {

        this.numbers = new DashboardNumbersDTO();
        this.lineChart = new ArrayList<>();
        //this.pieChart = new DashboardPieChartDTO();
        this.money = new LinkedList<>();
        this.monthProtocols = new DashboardBarChartDTO();
        this.organProtocols = new DashboardByOrganChartDTO();
        this.protocolByRegions = new DashboardByRegionChartDTO();
        this.organRepeatability = new DashboardByOrganChartDTO();
        this.totalRepeatability = new LinkedList<>();
    }
}
