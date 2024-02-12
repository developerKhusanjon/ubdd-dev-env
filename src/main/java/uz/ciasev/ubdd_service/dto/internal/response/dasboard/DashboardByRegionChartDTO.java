package uz.ciasev.ubdd_service.dto.internal.response.dasboard;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class DashboardByRegionChartDTO {

    private List<String> labelRu = new LinkedList<>();
    private List<String> labelKir = new LinkedList<>();
    private List<String> labelLat = new LinkedList<>();
    private List<Long> data = new LinkedList<>();
}
