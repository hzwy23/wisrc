package com.wisrc.sales.webapp.controller;

import com.wisrc.sales.webapp.entity.SalePlanCycleDetailsEntity;
import com.wisrc.sales.webapp.service.SalePlanCycleDetailsService;
import com.wisrc.sales.webapp.service.SalePlanInfoService;
import com.wisrc.sales.webapp.service.SalePlanTotalService;
import com.wisrc.sales.webapp.utils.FileExportUtil;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Api(tags = "销售计划管理")
@RequestMapping(value = "/sales")
public class SalePlanInfoController {
    @Autowired
    private SalePlanInfoService salePlanInfoService;
    @Autowired
    private SalePlanCycleDetailsService salePlanCycleDetailsService;
    @Autowired
    private SalePlanTotalService salePlanTotalService;

    @RequestMapping(value = "/sale/plan", method = RequestMethod.POST)
    @ApiOperation(value = "新增销售计划及详情")
    public Result add(@RequestBody List<DetailSalePlanVO> list,
                      @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {

        try {
            List<SalePlanCycleDetailsEntity> planDetails = salePlanCycleDetailsService.getDateById(list.get(0).getList().get(0).getUuid());
            List planDate = new ArrayList();
            for (SalePlanCycleDetailsEntity planDetail : planDetails) {
                planDate.add(planDetail.getPlanDate());
            }
            for (DetailSalePlanVO vo : list) {
                List<SalePlanCycleDetailsEntity> voList = vo.getList();
                if (vo.getUpdate()) {
                    int index = planDate.indexOf(vo.getPlanDate());
                    if (index != -1) {
                        planDate.remove(index);
                    }
                    for (SalePlanCycleDetailsEntity detailVO : voList) {
                        detailVO.setWeight(vo.getWeight());
                        salePlanCycleDetailsService.updateEntity(detailVO);

                    }
                    //return Result.failure(390,"该商品在该月的销售计划已存在",voList);
                } else {
                    vo.getEntity().setChargeEmployeeId(userId);
                    vo.getEntity().setCreateUser(userId);
                    vo.getEntity().setModifyUser(userId);
                    salePlanInfoService.add(vo);
                }
            }
            if (planDate.size() > 0) {
                salePlanCycleDetailsService.deleteDetail(planDetails.get(0).getSalePlanId(), planDate);
            }
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), userId);
        }
        //return Result.success();
    }

    @RequestMapping(value = "/sale/plan", method = RequestMethod.GET)
    @ApiOperation("分页条件查询销售计划")
    public Result select(@RequestParam(value = "pageNum", required = false) String pageNum,
                         @RequestParam(value = "pageSize", required = false) String pageSize,
                         @RequestParam(value = "shopId", required = false) String shopId,
                         @RequestParam(value = "msku", required = false) String msku,
                         @RequestParam(value = "asin", required = false) String asin,
                         @RequestParam(value = "salesStatusCd", required = false) String salesStatusCd,
                         @RequestParam(value = "stockSku", required = false) String stockSku,
                         @RequestParam(value = "commodityName", required = false) String commodityName,
                         @RequestHeader("X-AUTH-ID") String userId) {
        try {
            LinkedHashMap list = getPlan(pageNum, pageSize, shopId, msku, asin, salesStatusCd, stockSku, commodityName, userId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }

    private LinkedHashMap getPlan(String pageNum, String pageSize,
                                  String shopId, String msku, String asin, String salesStatusCd,
                                  String stockSku, String commodityName, String userId) {
        LinkedHashMap list;
        int size, num;
        if (pageNum != null && pageSize != null) {
            size = Integer.valueOf(pageSize);
            num = Integer.valueOf(pageNum);

            if (shopId != null || msku != null
                    || asin != null || stockSku != null
                    || commodityName != null || salesStatusCd != null) {
                list = salePlanInfoService.findByCond(num, size, shopId, msku, asin, stockSku, commodityName, salesStatusCd, userId);
            } else {
                list = salePlanInfoService.findByPage(num, size, userId);
            }
        } else {
            // 不尽兴分页，直接查询所有的MSKU信息
            list = salePlanInfoService.findAll(shopId, msku, asin, stockSku, commodityName, salesStatusCd, userId);
        }
        return list;
    }

    @RequestMapping(value = "/sale/plan/{salePlanId}", method = RequestMethod.GET)
    public Result selectDetail(@PathVariable("salePlanId") String salePlanId,
                               @RequestHeader(value = "X-AUTH-ID") @ApiIgnore String userId) {

        try {
            List<AddSalePlanVO> list = salePlanCycleDetailsService.getDetail(salePlanId, 0, userId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.success(500, e.getMessage(), "没有查到结果");
        }
    }

    @RequestMapping(value = "/sale/plan/{salePlanId}", method = RequestMethod.PUT)
    @ApiOperation(value = "根据销售计划ID修改计划详情", response = UpdateSalePlanVO.class)
    public Result update(@RequestBody List<UpdateSalePlanVO> voList, BindingResult result, @PathVariable("salePlanId") String salePlanId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        try {
            salePlanCycleDetailsService.update(voList, salePlanId, userId);
            return Result.success(voList);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "联系后台");
        }
    }

    @RequestMapping(value = "/sale/total", method = RequestMethod.GET)
    @ApiOperation(value = "查询销售计划汇总信息", response = SelectTotalInfoVO.class)
    public Result getTotal(@RequestParam(value = "shopId", required = false) String shopId,
                           @RequestParam(value = "msku", required = false) String msku,
                           @RequestParam(value = "asin", required = false) String asin,
                           @RequestParam(value = "directorEmployeeId", required = false) String directorEmployeeId,
                           @RequestParam(value = "chargeEmployeeId", required = false) String chargeEmployeeId,
                           @RequestParam(value = "salesStatusCd", required = false) String salesStatusCd,
                           @RequestParam(value = "startMonth", required = false) String startMonth,
                           @RequestParam(value = "endMonth", required = false) String endMonth,
                           @RequestHeader("X-AUTH-ID") String userId) {
        try {
            List<SelectTotalInfoVO> list =
                    salePlanTotalService.getTotal(shopId, msku, asin, directorEmployeeId, chargeEmployeeId, startMonth, endMonth, salesStatusCd, userId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "联系后台");
        }
    }

    @RequestMapping(value = "/sale/export", method = RequestMethod.GET)
    public Result export(HttpServletResponse response, HttpServletRequest request,
                         @RequestParam(value = "pageNum", required = false) String pageNum,
                         @RequestParam(value = "pageSize", required = false) String pageSize,
                         @RequestParam(value = "shopId", required = false) String shopId,
                         @RequestParam(value = "msku", required = false) String msku,
                         @RequestParam(value = "asin", required = false) String asin,
                         @RequestParam(value = "salesStatusCd", required = false) String salesStatusCd,
                         @RequestParam(value = "stockSku", required = false) String stockSku,
                         @RequestParam(value = "commodityName", required = false) String commodityName,
                         @RequestHeader("X-AUTH-ID") String userId) {
        try {
            LinkedHashMap list = getPlan(pageNum, pageSize, shopId, msku, asin, salesStatusCd, stockSku, commodityName, userId);
            List<SelectSalePlanListVO> voList = (List) list.get("selectSalePlanListVOList");
            Workbook workbook = salePlanCycleDetailsService.getResult(voList);
            FileExportUtil.exportExcelTo(request, response, workbook,
                    "销售计划", "yyyyMMdd", ".xls");
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "导出失败");
        }

    }

    @RequestMapping(value = "/sale/repeat", method = RequestMethod.GET)
    @ApiOperation(value = "根据月份，商品ID查询是否有重复数据", response = SelectSalePlanDetailVO.class)
    public Result getTotal(@RequestParam(value = "planDate", required = false) String planDate,
                           @RequestParam(value = "commodityId", required = false) String commodityId
    ) {
        try {
            List<SelectSalePlanDetailVO> list = salePlanCycleDetailsService.getRecord(planDate, commodityId);
            if (list.size() > 0) {
                return Result.success(list);
            } else {
                return Result.success(list);
            }
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "联系后台");
        }
    }
}
