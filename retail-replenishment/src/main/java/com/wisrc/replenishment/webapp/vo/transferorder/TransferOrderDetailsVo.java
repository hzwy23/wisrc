package com.wisrc.replenishment.webapp.vo.transferorder;

import com.wisrc.replenishment.webapp.entity.TransferOrderDetailsEntity;
import com.wisrc.replenishment.webapp.entity.TransferOrderPackInfoEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 包含了装箱规格的调拨明细
 */
public class TransferOrderDetailsVo extends TransferOrderDetailsEntity {
    @ApiModelProperty("库存sku名称")
    private String skuName;
    @ApiModelProperty("库存sku的label标签")
    private List<String> skuLabels;
    @ApiModelProperty("产品对应的url")
    private List<String> url;
    @ApiModelProperty("调拨明细的装箱规格")
    private List<TransferOrderPackInfoEntity> packInfoEntities;

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public List<String> getSkuLabels() {
        return skuLabels;
    }

    public void setSkuLabels(List<String> skuLabels) {
        this.skuLabels = skuLabels;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public List<TransferOrderPackInfoEntity> getPackInfoEntities() {
        return packInfoEntities;
    }

    public void setPackInfoEntities(List<TransferOrderPackInfoEntity> packInfoEntities) {
        this.packInfoEntities = packInfoEntities;
    }
}
