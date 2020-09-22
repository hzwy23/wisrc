package com.wisrc.sales.webapp.controller;

import com.wisrc.sales.webapp.enity.EstimateDetailEnity;
import com.wisrc.sales.webapp.enity.EstimateEnity;
import com.wisrc.sales.webapp.enity.RemarkEnity;
import com.wisrc.sales.webapp.enity.UpdateRemarkEnity;
import com.wisrc.sales.webapp.service.EstimateService;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.utils.Time;
import com.wisrc.sales.webapp.vo.EstimateEnityVo;
import com.wisrc.sales.webapp.vo.MskuParameterVo;
import com.wisrc.sales.webapp.vo.SaleEstimateVo;
import com.wisrc.sales.webapp.vo.saleEstimate.SaleEstimatePageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "销售预估管理")
@RequestMapping(value = "/sales")
public class SaleEstimateController {
    @Autowired
    private EstimateService estimateService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mskuId", value = "msku编码", required = false, dataType = "String", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "asinId", value = "asin编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "skuId", value = "库存sku", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "saleStatus", value = "销售状态", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "productName", value = "产品名称", required = false, dataType = "String", paramType = "query")

    })
    @RequestMapping(value = "/estimate", method = RequestMethod.POST)
    @ApiOperation(value = "新增销售预估", notes = "新增销售预估包括销售预估明细）", response = EstimateEnity.class)
    @ResponseBody
    public Result add(@RequestBody @Validated EstimateEnity estimateEnity, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        //暂时停止使用
        if (true) {
            return new Result(9990, "新增操作暂时停止使用", null);
        }

        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), null);
        }
        try {
            estimateEnity.setCreateTime(Time.getCurrentDateTime());
            estimateEnity.setCreateUser(userId);
            Result thisResult = estimateService.saveEstimate(estimateEnity);
            if (thisResult.getCode() != 200) {
                return thisResult;
            }
            return Result.success("销售预估保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "新增销售预估包括销售预估明细失败", e.getMessage());
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mskuId", value = "msku编码", required = false, dataType = "String", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "asinId", value = "asin编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "skuId", value = "库存sku", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "saleStatus", value = "销售状态", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "productName", value = "产品名称", required = false, dataType = "String", paramType = "query")

    })

    @ApiOperation(value = "分页模糊查询销售预估", notes = "查询销售预估信息。", response = SaleEstimateVo.class)
    @RequestMapping(value = "/estimate/list", method = RequestMethod.GET)
    public Result findByCond(@Valid SaleEstimatePageVo SaleEstimatePageVo, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        try {
            return estimateService.findByCond(SaleEstimatePageVo, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "分页模糊查询销售预估失败", e.getMessage());
        }
    }


    @RequestMapping(value = "/estimate/{estimateId}", method = RequestMethod.GET)
    @ApiOperation(value = "查询销售预估详情当前日期+1所在月份的数据")
    @ResponseBody
    public Result getEstimateById(@PathVariable("estimateId") String estimateId,
                                  @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        return estimateService.getEstimateEnityById(estimateId, userId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "createUser", value = "创建人", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "commodityId", value = "商品id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "estimateMonth", value = "销售计划月份(yyyy-mm)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "asOfDate", value = "数据日期", required = false, dataType = "String", paramType = "query"),

    })
    @RequestMapping(value = "/estimate/estimateDatail", method = RequestMethod.GET)
    @ApiOperation(value = "获取某个用户某个月销售计划")
    @ResponseBody
    public Result getEstimateByCond(@RequestParam(value = "createUser", required = false) String createUser,
                                    @RequestParam(value = "commodityId", required = true) String commodityId,
                                    @RequestParam(value = "estimateMonth", required = true) String estimateMonth,
                                    @RequestParam(value = "asOfDate", required = false) String asOfDate,
                                    @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        if (createUser == null) {
            createUser = userId;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date asOfDateSql = Time.getCurrentDate();
        if (asOfDate != null) {
            try {
                asOfDateSql = new Date(sdf.parse(asOfDate).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                return Result.failure(400, "日期格式错误", "");
            }
        }
        try {
            return estimateService.getEstimateEnityById(userId, createUser, commodityId, estimateMonth, asOfDateSql);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "服务异常请稍后再试", e.getMessage());
        }
    }

    // todo 是否加用户的msku检验
    @RequestMapping(value = "/sale/msku/{shopId}/{mskuId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据shopId和mskuId查询skuId和产品名称")
    @ResponseBody
    public Result getEstimateByCond(@PathVariable("shopId") String shopId, @PathVariable("mskuId") String mskuId, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        try {
            return estimateService.getProductDeatail(shopId, mskuId, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "服务异常请稍后再试", e.getMessage());
        }
    }

    @RequestMapping(value = "/estimate", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑销售预估明细")
    @ResponseBody
    public Result update(@RequestBody @Validated EstimateEnityVo estimateEnityVo, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        //暂时停止使用
        if (true) {
            return new Result(9990, "编辑操作暂时停止使用", null);
        }

        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        try {
            estimateEnityVo.setUpdateUser(userId);
            Result updateResult = estimateService.updateEstimateAndDetail(estimateEnityVo);
            if (updateResult.getCode() != 200) {
                return updateResult;
            }
            return Result.success("编辑销售预估成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "编辑销售预估失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/estimate/currenUser/{commodityId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户日期+1所在月份的数据")
    @ResponseBody
    public Result getCurrentUserEstimate(@PathVariable("commodityId") String commodityId, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        return estimateService.getEstimateEnityById(commodityId, userId);
    }

    // todo 是否加用户的msku检验
    @RequestMapping(value = "/estimate/totalNum/{commodityId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取某商品销售预估总量")
    @ResponseBody
    public Result getCommodityTotalNum(@PathVariable("commodityId") String commodityId,
                                       @RequestParam(value = "startTime", required = false) String startTime,
                                       @RequestParam(value = "endTime", required = false) String endTime,
                                       @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        try {
            Map map = estimateService.getTotalNum(commodityId, startTime, endTime);
            return Result.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "请联系管理员", e.getMessage());
        }
    }

    @RequestMapping(value = "/estimate/remark", method = RequestMethod.POST)
    @ApiOperation(value = "给当前用户的销售预估添加备注")
    @ResponseBody
    public Result addRemark(@RequestBody @Validated RemarkEnity remarkEnity, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        //暂时停止使用
        if (true) {
            return new Result(9990, "添加备注操作暂时停止使用", null);
        }

        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        try {
            remarkEnity.setCreateUser(userId);
            // todo  没有改造
            estimateService.insertRemark(remarkEnity);
            return Result.success("添加备注成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "请联系管理员", e.getMessage());
        }
    }

    @RequestMapping(value = "/estimate/updateRemark", method = RequestMethod.POST)
    @ApiOperation(value = "编辑销售预估添加备注")
    @ResponseBody
    public Result addUpdateRemark(@RequestBody @Validated UpdateRemarkEnity updateRemarkEnity, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        //暂时停止使用
        if (true) {
            return new Result(9990, "编辑操作暂时停止使用", null);
        }

        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        try {
            updateRemarkEnity.setCreateUser(userId);
            // todo  没有改造
            estimateService.insertUpdateRemark(updateRemarkEnity);
            return Result.success("添加备注成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "请联系管理员", e.getMessage());
        }
    }

    // todo 是否加用户的msku检验
    @RequestMapping(value = "/estimate/estimateTimeDatail", method = RequestMethod.GET)
    @ApiOperation(value = "获取某商品时间段内销售预估")
    @ResponseBody
    public Result getEstimateByTime(@RequestParam(value = "mskuId", required = true) String mskuId,
                                    @RequestParam(value = "shopId", required = true) String shopId,
                                    @RequestParam(value = "startTime", required = false) String startTime,
                                    @RequestParam(value = "endTime", required = false) String endTime,
                                    @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        try {
            List<EstimateDetailEnity> estimateDetailEnityList = estimateService.getEstimateEnityByTime(mskuId, shopId, startTime, endTime);
            return Result.success(estimateDetailEnityList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "请联系管理员", e.getMessage());
        }
    }

    // todo 是否加用户的msku检验
    @RequestMapping(value = "/estimate/batchEstimateDetail", method = RequestMethod.POST)
    @ApiOperation(value = "批量查询某时间内预估")
    @ResponseBody
    public Result getBatchEstimateDetail(@RequestBody List<MskuParameterVo> mskuParameterVoList) {
        try {
            Map map = estimateService.getBatchEstimateDetail(mskuParameterVoList);
            return Result.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "请联系管理员", e.getMessage());
        }
    }
}
