package com.wisrc.basic.controller;

import com.wisrc.basic.entity.ShipmentEnterpriseEntity;
import com.wisrc.basic.service.ShipmentEnterpriseService;
import com.wisrc.basic.utils.Result;
import com.wisrc.basic.utils.Time;
import com.wisrc.basic.utils.Toolbox;
import com.wisrc.basic.vo.ShipmentEnterpriseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Api(tags = "物流商管理")
@RequestMapping(value = "/basic")
public class ShipmentEnterpriseController {

    @Autowired
    private ShipmentEnterpriseService shipmentEnterpriseService;

    @ApiOperation(value = "新增物流商信息", notes = "物流商ID唯一索引，每新增一条物流商信息，都会为这条数据生成一个物流商ID. <br/>" +
            "物流商ID具有唯一性，通过物流商ID，可以获取，修改物流商的详细信息<br/>" +
            "新添加的物流商状态默认为正常")
    @RequestMapping(value = "/shipment/add", method = RequestMethod.POST)
    public Result addShipment(@Validated ShipmentEnterpriseVO vo, BindingResult result,
                              @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), vo);
        }
        ShipmentEnterpriseEntity entity = toEntity(vo);
        entity.setShipmentId(Toolbox.UUIDrandom());
        entity.setCreateUser(userId);
        entity.setModifyUser(userId);
        entity.setStatusCd(1);
        entity.setCreateTime(Time.getCurrentTimestamp());
        entity.setModifyTime(Time.getCurrentTimestamp());
        try {
            shipmentEnterpriseService.add(entity);
            return Result.success("新增物流商信息成功");
        } catch (DuplicateKeyException e) {
            return Result.failure(500, "物流商名称【" + entity.getShipmentName() + "】重复", entity);
        }
    }

    @ApiOperation(value = "修改物流商状态", notes = "改变物流商状态，0：已删除，1：正常")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shipmentId", value = "物流商ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "物流商状态", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/shipment/status", method = RequestMethod.PUT)
    public Result changeStatus(@RequestParam("shipmentId") String shipmentId,
                               @RequestParam("statusCd") int statusCd,
                               @RequestHeader(value = "X-AUTH-ID", defaultValue = "default") @ApiIgnore String userId) {
        shipmentEnterpriseService.changeStatus(shipmentId, statusCd, userId);
        return Result.success("更新状态成功");
    }

    @RequestMapping(value = "/shipment/list", method = RequestMethod.GET)
    @ApiOperation(value = "分页模糊查询", notes = "根据分页和关键字实现分页查询和模糊查询，当pageSize和pageNum为0时查询所有数据<br/>" +
            "当keyword为空时，为精准查询。")
    public Result findByCond(@RequestParam(value = "pageNum", required = false) String pageNum,
                             @RequestParam(value = "pageSize", required = false) String pageSize,
                             @RequestParam(value = "statusCd", required = false) String statusCd,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            int size, num;
            if (pageNum != null && pageSize != null) {
                size = Integer.valueOf(pageSize);
                num = Integer.valueOf(pageNum);
                if (statusCd != null || (keyword != null && !keyword.trim().isEmpty())) {
                    LinkedHashMap list = shipmentEnterpriseService.findByCond(num, size, statusCd, keyword);
                    return Result.success(list);
                } else {
                    LinkedHashMap list = shipmentEnterpriseService.findByPage(num, size);
                    return Result.success(list);
                }
            } else {
                LinkedHashMap list = shipmentEnterpriseService.findAll(statusCd, keyword);
                return Result.success(list);
            }
        } catch (Exception e) {
            return Result.failure(390, "请输入正确的分页数字", e.getMessage());
        }
    }

    @RequestMapping(value = "/shipment/update", method = RequestMethod.PUT)
    @ApiOperation(value = "更新物流商信息", notes = "更新物流商信息过程，物流商ID不能编辑. 更新程序执行过程中，将会更新物流商ID对应行的其他字段值<br/>" +
            "1.如果在提交更新请求的过程中，有字段值没有设置值，则将会被设置为空<br/>" +
            "2.如果指定的物流商ID不存在，则不会更新任何数据<br/>" +
            "<strong>字段介绍</strong><br/>" +
            "statusCd：0 已删除，1 正常<br/>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shipmentId", value = "供应商ID", dataType = "String", paramType = "query", required = true)
    })
    public Result updateById(@Validated ShipmentEnterpriseVO vo, BindingResult result,
                             @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        ShipmentEnterpriseEntity entity = toEntity(vo);
        entity.setModifyUser(userId);
        entity.setModifyTime(Time.getCurrentTimestamp());
        try {
            shipmentEnterpriseService.updateById(entity);
            return Result.success("编辑物流商信息成功");
        } catch (DuplicateKeyException e) {
            return Result.failure(460, "物流商名称【" + entity.getShipmentName() + "】重复", entity);
        }
    }

    @RequestMapping(value = "/shipment/status/attr", method = RequestMethod.GET)
    @ApiOperation(value = "物流商状态码表信息", notes = "物流商状态ID(1--正常（默认），2--已删除)")
    public Result findStatusAttr(@RequestParam(value = "statusCd", required = false, defaultValue = "0") int statusCd) {
        return Result.success(shipmentEnterpriseService.findStatusAttr(statusCd));
    }

    @RequestMapping(value = "/shipment/type/attr", method = RequestMethod.GET)
    @ApiOperation(value = "物流商类型码表信息", notes = "物流商类型ID(1--官方，2--代理)")
    public Result findTypeAttr(@RequestParam(value = "shipmentType", required = false, defaultValue = "0") int shipmentType) {
        return Result.success(shipmentEnterpriseService.findTypeAttr(shipmentType));
    }

    @RequestMapping(value = "/shipment/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "物流商类型码表信息", notes = "物流商类型ID(1--官方，2--代理)")
    public Result findShipmentById(@PathVariable String id) {
        return Result.success(shipmentEnterpriseService.findShipmentById(id));
    }

    @RequestMapping(value = "/shipment/listid", method = RequestMethod.GET)
    public Result findByListId(@RequestParam("listId") String listId) {
        String[] ids = listId.split(",");
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.length; i++) {
            str = "'" + ids[i] + "'";
            if (i < ids.length - 1) {
                str += ",";
            }
            endstr += str;
        }
        try {
            List<ShipmentEnterpriseEntity> list = shipmentEnterpriseService.findByListId(endstr);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(500, e.getMessage(), listId);
        }
    }

    private ShipmentEnterpriseEntity toEntity(ShipmentEnterpriseVO vo) {
        ShipmentEnterpriseEntity entity = new ShipmentEnterpriseEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }


    @RequestMapping(value = "/shipment/list/like", method = RequestMethod.GET)
    @ApiOperation(value = "根据物流商名称和联系人模糊查询物流商信息")
    public Result findByCond(@RequestParam(value = "statusCd", required = false, defaultValue = "0") int statusCd,
                             @RequestParam(value = "shipmentName", required = false) String shipmentName,
                             @RequestParam(value = "contact", required = false) String contact) {
        try {
            LinkedHashMap list = shipmentEnterpriseService.findByName(statusCd, shipmentName, contact);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/shipment/enable", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有状态为正常的物流商信息，供需要该信息的服务调用")
    public Result findAllEnableShipment() {
        try {
            LinkedHashMap list = shipmentEnterpriseService.findAllEnableShipment();
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/shipment/code", method = RequestMethod.GET)
    @ApiOperation(value = "查询货运公司代码")
    public Result shipmentCodeList() {
        return shipmentEnterpriseService.shipmentCodeList();
    }
}