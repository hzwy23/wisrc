package com.wisrc.purchase.webapp.vo.entrywarehouse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = "采购入库单详情")
public class EntryWarehouseAllVO {
    @ApiModelProperty(value = "采购入库单基本信息", position = 1)
    private EntryWarehouseVO entryWarehouseVO;
    @ApiModelProperty(value = "采购入库单产品信息", position = 2)
    @NotEmpty(message = "采购入库单产品不能为空")
    @Valid
    private List<EntryWarehouseProductVO> entryWarehouseProductVO;

    public EntryWarehouseVO getEntryWarehouseVO() {
        return entryWarehouseVO;
    }

    public void setEntryWarehouseVO(EntryWarehouseVO entryWarehouseVO) {
        this.entryWarehouseVO = entryWarehouseVO;
    }

    public List<EntryWarehouseProductVO> getEntryWarehouseProductVO() {
        return entryWarehouseProductVO;
    }

    public void setEntryWarehouseProductVO(List<EntryWarehouseProductVO> entryWarehouseProductVO) {
        this.entryWarehouseProductVO = entryWarehouseProductVO;
    }

}
