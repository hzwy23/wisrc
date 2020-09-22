package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Api(tags = "运单信息")
public class WaybillInfoEntity {
    @ApiModelProperty(value = "运单编码")
    private String waybillId;
    @ApiModelProperty(value = "运单下单时间")
    private Date waybillOrderDate;
    @ApiModelProperty(value = "物流报价单")
    private String offerId;
    @ApiModelProperty(value = "物流单号")
    private String logisticsId;
    @ApiModelProperty(value = "签收日期")
    private Date signInDate;
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "物流面单(附件地址)")
    private String logisticsSurfaceUrl;
    @ApiModelProperty(value = "检测报告(附件地址)")
    private String examiningReportUrl;
    @ApiModelProperty(value = "soNoUrl")
    private String soNoUrl;
    @ApiModelProperty(value = "运单物流状态")
    private int logisticsTypeCd;
    @ApiModelProperty(value = "备注信息")
    private String remark;
    @ApiModelProperty(value = "删除标识")
    private int deleteStatus;
    @ApiModelProperty(value = "删除时随机码")
    private String deleteRandom;
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Timestamp modifyTime;
    @ApiModelProperty(value = "修改人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "预计签收日期")
    private Date estimateDate;
    @ApiModelProperty(value = "少发货数据")
    private int isLackShipment;
    @ApiModelProperty(value = "少物流数据")
    private int isLackLogistics;
    @ApiModelProperty(value = "少报关信息")
    private int isLackCustomsDeclare;
    @ApiModelProperty(value = "少清关信息")
    private int isLackCustomsClearance;
    @ApiModelProperty("随机数")
    private String randomValue;

    @ApiModelProperty(value = "补货数量")
    private int replenishmentNumber;
    @ApiModelProperty(value = "发货数量")
    private int deliveryNumber;
    @ApiModelProperty(value = "签收数量")
    private int signNumber;
    @ApiModelProperty(value = "亚马逊收货仓地址")
    private String amazonWarehouseAddress;
    @ApiModelProperty(value = "亚马逊收货仓邮编")
    private String amazonWarehouseZipcode;
    @ApiModelProperty("当这个单子是调拨单对应的运单时，该字段存收货仓地址")
    private String receiveAddr;
    @ApiModelProperty(value = "收货仓编码")
    private String warehouseCode;
    @ApiModelProperty(value = "货运公司代号")
    private String logisticsTrackUrl;
    @ApiModelProperty(value = "货运公司代号编码")
    private Integer codeCd;

    @Override
    public String toString() {
        return "WaybillInfoEntity{" +
                "waybillId='" + waybillId + '\'' +
                ", waybillOrderDate=" + waybillOrderDate +
                ", offerId='" + offerId + '\'' +
                ", logisticsId='" + logisticsId + '\'' +
                ", signInDate=" + signInDate +
                ", warehouseId='" + warehouseId + '\'' +
                ", logisticsSurfaceUrl='" + logisticsSurfaceUrl + '\'' +
                ", examiningReportUrl='" + examiningReportUrl + '\'' +
                ", logisticsTypeCd=" + logisticsTypeCd +
                ", remark='" + remark + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", deleteRandom='" + deleteRandom + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", estimateDate=" + estimateDate +
                ", isLackShipment=" + isLackShipment +
                ", isLackLogistics=" + isLackLogistics +
                ", isLackCustomsDeclare=" + isLackCustomsDeclare +
                ", isLackCustomsClearance=" + isLackCustomsClearance +
                '}';
    }
}
