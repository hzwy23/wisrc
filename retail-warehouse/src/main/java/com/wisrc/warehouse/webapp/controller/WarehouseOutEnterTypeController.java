package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.WarehouseOutEnterTypeService;
import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterTypeEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.OutEnterTypeVO;
import com.wisrc.warehouse.webapp.vo.swagger.OutEnterTypeResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Api(tags = "出入库类型")
@RequestMapping(value = "/warehouse")
public class WarehouseOutEnterTypeController {
    @Autowired
    private WarehouseOutEnterTypeService warehouseOutEnterTypeService;

    @RequestMapping(value = "/outenter/type", method = RequestMethod.GET)
    @ApiOperation(value = "查询出入库类型", notes = "查询出入库类型<br/>" +
            "0表示出入库数据都查询<br/>" +
            "1表示出库<br/>" +
            "2表示入库", response = OutEnterTypeResponseModel.class)
    @ResponseBody
    public Result getWarehouseOutEnterType() {
        List<WarehouseOutEnterTypeEntity> warehouseOutEnterTypeEntityList = warehouseOutEnterTypeService.getWarehouseOutEnterType();
        List<OutEnterTypeVO> result = new ArrayList<>();
        for (WarehouseOutEnterTypeEntity e : warehouseOutEnterTypeEntityList) {
            result.add(OutEnterTypeVO.toVO(e));
        }
       /* StringBuffer sb = new StringBuffer("");
        for (WarehouseOutEnterTypeEntity m : warehouseOutEnterTypeEntityList) {
            sb.append(m.toString()).append("<br/>");
        } */
        return Result.success(result);
    }

}
