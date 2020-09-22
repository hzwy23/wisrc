package com.wisrc.order.webapp.dto.saleNvoice;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class IdsToNvoiceOutsideDto {
    private String invoiceId;
    private String statusDesc;
    private String wmsWaveNumber;
    private BigDecimal totalWeight;
    private List orderIds;
}
