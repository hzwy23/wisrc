package com.wisrc.purchase.webapp.query.supplierOffer;

import lombok.Data;

import java.util.List;

@Data
public class SupplierOfferPageQuery {
    private String employeeId;
    private List supplierIds;
    private List skuIds;
    private Integer statusCd;
}
