package com.wisrc.purchase.webapp.vo.swagger;

import com.wisrc.purchase.webapp.vo.AddDetailsProdictAllVO;
import com.wisrc.purchase.webapp.vo.OrderBasisInfoVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AddBasisInfoModel {
    @ApiModelProperty(value = "返回状态吗", position = 1)
    private int code;

    @ApiModelProperty(value = "返回消息", position = 2)
    private int msg;

    @ApiModelProperty(value = "订单基本信息板块", position = 3)
    private OrderBasisInfoVO orderBasisInfoVO;

    @ApiModelProperty(value = "订单产品信息板块", position = 4)
    private List<AddDetailsProdictAllVO> addDetailsProdictAllVOList;

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

    public OrderBasisInfoVO getOrderBasisInfoVO() {
        return orderBasisInfoVO;
    }

    public void setOrderBasisInfoVO(OrderBasisInfoVO orderBasisInfoVO) {
        this.orderBasisInfoVO = orderBasisInfoVO;
    }

    public List<AddDetailsProdictAllVO> getAddDetailsProdictAllVOList() {
        return addDetailsProdictAllVOList;
    }

    public void setAddDetailsProdictAllVOList(List<AddDetailsProdictAllVO> addDetailsProdictAllVOList) {
        this.addDetailsProdictAllVOList = addDetailsProdictAllVOList;
    }
}
