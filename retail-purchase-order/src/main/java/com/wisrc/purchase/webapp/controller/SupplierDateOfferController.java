package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.entity.SupplierDateOfferEntity;
import com.wisrc.purchase.webapp.entity.TeamStatusAttrEntity;
import com.wisrc.purchase.webapp.service.SupplierDateOfferService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.vo.supplierDateOffer.SupplierDateOfferPageVo;
import com.wisrc.purchase.webapp.vo.supplierDateOffer.SupplierDateOfferVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.LinkedHashMap;

@Api(tags = "供应商交期及报价")
@RestController
@RequestMapping(value = "/purchase")
public class SupplierDateOfferController {
    @Autowired
    SupplierDateOfferService supplierDateOfferService;

    @Deprecated
    @RequestMapping(value = "/supplier/offer/info", method = RequestMethod.GET)
    @ApiOperation(value = "查询供应商交期及报价信息", notes = "供应商交期及报价列表", response = SupplierDateOfferVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "employeeId", value = "采购员ID", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierId", value = "供应商ID", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "skuId", value = "库存SKU", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "statusCd", value = "交货状态(0-全部，1-合作中，2-停止合作)", paramType = "query", dataType = "int"),
    })
    public Result findInfo(@RequestParam(value = "pageNum", required = false) String pageNum,
                           @RequestParam(value = "pageSize", required = false) String pageSize,
                           @RequestParam(value = "employeeId", required = false) String employeeId,
                           @RequestParam(value = "supplierId", required = false) String supplierId,
                           @RequestParam(value = "skuId", required = false) String skuId,
                           @RequestParam(value = "statusCd", required = false, defaultValue = "-1") int statusCd) {
        try {
            int size = Integer.valueOf(pageSize);
            int num = Integer.valueOf(pageNum);
            if (statusCd == -1) {
                statusCd = 0;
            }
            LinkedHashMap result = supplierDateOfferService.findInfo(num, size, employeeId, supplierId, skuId, statusCd);
            return Result.success(200, "分页查询成功", result);
        } catch (Exception e) {
            if (statusCd == -1) {
                statusCd = 0;
            }
            LinkedHashMap result = supplierDateOfferService.findInfo(employeeId, supplierId, skuId, statusCd);
            return Result.success(200, "不分页条件查询成功", result);
        }
    }

    @RequestMapping(value = "/supplier/offer/byid", method = RequestMethod.GET)
    @ApiOperation(value = "查看供应商交期及报价详情信息", notes = "查看供应商交期及报价详情信息", response = SupplierDateOfferVO.class)
    public Result findById(@RequestParam(value = "supplierOfferId", required = true) String supplierOfferId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            SupplierDateOfferVO infoById = supplierDateOfferService.findInfoById(supplierOfferId);
            return Result.success(200, "成功", infoById);
        } catch (Exception e) {
            return Result.failure(390, "异常", "");
        }
    }

    @RequestMapping(value = "/supplier/offer/change/status", method = RequestMethod.PUT)
    @ApiOperation(value = "变更状态(ID数组)", notes = "变更状态(ID数组))")
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result upStatus(String[] idList, @RequestParam(value = "statusCd", required = false, defaultValue = "2") int statusCd, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            SupplierDateOfferEntity ele = new SupplierDateOfferEntity();
            ele.setModifyUser(userId);
            ele.setModifyTime(Time.getCurrentTimestamp());
            ele.setStatusCd(statusCd);
            for (String id : idList) {
                ele.setSupplierOfferId(id);
                supplierDateOfferService.upStatus(ele);
            }
            return Result.success(200, "变更状态成功", "");
        } catch (Exception e) {
            return Result.failure(390, "变更状态异常", "");
        }
    }

    @RequestMapping(value = "/supplier/offer/change/days", method = RequestMethod.PUT)
    @ApiOperation(value = "变更运输时间(ID数组)", notes = "变更运输时间(ID数组))")
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result upDays(String[] idList, @RequestParam(value = "haulageDays", required = true) int haulageDays, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            SupplierDateOfferEntity ele = new SupplierDateOfferEntity();
            ele.setModifyUser(userId);
            ele.setModifyTime(Time.getCurrentTimestamp());
            ele.setHaulageDays(haulageDays);
            for (String id : idList) {
                ele.setSupplierOfferId(id);
                supplierDateOfferService.upDate(ele);
            }
            return Result.success(200, "变更运输时间成功", "");
        } catch (Exception e) {
            return Result.failure(390, "变更运输时间异常", "");
        }
    }

    @RequestMapping(value = "/supplier/offer/change/user", method = RequestMethod.PUT)
    @ApiOperation(value = "变更采购员(ID数组)", notes = "变更采购员(ID数组))")
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result upDays(String[] idList, @RequestParam(value = "employeeId", required = true) String employeeId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            SupplierDateOfferEntity ele = new SupplierDateOfferEntity();
            ele.setModifyUser(userId);
            ele.setModifyTime(Time.getCurrentTimestamp());
            ele.setEmployeeId(employeeId);
            for (String id : idList) {
                ele.setSupplierOfferId(id);
                supplierDateOfferService.upEmployee(ele);
            }
            return Result.success(200, "变更采购员成功", "");
        } catch (Exception e) {
            return Result.failure(390, "变更采购员异常", "");
        }
    }

    @RequestMapping(value = "/supplier/offer/delete", method = RequestMethod.PUT)
    @ApiOperation(value = "批量删除信息(ID数组)", notes = "批量删除信息(ID数组)")
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result upDays(String[] idList, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            SupplierDateOfferEntity ele = new SupplierDateOfferEntity();
            ele.setModifyUser(userId);
            ele.setModifyTime(Time.getCurrentTimestamp());
            ele.setDeleteStatus(1);
            for (String id : idList) {
                ele.setSupplierOfferId(id);
                supplierDateOfferService.delInfo(ele);
            }
            return Result.success(200, "批量删除成功", "");
        } catch (Exception e) {
            return Result.failure(390, "批量删除异常", "");
        }
    }

    @RequestMapping(value = "/supplier/offer/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增/编辑供应商交期及报价", notes = "新增/编辑供应商交期及报价", response = SupplierDateOfferVO.class)
    public Result addInfo(@RequestBody SupplierDateOfferVO vo, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(400, "供应商交期及报价新增失败", result.getAllErrors().get(0).getDefaultMessage());
        }
        String supplierOfferId = vo.getSupplierOfferId();
        if (supplierOfferId == null || supplierOfferId.trim().isEmpty()) {
            if (supplierDateOfferService.findRepeat(vo.getSupplierId(), vo.getSkuId()) > 0) {
                return Result.failure(390, "供应商交期及报价限制库存sku和供应商不能同时重复,已经存在", "");
            }
            try {
                SupplierDateOfferEntity ele = new SupplierDateOfferEntity();
                BeanUtils.copyProperties(vo, ele);
                ele.setCreateTime(Time.getCurrentTimestamp());
                ele.setModifyTime(Time.getCurrentTimestamp());
                ele.setCreateUser(userId);
                ele.setModifyUser(userId);
                ele.setDeleteStatus(0);
                ele.setStatusCd(1);
                supplierDateOfferService.insertInfo(ele);
                return Result.success(200, "供应商交期及报价新增成功", "");
            } catch (Exception e) {
                return Result.failure(390, "供应商交期及报价新增失败", "");
            }
        } else {
            try {
                SupplierDateOfferEntity ele = new SupplierDateOfferEntity();
                BeanUtils.copyProperties(vo, ele);
                ele.setModifyTime(Time.getCurrentTimestamp());
                ele.setModifyUser(userId);
                supplierDateOfferService.updateInfo(ele);
                return Result.success(200, "供应商交期及报价修改成功", "");
            } catch (Exception e) {
                return Result.failure(390, "供应商交期及报价修改失败", "");
            }
        }
    }

    /**
     * 根据供应商ID查询上一次选择供应商时候的运输时间-国内
     *
     * @return
     */
    @RequestMapping(value = "/supplier/days", method = RequestMethod.GET)
    @ApiOperation(value = "根据供应商ID查询上一次选择供应商时候的运输时间-国内", notes = "根据供应商ID查询上一次选择供应商时候的运输时间-国内")
    public Result findDays(@RequestParam(value = "supplierId", required = true) String supplierId) {
        SupplierDateOfferEntity infoBySupplier = supplierDateOfferService.findInfoBySupplier(supplierId);
        if (infoBySupplier != null) {
            return Result.success(200, "成功", infoBySupplier.getHaulageDays());
        } else {
            return Result.success(200, "成功,上次无此供应商选择", "");
        }
    }

    /**
     * 供应商合作状态码表
     *
     * @return
     */
    @RequestMapping(value = "/supplier/team/status/attr", method = RequestMethod.GET)
    @ApiOperation(value = "供应商合作状态码表", notes = "供应商合作状态码表", response = TeamStatusAttrEntity.class)
    public Result findStatusAttr() {
        return Result.success(200, "成功", supplierDateOfferService.findTeamAttr());
    }


    @ApiOperation(value = "获取供应商报价操作记录", notes = "根据报价单号，获取这个报价单号的历史操作记录")
    @RequestMapping(value = "/supplier/offer/handle/logs", method = RequestMethod.GET)
    public Result findLogsById(@RequestParam("supplierOfferId") String supplierOfferId) {
        return Result.success(supplierDateOfferService.findLogsById(supplierOfferId));
    }

    @RequestMapping(value = "/supplier/offer", method = RequestMethod.GET)
    @ApiOperation(value = "查询供应商交期及报价信息", notes = "供应商交期及报价列表", response = SupplierDateOfferVO.class)
    public Result supplierOfferPage(@Valid SupplierDateOfferPageVo supplierDateOfferPageVo) {
        if (supplierDateOfferPageVo.getStatusCd() == -1) {
            supplierDateOfferPageVo.setStatusCd(0);
        }
        return supplierDateOfferService.supplierOfferPage(supplierDateOfferPageVo);
    }
}
