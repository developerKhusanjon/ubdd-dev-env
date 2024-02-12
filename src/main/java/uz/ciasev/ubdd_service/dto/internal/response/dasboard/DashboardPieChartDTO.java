package uz.ciasev.ubdd_service.dto.internal.response.dasboard;

import lombok.Data;

@Data
public class DashboardPieChartDTO {

    private Long totalAmount;
    private Long executed;
    private Long awaitExecution;
    private Long discount;
    private Long sendToMib;
    private Long sendToCourt;
}
