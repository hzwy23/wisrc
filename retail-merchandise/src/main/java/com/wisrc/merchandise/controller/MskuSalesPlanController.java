package com.wisrc.merchandise.controller;

import com.wisrc.merchandise.dto.mskuSalesPlan.SalesPlanPageDto;
import com.wisrc.merchandise.service.MskuSalesPlanService;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.vo.CheckMskuShopVo;
import com.wisrc.merchandise.vo.MskuSalesPlanPageVo;
import com.wisrc.merchandise.vo.SalesMskuEditVo;
import com.wisrc.merchandise.vo.SalesMskuSaveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/operation/merchandise/sales/plan")
@Controller
@Api(tags = "销售计划控制器")
public class MskuSalesPlanController {

    @Autowired
    private MskuSalesPlanService mskuSalesPlanService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SalesPlanPageDto.class)
    })
    @GetMapping("/list")
    @ApiOperation(value = "查询销售计划信息", notes = "店铺、小组、小组负责人均为选择框数据<br/>" +
            "关键字模糊查询asin、商品编号、商品名称和产品名称<br/><strong>必传</strong><br/>" +
            "currentPage和pageSize固定为数字，排序方式数据格式由前端约定")
    @ResponseBody
    public Result mskuSalesPlan(@Valid MskuSalesPlanPageVo mskuSalesPlanPageVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return mskuSalesPlanService.getSalesPlanList(mskuSalesPlanPageVo);
    }

    /**
     * 添加销售计划
     *
     * @param salesMskuSaveVo
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "添加销售计划信息", notes = "以店铺id和商品编号对商品进行关联，添加计划时商品必须存在，商品必须要有至少一条计划才能新增<br/>" +
            "一个商品可以存在多个计划，多个计划之间时间区域不能有重叠，若结束时间为空则代表永久有效<br/>指导标准价为正两位小数，预计日销量为正整数")
    @ResponseBody
    public Result saveMskuSalesPlan(@Valid @RequestBody SalesMskuSaveVo salesMskuSaveVo, BindingResult bindingResult) {
        return mskuSalesPlanService.saveSalesPlan(salesMskuSaveVo, bindingResult);
    }

    /**
     * 获取销售计划内容
     *
     * @param id 销售计划ID
     * @return
     */
    @GetMapping("/plans")
    @ApiOperation(value = "获取销售计划内容", notes = "供外部模块访问")
    @ResponseBody
    public Result getMskuSalesPlan(@RequestParam(value = "id", required = true) String id) {
        return mskuSalesPlanService.getSalesPlan(id);
    }

    /**
     * 编辑销售计划
     *
     * @param salesMskuEditVo
     * @return
     */
    @PutMapping("/edit")
    @ApiOperation(value = "编辑销售计划信息", notes = "店铺id和商品编号不能编辑，可对计划数量进行增删")
    @ResponseBody
    public Result editMskuSalesPlan(@Valid @RequestBody SalesMskuEditVo salesMskuEditVo, BindingResult bindingResult) {
        return mskuSalesPlanService.editMskuSalesPlan(salesMskuEditVo, bindingResult);
    }

    /**
     * 删除单个销售计划
     *
     * @param planId 销售计划ID
     * @return
     */
    @DeleteMapping("/delete/{planId}")
    @ApiOperation(value = "删除销售计划", notes = "未使用接口")
    @ResponseBody
    public Result deleteSalesPlan(@PathVariable(value = "planId") String planId) {
        return mskuSalesPlanService.deleteSalesPlan(planId);
    }

    /**
     * 删除msku销售计划
     *
     * @param id mskuID
     * @return
     */
    @DeleteMapping("/delete/plan/{id}")
    @ApiOperation(value = "删除销售计划信息", notes = "对整个商品下的所有计划进行删除，没有计划的商品不会再页面显示")
    @ResponseBody
    public Result deleteMskuSalesPlan(@PathVariable(value = "id") String id) {
        return mskuSalesPlanService.deleteMskuSalesPlan(id);
    }

    /**
     * 验证msku销售计划
     *
     * @param mskuShop
     * @return
     */
    @PostMapping("/check")
    @ApiOperation(value = "验证msku和店铺", notes = "不能在不存在商品或已有计划的商品外新增商品计划")
    @ResponseBody
    public Result checkMskuShop(@Valid @RequestBody CheckMskuShopVo mskuShop) {
        return mskuSalesPlanService.checkMskuShop(mskuShop);
    }
}
