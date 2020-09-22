package com.wisrc.purchase.webapp.vo.purchaseReturn.show;

import com.wisrc.purchase.webapp.entity.ProductMachineInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class GatherPurchaseReturnVO {
    private String returnBill;
    private String createDateStr;

    private String supplierId;
    private String supplierName;

    private String employeeId;
    private String employeeName;

    private String warehouseId;
    private String warehouseName;

    private String orderId;
    private String remark;

    private String uuid;
    private String skuId;
    private String skuNameZh;

    private String returnQuantity;
    private String spareQuantity;
    private String batchNumber;
    private String unitPriceWithoutTax;
    private String amountWithoutTax;
    private String taxRate;
    private String unitPriceWithTax;
    private String amountWithTax;
    @ApiModelProperty(value = "是否需要包材")
    private String isNeedPacking;
    private String packWarehouseName;
    @ApiModelProperty(value = "包材明细")
    private List<ProductMachineInfoEntity> productMachineInfoEntityList;

    public void setCreateDateStr(Date date) {
        if (date == null) {
            this.createDateStr = "";
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String str = df.format(date);
            this.createDateStr = str;
        }
        this.createDateStr = createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
}
