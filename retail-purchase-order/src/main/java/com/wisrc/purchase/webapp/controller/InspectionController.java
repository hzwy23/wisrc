package com.wisrc.purchase.webapp.controller;


import com.google.gson.Gson;
import com.wisrc.purchase.webapp.dto.inspection.InspectionDto;
import com.wisrc.purchase.webapp.entity.ArrivalProductDetailsInfoEntity;
import com.wisrc.purchase.webapp.service.PurchaseEmployeeService;
import com.wisrc.purchase.webapp.service.InspectionService;
import com.wisrc.purchase.webapp.service.SupplierService;
import com.wisrc.purchase.webapp.service.externalService.ProductService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.inspection.*;
import com.wisrc.purchase.webapp.vo.returnVO.ArrivalBillReturnVO;
import com.wisrc.purchase.webapp.vo.swagger.GetInspectionModel;
import com.wisrc.purchase.webapp.vo.swagger.InspectionPageModel;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/purchase/arrival")
@RestController
@Api(value = "/arrival", tags = "到货通知信息管理")
public class InspectionController {
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private PurchaseEmployeeService purchaseEmployeeService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "保存到货通知单信息", notes = "录入到货通知单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-ID", value = "当前用户", paramType = "header", dataType = "String", required = true, defaultValue = "")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveInspection(@Valid @RequestBody InspectionSaveVo inspectionSaveVo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(423, errorList.get(0).getDefaultMessage(), inspectionSaveVo);
        }
        Result result = check(inspectionSaveVo);
        if (result.getCode() != 200) {
            return result;
        }
        //检查到货通知单 提货数量、提备品数是否至少一项大于0
        List<InspectionProductSaveVo> storeSkus = inspectionSaveVo.getStoreSkuGroup();
        if (storeSkus != null) {
            StringBuilder message = new StringBuilder("到货通知单产品");
            for (int i = 0; i < storeSkus.size(); i++) {
                InspectionProductSaveVo inspectionProductEditVo = storeSkus.get(i);
                if (inspectionProductEditVo.getDeliveryQuantity() + inspectionProductEditVo.getDeliverySpareQuantity() <= 0) {
                    message.append("第").append(i + 1).append("项").append("、");
                }
            }
            if (message.length() > 8) {
                message = message.deleteCharAt(message.length() - 1);
                message.append("提货数量和提备品数至少需要一项大于0");
                return new Result(400, message.toString(), null);
            }
        }

        // 校验参数
        InspectionDto inspectionDto = new InspectionDto();
        BeanUtils.copyProperties(inspectionSaveVo, inspectionDto);


        inspectionDto.setPurchaseOrderId(inspectionSaveVo.getOrderId());
        inspectionDto.setHaulageDays(inspectionSaveVo.getHaulageTime());
        inspectionDto.setApplyDate(new java.sql.Date(inspectionSaveVo.getApplyDate().getTime()));
        if (inspectionSaveVo.getFreight() != null && inspectionSaveVo.getFreight() != "") {
            inspectionDto.setFreight(new BigDecimal(inspectionSaveVo.getFreight()));
        }

        if (inspectionSaveVo.getExpectArrivalTime() != null) {
            inspectionDto.setEstimateArrivalDate(new java.sql.Date(inspectionSaveVo.getExpectArrivalTime().getTime()));
        }

        List<ArrivalProductDetailsInfoEntity> storeSkuGroup = new ArrayList<>();
        Double totalFreight = null;
        for (InspectionProductSaveVo inspectionProduct : inspectionSaveVo.getStoreSkuGroup()) {
            ArrivalProductDetailsInfoEntity arrivalProductDetails = new ArrivalProductDetailsInfoEntity();
            BeanUtils.copyProperties(inspectionProduct, arrivalProductDetails);
            if (inspectionProduct.getFreight() != null) {
                arrivalProductDetails.setFreight(new BigDecimal(inspectionProduct.getFreight()));
                if (totalFreight == null) {
                    totalFreight = (double) 0;
                }
                totalFreight += arrivalProductDetails.getFreight().doubleValue();
            }
            arrivalProductDetails.setDeleteStatus(0);
            storeSkuGroup.add(arrivalProductDetails);
        }
       /* if (inspectionDto.getFreight() != null || totalFreight != null) {
            if (inspectionDto.getFreight() == null || totalFreight != inspectionDto.getFreight().doubleValue()) {
                return Result.success(423, "sku运费合计不等于总运费", inspectionDto);
            }
        }*/

        inspectionDto.setStoreSkuGroup(storeSkuGroup);
        try {
            return inspectionService.saveInspection(inspectionDto, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success(423, "新增到货通知单失败，请联系管理员", inspectionDto);
        }
    }

    private Result check(InspectionSaveVo inspectionSaveVo) {
        List<Map> mapList = new ArrayList<>();
        List<InspectionProductSaveVo> storeSkuGroup = inspectionSaveVo.getStoreSkuGroup();
        for (InspectionProductSaveVo inspectionProductSaveVo : storeSkuGroup) {
            String skuId = inspectionProductSaveVo.getSkuId();
            Integer deliverNum = inspectionProductSaveVo.getDeliveryQuantity();
            if (deliverNum == null) {
                deliverNum = 0;
            }
            Integer deliverySpareQuantity = inspectionProductSaveVo.getDeliverySpareQuantity();
            if (deliverySpareQuantity == null) {
                deliverySpareQuantity = 0;
            }
            Map map = new HashMap();
            map.put("skuId", skuId);
            map.put("number", deliverNum + deliverySpareQuantity);
            map.put("warehouseId", inspectionSaveVo.getPackWarehouseId());
            mapList.add(map);
        }
        Gson gson = new Gson();
        Result result = productService.checkWarehouseNum(gson.toJson(mapList));
        if (result.getCode() != 200) {
            return result;
        }
        return Result.success();
    }

    @ApiOperation(value = "编辑到货通知单信息", notes = "")
    @RequestMapping(value = "/{arrivalId}", method = RequestMethod.PUT)
    public Result editInspection(@Valid @RequestBody InspectionEditVo inspectionEditVo,
                                 BindingResult bindingResult,
                                 @PathVariable("arrivalId") String arrivalId,
                                 @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        //检查到货通知单 提货数量、提备品数是否至少一项大于0
        List<InspectionProductEditVo> storeSkus = inspectionEditVo.getStoreSkuGroup();
        if (storeSkus != null) {
            StringBuilder message = new StringBuilder("到货通知单产品");
            for (int i = 0; i < storeSkus.size(); i++) {
                InspectionProductEditVo inspectionProductEditVo = storeSkus.get(i);
                if (inspectionProductEditVo.getDeliveryQuantity() + inspectionProductEditVo.getDeliverySpareQuantity() <= 0) {
                    message.append("第").append(i + 1).append("项").append("、");
                }
            }
            if (message.length() > 8) {
                message = message.deleteCharAt(message.length() - 1);
                message.append("提货数量和提备品数至少需要一项大于0");
                return new Result(400, message.toString(), null);
            }
        }

        InspectionDto inspectionVo = new InspectionDto();
        BeanUtils.copyProperties(inspectionEditVo, inspectionVo);
        inspectionVo.setPurchaseOrderId(inspectionEditVo.getOrderId());
        inspectionVo.setHaulageDays(inspectionEditVo.getHaulageTime());
        inspectionVo.setApplyDate(new java.sql.Date(inspectionEditVo.getApplyDate().getTime()));

        // add by zhanwei_huang, 运费为非必输项，可以为空
        if (inspectionEditVo.getFreight() != null && !inspectionEditVo.getFreight().trim().isEmpty()) {
            inspectionVo.setFreight(new BigDecimal(inspectionEditVo.getFreight()));
        }

        inspectionVo.setEstimateArrivalDate(new java.sql.Date(inspectionEditVo.getExpectArrivalTime().getTime()));
        List storeSkuGroup = new ArrayList();
        Double totalFreight = null;
        for (InspectionProductEditVo editVo : inspectionEditVo.getStoreSkuGroup()) {
            ArrivalProductDetailsInfoEntity arrivalProductDetailsInfo = new ArrivalProductDetailsInfoEntity();
            BeanUtils.copyProperties(editVo, arrivalProductDetailsInfo);
            if (editVo.getFreight() != null) {
                arrivalProductDetailsInfo.setFreight(new BigDecimal(editVo.getFreight()));
                if (totalFreight == null) {
                    totalFreight = (double) 0;
                }
                totalFreight += arrivalProductDetailsInfo.getFreight().doubleValue();
            }
            storeSkuGroup.add(arrivalProductDetailsInfo);
        }
        if (inspectionVo.getFreight() != null || totalFreight != null) {
            if (inspectionVo.getFreight() == null || totalFreight != inspectionVo.getFreight().doubleValue()) {
                return Result.success(423, "sku运费合计不等于总运费", inspectionVo);
            }
        }
        inspectionVo.setStoreSkuGroup(storeSkuGroup);
        return inspectionService.editInspection(inspectionVo, arrivalId, userId);
    }

    @Deprecated
    @ApiOperation(value = "删除到货通知单信息",
            notes = "")
    @RequestMapping(value = "/product/delete", method = RequestMethod.PUT)
    public Result deleteInspectionProduct(@RequestBody DeleteInspectionProduct deleteInspectionProduct) {
        List inspectionProductIds = deleteInspectionProduct.getArrivalProductId();
        return inspectionService.deleteInspectionProductDetails(inspectionProductIds);
    }

    @ApiOperation(value = "删除到货通知单信息",
            notes = "")
    @RequestMapping(value = "/product/delete/two", method = RequestMethod.PUT)
    public Result deleteArrival(@RequestBody ArrivalDeleteVo arrivalDeleteVo) {
        List arrivalIds = arrivalDeleteVo.getArrivalId();
        return inspectionService.deleteArrival(arrivalIds);
    }

    @Deprecated
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = InspectionPageModel.class)
    })
    @ApiOperation(value = "到货通知单信息列表")
    @RequestMapping(method = RequestMethod.GET)
    public Result inspectionList(@Valid InspectionPageVo inspectionPageVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, errorList.get(0).getDefaultMessage(), inspectionPageVo);
        }
        return inspectionService.inspectionList(inspectionPageVo);
    }

    @Deprecated
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetInspectionModel.class),
    })
    @ApiOperation(value = "根据单号获取到货通知单信息",
            notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "arrivalId", value = "申请单号", paramType = "path", dataType = "String", required = true)
    })
    @RequestMapping(value = "/{arrivalId}", method = RequestMethod.GET)
    public Result getInspection(@PathVariable("arrivalId") String arrivalId) {
        return inspectionService.getInspection(arrivalId);
    }

    @Deprecated
    @ApiOperation(value = "导出到货通知单信息",
            notes = "")
    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public Result getInspection(@Valid @RequestBody InspectionExcelVo inspectionExcelVo, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return inspectionService.excel(inspectionExcelVo, request, response);
    }

    @ApiOperation(value = "获取人员选择接口",
            notes = "")
    @RequestMapping(value = "/employee/selector", method = RequestMethod.GET)
    public Result getEmployeeSelector(@Valid EmployeeSelectorVo employeeSelectorVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return purchaseEmployeeService.getEmployeeSelector(employeeSelectorVo);
    }

    @ApiOperation(value = "获取供应商选择接口", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "supplierId", value = "供应商编号", paramType = "query", dataType = "String", required = true)
    })

    @RequestMapping(value = "/supplier/info", method = RequestMethod.GET)
    public Result getSupplierInfo(@RequestParam("supplierId") String supplierId) {
        return supplierService.getSupplierInfo(supplierId);
    }

    @ApiOperation(value = "改变到货通知单下某个sku的子状态", notes = "根据到货通知单号，修改到货通知单状态")
    @RequestMapping(value = "/status/{arrivalId}/{skuId}/{statusCd}", method = RequestMethod.PUT)
    public Result changeStatus(@PathVariable("arrivalId") String arrivalId,
                               @PathVariable("skuId") String skuId,
                               @PathVariable("statusCd") Integer statusCd) {
        return inspectionService.changeStatus(arrivalId, skuId, statusCd);
    }

    @RequestMapping(value = "/status/{arrivalId}/{skuId}", method = RequestMethod.GET)
    public Result getStatusByArrivalIdAndSkuId(@PathVariable("arrivalId") String arrivalId,
                                               @PathVariable("skuId") String skuId) {

        try {
            Integer status = inspectionService.getStatusByArrivalIdAndSkuId(arrivalId, skuId);
            return Result.success(status);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

    }

    @ApiOperation(value = "根据采购单和sku获取入库欠数",
            notes = "获取入库欠数")
    @RequestMapping(value = "/warehouse/getWarehouseOweNum/{orderId}/{skuId}", method = RequestMethod.GET)
    public Result getWarehouseOweNum(@PathVariable("orderId") String orderId, @PathVariable("skuId") String skuId) {
        Integer oweNum = inspectionService.getOweNum(orderId, skuId);
        Map map = new HashMap<>();
        map.put("skuId", skuId);
        map.put("warehouseOweNum", oweNum);
        return Result.success(map);
    }

    @ApiOperation("回写wms传回来的到货通知单数据")
    @RequestMapping(value = "/wms/return", method = RequestMethod.PUT)
    public Result wmsReturnArrivalData(@RequestBody ArrivalBillReturnVO entity) {
        try {
            Result result = inspectionService.updateWmsReturnData(entity);
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), null);
        }
    }

    @ApiOperation("通过到货通知单的唯一标识码查出到货通知单号")
    @RequestMapping(value = "/wms/inspection", method = RequestMethod.GET)
    public Result getInspectionIdByUuid(@RequestParam("randomValue") String randomValue) {
        try {
            if (inspectionService.getInspectionIdByOrder(randomValue) == null) {
                return Result.failure(390, "参数错误", "");
            }
            return Result.success(inspectionService.getInspectionIdByOrder(randomValue));
        } catch (Exception e) {
            return Result.failure(390, "查询失败", "");
        }
    }
}
