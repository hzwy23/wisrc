package com.wisrc.replenishment.webapp.query.logisticBill;

import lombok.Data;

import java.util.List;

@Data
public class FindDetailQuery {
    private List<HistoryQuery> historyKey;
}
