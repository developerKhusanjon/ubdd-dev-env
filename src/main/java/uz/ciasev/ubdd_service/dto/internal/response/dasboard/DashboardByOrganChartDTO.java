package uz.ciasev.ubdd_service.dto.internal.response.dasboard;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class DashboardByOrganChartDTO {

    private List<Long> id = new LinkedList<>();
    private List<Long> data = new LinkedList<>();
}
