package com.wisrc.sales.webapp.enity;

import com.wisrc.sales.webapp.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
public class EstimateEnity {
    @ApiModelProperty(value = "销售预估主键id", required = false, hidden = true)
    private String estimateId;
    @ApiModelProperty(value = "店铺id")
    @NotEmpty(message = "店铺id不能为空")
    private String shopId;
    @ApiModelProperty(value = "msku编码")
    @NotEmpty(message = "msku编码不能为空")
    private String mskuId;
    @ApiModelProperty(value = "商品id", required = false, hidden = true)
    private String commodityId;
    @ApiModelProperty(value = "创建人", required = false, hidden = true)
    private String createUser;
    @ApiModelProperty(value = "创建时间", required = false, hidden = true)
    private String createTime;
    @ApiModelProperty(value = "更新时间", required = false, hidden = true)
    private String modifyTime;
    @ApiModelProperty(value = "更新人", required = false, hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "负责人Id", required = false, hidden = true)
    private String chargeEmployeeId;
    @ApiModelProperty(value = "类目主管人Id", required = false, hidden = true)
    private String directorEmployeeId;
    @ApiModelProperty(value = "sku编码", required = false, hidden = true)
    private String skuId;
    @ApiModelProperty(value = "产品名称", required = false, hidden = true)
    private String productName;
    @ApiModelProperty(value = "更新人员工Id", required = false, hidden = true)
    private String updateEmployeeId;
    // 生效日期
    private Timestamp effectiveDate;
    // 时效日期
    private Timestamp expirationDate;
    @ApiModelProperty(value = "销售预估详细列表", required = true)
    @NotEmpty(message = "销售预估详细不能为空")
    @Valid
    private List<EstimateDetailEnity> estimateDetailList;

    @ApiModelProperty(value = "更新标识", required = false, hidden = true)
    private Integer updateFlag;

    @ApiModelProperty(value = "更新标识", required = false, hidden = true)
    private String shopSellerId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public List<EstimateDetailEnity> getEstimateDetailList() {
        return estimateDetailList;
    }

    public void setEstimateDetailList(List<EstimateDetailEnity> estimateDetailList) {
        this.estimateDetailList = estimateDetailList;
    }

    public String getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getChargeEmployeeId() {
        return chargeEmployeeId;
    }

    public void setChargeEmployeeId(String chargeEmployeeId) {
        this.chargeEmployeeId = chargeEmployeeId;
    }

    public String getDirectorEmployeeId() {
        return directorEmployeeId;
    }

    public void setDirectorEmployeeId(String directorEmployeeId) {
        this.directorEmployeeId = directorEmployeeId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreateTime() {
        Date date = DateUtil.convertStrToDate(createTime, DateUtil.DATETIME_FORMAT);
        return DateUtil.convertDateToStr(date, DateUtil.DATETIME_FORMAT);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        Date date = DateUtil.convertStrToDate(modifyTime, DateUtil.DATETIME_FORMAT);
        return DateUtil.convertDateToStr(date, DateUtil.DATETIME_FORMAT);
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUpdateEmployeeId() {
        return updateEmployeeId;
    }

    public void setUpdateEmployeeId(String updateEmployeeId) {
        this.updateEmployeeId = updateEmployeeId;
    }
}
