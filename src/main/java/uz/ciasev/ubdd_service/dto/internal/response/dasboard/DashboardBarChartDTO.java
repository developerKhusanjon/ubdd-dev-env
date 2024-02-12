package uz.ciasev.ubdd_service.dto.internal.response.dasboard;

import lombok.Data;

import java.util.Arrays;

@Data
public class DashboardBarChartDTO {

//    private Long[] totalAmount = new Long[12];
    private Long[] executed = new Long[12];
    private Long[] awaitExecution = new Long[12];
    private Long[] discount = new Long[12];
    private Long[] sendToMib = new Long[12];
    private Long[] sendToCourt = new Long[12]; // TODO: new

    public DashboardBarChartDTO() {
//        Arrays.fill(this.totalAmount, 0L);
        Arrays.fill(this.executed, 0L);
        Arrays.fill(this.awaitExecution, 0L);
        Arrays.fill(this.discount, 0L);
        Arrays.fill(this.sendToMib, 0L);
        Arrays.fill(this.sendToCourt, 0L);
    }
}
