package com.wisrc.quality.webapp.controller;


import cn.hutool.core.date.DateUtil;
import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.service.ProductInspectionInfoService;
import com.wisrc.quality.webapp.vo.productInspectionInfo.add.AddProductInspectionInfoVO;
import com.wisrc.quality.webapp.vo.productInspectionInfo.get.GetProductInspectionInfoTempVO;
import com.wisrc.quality.webapp.vo.productInspectionInfo.get.GetProductInspectionInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "产品检验单管理")
@RequestMapping(value = "/quality")
public class ProductInspectionInfoController {
    private final Logger logger = LoggerFactory.getLogger(ProductInspectionInfoController.class);

    @Autowired
    private ProductInspectionInfoService productInspectionInfoService;

    @ApiOperation(value = "新增产品检验单", notes = "")
    @ApiImplicitParam(name = "X-AUTH-ID", value = "用户ID", required = true, dataType = "String", paramType = "header")
    @RequestMapping(value = "/inspection", method = RequestMethod.POST)
    public Result add(@Valid @RequestBody AddProductInspectionInfoVO addProductInspectionInfoVO, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        if (bindingResult.hasErrors()) {
            return new Result(9991, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        return productInspectionInfoService.insert(addProductInspectionInfoVO, userId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "inspectionCd", value = "检验单号", required = true, dataType = "String", paramType = "query"),
    })
    @ApiOperation(value = "更新检验单", notes = "")
    @RequestMapping(value = "/inspection", method = RequestMethod.PUT)
    public Result update(@RequestParam("inspectionCd") String inspectionCd, @Valid @RequestBody AddProductInspectionInfoVO addProductInspectionInfoVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9991, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        return productInspectionInfoService.update(inspectionCd, addProductInspectionInfoVO);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "inspectionCd", value = "检验单号", required = true, dataType = "String", paramType = "path"),
    })
    @ApiOperation(value = "通过检验单号查询检验单信息", notes = "")
    @RequestMapping(value = "/inspection/{inspectionCd}", method = RequestMethod.GET)
    public Result getByInspectionCd(@PathVariable("inspectionCd") String inspectionCd) {
        return productInspectionInfoService.getByInspectionCd(inspectionCd);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "inspectionCd", value = "检验单号", required = true, dataType = "String", paramType = "path"),
    })
    @ApiOperation(value = "通过检验单号删除检验单", notes = "")
    @RequestMapping(value = "/inspection/{inspectionCd}", method = RequestMethod.DELETE)
    public Result deleteByInspectionCd(@PathVariable("inspectionCd") String inspectionCd) {
        return productInspectionInfoService.deleteByInspectionCd(inspectionCd);
    }

    @ApiOperation(value = "模糊查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页（小于1或者null为不分页）", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（小于1或者null为不分页）", required = false, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/inspection/fuzzy/one", method = RequestMethod.GET)
    public Result deleteByInspectionCd(@Valid GetProductInspectionInfoTempVO tempVo, BindingResult bindingResult, Integer pageNum, Integer pageSize) {
        if (bindingResult.hasErrors()) {
            return Result.failure(423, bindingResult.getFieldError().getDefaultMessage(), tempVo);
        }
        GetProductInspectionInfoVO vo = new GetProductInspectionInfoVO();
        BeanUtils.copyProperties(tempVo, vo);
        //时间处理
        try {
            vo.setInspectionDateStart(DateUtil.parse(tempVo.getInspectionDateStart()));
            vo.setInspectionDateEnd(DateUtil.parse(tempVo.getInspectionDateEnd()));
        } catch (Exception e) {
            return new Result(9999, "日期格式不符合要求", null);
        }
        return productInspectionInfoService.fuzzyFind(vo, pageNum, pageSize);
    }

    @ApiOperation(value = "模糊查询产品sku批量关联查询产品检验单下的验货数量，合格数和不合格数", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "arrivalId", value = "到货通知单单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "skuIds", value = "库存sku编号数组", required = true, dataType = "List", paramType = "query"),

    })
    @RequestMapping(value = "/inspection/fuzzy/two", method = RequestMethod.GET)
    public Result deleteByInspectionCd(@RequestParam("skuIds") List<String> skuIds, @RequestParam("arrivalId") String arrivalId) {
        Map map = productInspectionInfoService.fuzzyFindTwo(skuIds, arrivalId);
        return Result.success(map);
    }

    @ApiOperation(value = "批量删除检验单", notes = "通过检验单编号数组批量删除检验单")
    @RequestMapping(value = "/inspection/delete/batch", method = RequestMethod.PUT)
    public Result deleteBatch(@RequestParam("inspectionCdsList") List<String> inspectionCdsList) {
        for (String inspectionCd : inspectionCdsList) {
            //因为单个的批量删除已经做了检验，所以不管单个的删除是否成功，不会影响数据的正确性
            productInspectionInfoService.deleteByInspectionCd(inspectionCd);
        }
        return Result.success();
    }

    @ApiOperation(value = "通过采购订单查产品检验单")
    @RequestMapping(value = "/inspection/by/orderId", method = RequestMethod.GET)
    public Result getInspectionProductInfo(@RequestParam("orderId") String orderId) {
        try {
            int a = productInspectionInfoService.getInspectionProductInfo(orderId);
            return Result.success(a);
        } catch (Exception e) {
            return Result.failure(390, "获取产品检验单失败", null);
        }
    }

}
