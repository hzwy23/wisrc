package com.wisrc.replenishment.webapp.vo.waybill;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "运单号缺少相关信息")
public class WaybillIfInfoVO {
    @ApiModelProperty(value = "少物流信息数量")
    private int logisticsInfoNum;
    @ApiModelProperty(value = "少发货数数量")
    private int deliveryNum;
    @ApiModelProperty(value = "少报关资料数量")
    private int icustomsInfoNum;
    @ApiModelProperty(value = "少清关发票数量")
    private int clearanceNum;

    public int getLogisticsInfoNum() {
        return logisticsInfoNum;
    }

    public void setLogisticsInfoNum(int logisticsInfoNum) {
        this.logisticsInfoNum = logisticsInfoNum;
    }

    public int getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(int deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public int getIcustomsInfoNum() {
        return icustomsInfoNum;
    }

    public void setIcustomsInfoNum(int icustomsInfoNum) {
        this.icustomsInfoNum = icustomsInfoNum;
    }

    public int getClearanceNum() {
        return clearanceNum;
    }

    public void setClearanceNum(int clearanceNum) {
        this.clearanceNum = clearanceNum;
    }
}
