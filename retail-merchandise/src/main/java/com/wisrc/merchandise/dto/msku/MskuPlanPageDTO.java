package com.wisrc.merchandise.dto.msku;

import lombok.Data;

@Data
public class MskuPlanPageDTO {
    private String salesStatus;
    private String startDate;
    private String expiryDate;
    private Integer expectedDailySales;
    private Double guidePrice;
    private String planId;
}
