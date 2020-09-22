package com.wisrc.wms.webapp.vo.ReturnVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * 流水回写实体
 * WMS->ERP
 */
public class OutEnterWaterReturnVO {
    @ApiModelProperty("指令ID")
    private String markId;
    @ApiModelProperty("明细ID")
    private int detailId;
    @ApiModelProperty("仓库标号")
    private String warehouseId;
    @ApiModelProperty("分仓编号")
    private String subWarehouseId;
    @ApiModelProperty("库存sku")
    private String skuId;
    @ApiModelProperty("产品名称")
    private String skuName;
    @ApiModelProperty("fnSku")
    private String fnSkuId;
    @ApiModelProperty("库位编码")
    private String warehousePosition;
    @ApiModelProperty("生产批次")
    private String enterBatch;
    @ApiModelProperty("入库批次")
    private String productionBatch;
    @ApiModelProperty("总库存可用数")
    private Integer enableSumStockNum;
    @ApiModelProperty("总库存数量")
    private Integer sumStockNum;
    @ApiModelProperty("操作前数目")
    private Integer changeAgoNum;
    @ApiModelProperty("操作数目")
    private Integer changeNum;
    @ApiModelProperty("操作后数目")
    private Integer changeLaterNum;
    @ApiModelProperty("出入类型")
    private Integer outEnterType;
    @ApiModelProperty("来源单据")
    private String sourceId;
    @ApiModelProperty("单据类型")
    private String documentType;
    @ApiModelProperty("操作时间")
    private String createTime;
    @ApiModelProperty("操作人")
    private String createUser;
    @ApiModelProperty("用户编号")
    private String userCode;
    @ApiModelProperty("备注")
    private String remark;

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
    }

    public String getEnterBatch() {
        return enterBatch;
    }

    public void setEnterBatch(String enterBatch) {
        this.enterBatch = enterBatch;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public Integer getEnableSumStockNum() {
        return enableSumStockNum;
    }

    public void setEnableSumStockNum(Integer enableSumStockNum) {
        this.enableSumStockNum = enableSumStockNum;
    }

    public Integer getSumStockNum() {
        return sumStockNum;
    }

    public void setSumStockNum(Integer sumStockNum) {
        this.sumStockNum = sumStockNum;
    }

    public Integer getChangeAgoNum() {
        return changeAgoNum;
    }

    public void setChangeAgoNum(Integer changeAgoNum) {
        this.changeAgoNum = changeAgoNum;
    }

    public Integer getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Integer changeNum) {
        this.changeNum = changeNum;
    }

    public Integer getChangeLaterNum() {
        return changeLaterNum;
    }

    public void setChangeLaterNum(Integer changeLaterNum) {
        this.changeLaterNum = changeLaterNum;
    }

    public Integer getOutEnterType() {
        return outEnterType;
    }

    public void setOutEnterType(Integer outEnterType) {
        this.outEnterType = outEnterType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
