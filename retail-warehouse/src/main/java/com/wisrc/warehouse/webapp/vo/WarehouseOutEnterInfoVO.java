package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

@ApiModel(value = "出入库基本信息")
public class WarehouseOutEnterInfoVO {
    @ApiModelProperty(value = "uuid")
    private String uuid;
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "来源ID")
    private String sourceId;
    @ApiModelProperty(value = "skuId")
    private String skuId;
    @ApiModelProperty(value = "fnSkuId")
    private String fnSkuId;
    @ApiModelProperty(value = "生产批次")
    private String productionBatch;
    @ApiModelProperty(value = "入库批次")
    private String enterBatch;
    @ApiModelProperty(value = "变更前数量")
    private String change_ago_num;
    @ApiModelProperty(value = "出入库类型")
    private String out_enter_type;
    @ApiModelProperty(value = "变更数量")
    private String change_num;
    @ApiModelProperty(value = "变更后数量")
    private String change_later_num;
    @ApiModelProperty(value = "单据类型")
    private String document_type;
    @ApiModelProperty(value = "库位")
    private String warehouse_position;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "创建人")
    private String createUser;

    public static final WarehouseOutEnterInfoVO toVO(WarehouseOutEnterInfoEntity ele) {
        WarehouseOutEnterInfoVO vo = new WarehouseOutEnterInfoVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public String getEnterBatch() {
        return enterBatch;
    }

    public void setEnterBatch(String enterBatch) {
        this.enterBatch = enterBatch;
    }

    public String getChange_ago_num() {
        return change_ago_num;
    }

    public void setChange_ago_num(String change_ago_num) {
        this.change_ago_num = change_ago_num;
    }

    public String getOut_enter_type() {
        return out_enter_type;
    }

    public void setOut_enter_type(String out_enter_type) {
        this.out_enter_type = out_enter_type;
    }

    public String getChange_num() {
        return change_num;
    }

    public void setChange_num(String change_num) {
        this.change_num = change_num;
    }

    public String getChange_later_num() {
        return change_later_num;
    }

    public void setChange_later_num(String change_later_num) {
        this.change_later_num = change_later_num;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getWarehouse_position() {
        return warehouse_position;
    }

    public void setWarehouse_position(String warehouse_position) {
        this.warehouse_position = warehouse_position;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
