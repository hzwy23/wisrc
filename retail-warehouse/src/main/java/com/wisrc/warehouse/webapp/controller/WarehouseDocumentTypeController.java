package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.entity.WarehouseDocumentTypeEntity;
import com.wisrc.warehouse.webapp.service.WarehouseDocumentTypeService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.swagger.DocumentResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(tags = "出入库单据类型")
@RequestMapping(value = "/warehouse")
public class WarehouseDocumentTypeController {
    @Autowired
    private WarehouseDocumentTypeService warehouseDocumentTypeService;

    @RequestMapping(value = "/document/type", method = RequestMethod.GET)
    @ApiOperation(value = "单据类型", response = DocumentResponseModel.class)
    @ResponseBody
    public Result getWarehouseDocumentType() {
        List<WarehouseDocumentTypeEntity> warehouseDocumentTypeEntityList = warehouseDocumentTypeService.findAll();
        return Result.success(warehouseDocumentTypeEntityList);
    }

}
