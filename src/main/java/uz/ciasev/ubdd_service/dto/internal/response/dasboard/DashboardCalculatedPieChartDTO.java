package uz.ciasev.ubdd_service.dto.internal.response.dasboard;

import lombok.Data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Data
public class DashboardCalculatedPieChartDTO {

    private Long totalAmount;
    private Long executed;
    private Long awaitExecution;
    private Long discount;
    private Long sendToMib;
    private Long sendToCourt;

    private Long[] totalAmountByMonth = new Long[12];
    private Long[] executedByMonth = new Long[12];
    private Long[] awaitExecutionByMonth = new Long[12];
    private Long[] discountByMonth = new Long[12];
    private Long[] sendToMibByMonth = new Long[12];
    private Long[] sendToCourtByMonth = new Long[12];

    public DashboardCalculatedPieChartDTO() {

        Arrays.fill(this.totalAmountByMonth, 0L);
        Arrays.fill(this.executedByMonth, 0L);
        Arrays.fill(this.awaitExecutionByMonth, 0L);
        Arrays.fill(this.discountByMonth, 0L);
        Arrays.fill(this.sendToMibByMonth, 0L);
        Arrays.fill(this.sendToCourtByMonth, 0L);
    }

    private int getIdx(LocalDate date) {
        return date.getMonth().getValue() - 1;
    }

    public void addTotalAmount(Long amount, LocalDate date) {
        this.totalAmountByMonth[getIdx(date)] += Optional.ofNullable(amount).orElse(0L);
    }

    public void addExecuted(Long amount, LocalDate date) {
        this.executedByMonth[getIdx(date)] += Optional.ofNullable(amount).orElse(0L);
    }

    public void addAwaitExecution(Long amount, LocalDate date) {
        this.awaitExecutionByMonth[getIdx(date)] += Optional.ofNullable(amount).orElse(0L);
    }

    public void addDiscount(Long amount, LocalDate date) {
        this.discountByMonth[getIdx(date)] += Optional.ofNullable(amount).orElse(0L);
    }

    public void addSendToMib(Long amount, LocalDate date) {
        this.sendToMibByMonth[getIdx(date)] += Optional.ofNullable(amount).orElse(0L);
    }

    public void addSendToCourt(Long amount, LocalDate date) {
        this.sendToCourtByMonth[getIdx(date)] += Optional.ofNullable(amount).orElse(0L);
    }

    public void calculateTotals() {
        this.totalAmount = Arrays.stream(this.totalAmountByMonth).reduce(Long::sum).orElse(0L);
        this.executed = Arrays.stream(this.executedByMonth).reduce(Long::sum).orElse(0L);
        this.awaitExecution = Arrays.stream(this.awaitExecutionByMonth).reduce(Long::sum).orElse(0L);
        this.discount = Arrays.stream(this.discountByMonth).reduce(Long::sum).orElse(0L);
        this.sendToMib = Arrays.stream(this.sendToMibByMonth).reduce(Long::sum).orElse(0L);
        this.sendToCourt = Arrays.stream(this.sendToCourtByMonth).reduce(Long::sum).orElse(0L);
    }
}
