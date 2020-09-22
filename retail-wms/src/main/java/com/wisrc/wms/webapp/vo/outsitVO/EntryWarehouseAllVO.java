package com.wisrc.wms.webapp.vo.outsitVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(tags = "采购入库单详情")
public class EntryWarehouseAllVO {
    @ApiModelProperty(value = "采购入库单基本信息", position = 1)
    private EntryWarehouseVO entryWarehouseVO;
    @ApiModelProperty(value = "采购入库单产品信息", position = 2)
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
