package com.wisrc.warehouse.webapp.vo.swagger;

import com.wisrc.warehouse.webapp.vo.WarehouseOutEnterInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class WarehouseInfoResponseModel {
    @ApiModelProperty(value = "返回状态吗", position = 1)
    private int code;

    @ApiModelProperty(value = "返回消息", position = 2)
    private int msg;

    @ApiModelProperty(value = "数据信息", position = 3)
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @ApiModel
    class Data {
        @ApiModelProperty(value = "总共数据条数", position = 1)
        private int total;
        @ApiModelProperty(value = "页码", position = 2)
        private int pages;
        @ApiModelProperty(value = "出入库信息表", position = 3)
        private List<WarehouseOutEnterInfoVO> warehouseOutEnterInfoVOList;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<WarehouseOutEnterInfoVO> getWarehouseOutEnterInfoVOList() {
            return warehouseOutEnterInfoVOList;
        }

        public void setWarehouseOutEnterInfoVOList(List<WarehouseOutEnterInfoVO> warehouseOutEnterInfoVOList) {
            this.warehouseOutEnterInfoVOList = warehouseOutEnterInfoVOList;
        }
    }
}
