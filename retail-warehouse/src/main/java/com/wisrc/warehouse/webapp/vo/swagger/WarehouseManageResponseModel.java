package com.wisrc.warehouse.webapp.vo.swagger;

import com.wisrc.warehouse.webapp.vo.WarehouseManageInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class WarehouseManageResponseModel {
    @ApiModelProperty(value = "返回状态吗", position = 1)
    private int code;

    @ApiModelProperty(value = "返回消息", position = 2)
    private int msg;

    @ApiModelProperty(value = "仓库信息表", position = 3)
    private List<WarehouseManageInfoVO> warehouseManageInfoVOList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }


    public List<WarehouseManageInfoVO> getWarehouseManageInfoVOList() {
        return warehouseManageInfoVOList;
    }

    public void setWarehouseManageInfoVOList(List<WarehouseManageInfoVO> warehouseManageInfoVOList) {
        this.warehouseManageInfoVOList = warehouseManageInfoVOList;
    }
}
