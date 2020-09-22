package com.wisrc.merchandise.controller;


import com.wisrc.merchandise.dto.msku.MskuPageDTO;
import com.wisrc.merchandise.service.MskuInfoService;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.vo.*;
import com.wisrc.merchandise.vo.mskuStockSalesInfo.SetBatchMskuStockSalesInfoVO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/operation/merchandise/msku")
@Controller
@Api(tags = "商品管理控制器")
public class MskuInfoController {

    @Autowired
    private MskuInfoService mskuInfoService;

    /**
     * 查询msku
     *
     * @param shopId       所属店铺
     * @param team         负责小组
     * @param manager      负责人
     * @param deliveryMode 配送方式
     * @param salesStatus  销售状态
     * @param mskuStatus   MSKU状态
     * @param findKey      关键字
     * @param currentPage  当前页码
     * @param pageSize     页面数据量
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询商品信息", notes = "店铺、小组、小组负责人、配送方式、" +
            "销售状态和商品状态均为选择框数据<br/>关键字模糊查询asin、商品编号、商品名称、产品编号和产品名称<br/><strong>必传</strong><br/>" +
            "currentPage和pageSize固定为数字，排序方式数据格式由前端约定")
    @ApiImplicitParams(value =
            {@ApiImplicitParam(paramType = "query", dataType = "String", name = "shopId", value = "店铺id"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "team", value = "小组"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "manager", value = "小组负责人"),
                    @ApiImplicitParam(paramType = "query", dataType = "int", name = "deliveryMode", value = "配送方式"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "salesStatus", value = "销售状态"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "mskuStatus", value = "商品状态"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "findKey", value = "关键字"),
                    @ApiImplicitParam(paramType = "query", dataType = "int", name = "currentPage", value = "当前页码", defaultValue = "1"),
                    @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "页面数据数量", defaultValue = "10"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "sort", value = "排序方式", defaultValue = "{prop:\"updateTime\",order:\"descending\"}"),
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X-AUTH-ID", value = "当前帐号", required = true)
            }
    )
    @ResponseBody
    public Result mskuInfo(@RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String userId,
                           String shopId, String team, String manager, Integer deliveryMode, String salesStatus, String mskuStatus, String findKey,
                           Integer currentPage, Integer pageSize, String sort) {
        Integer salesStatusInt = null;
        Integer mskuStatusInt = null;
        if (salesStatus != null) {
            salesStatusInt = Integer.parseInt(salesStatus);
        }
        if (mskuStatus != null) {
            mskuStatusInt = Integer.parseInt(mskuStatus);
        }

        return mskuInfoService.getMskuList(userId, shopId, team, manager, deliveryMode, salesStatusInt, mskuStatusInt, findKey, currentPage, pageSize, sort);
    }

    /**
     * 添加msku
     *
     * @param mskuInfoSaveVo
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增商品信息", notes = "商品编号和店铺id组合成唯一索引<br/>库存sku和asin外部信息为从其他外部接口获取的信息<br/>" +
            "防止小组和负责人之间发生变化产生数据混乱所以只存储小组和负责人名字<br/>父asin为asin外部获取信息，可进行更改")
    @ResponseBody
    public Result saveMskuInfo(@Valid @RequestBody MskuInfoSaveVo mskuInfoSaveVo) {
        return mskuInfoService.saveMsku(mskuInfoSaveVo);
    }

    /**
     * 获取单个msku内容
     *
     * @param id 商品ID
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MskuPageDTO.class)
    })
    @GetMapping("/msku")
    @ApiOperation(value = "获取商品内容", notes = "接口供外部访问")
    @ResponseBody
    public Result getMskuInfo(@RequestParam(value = "id", required = true) String id) {
        return mskuInfoService.getMsku(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/msku/stock/sales")
    @ApiOperation(value = "获取商品的销量与库存")
    @ResponseBody
    public Result getSalesStockById(@RequestParam(value = "id", required = true) String id) {
        return mskuInfoService.getSalesStockById(id);
    }

    /**
     * 编辑msku
     *
     * @param mskuInfoEditVo
     * @return
     */
    @PutMapping("/edit")
    @ApiOperation(value = "编辑商品信息", notes = "商品编号和店铺id不能更改，根据商品id进行编辑，asin外部信息从爬虫接口获取，只允许进行数据获取更新")
    @ResponseBody
    public Result editMskuInfo(@Valid @RequestBody MskuInfoEditVo mskuInfoEditVo) {
        return mskuInfoService.editMsku(mskuInfoEditVo);
    }

    /**
     * 停用/启用切换
     *
     * @param mskuSwitchVo
     * @return
     */
    @PutMapping("/switch")
    @ApiOperation(value = "停用/启用切换", notes = "商品状态编码，1：启用，2：禁用")
    @ResponseBody
    public Result mskuSwitch(@RequestBody MskuSwitchVo mskuSwitchVo) {
        return mskuInfoService.mskuSwitch(mskuSwitchVo);
    }

    /**
     * 批量停用
     *
     * @param idsBatchVo
     * @return
     */
    @PutMapping("/all-down")
    @ApiOperation(value = "批量停用", notes = "所有选择商品更改为禁用状态")
    @ResponseBody
    public Result mskuSwitch(@RequestBody IdsBatchVo idsBatchVo) {
        return mskuInfoService.allDelete(idsBatchVo);
    }

    /**
     * msku验证接口
     *
     * @param msku   商品sku
     * @param shopId 店铺Id
     * @return
     */
    @GetMapping("/check-msku")
    @ApiOperation(value = "验证msku", notes = "通过爬虫接口验证msku是否在amazon上存在，返回asin外部信息")
    @ApiImplicitParams(value =
            {@ApiImplicitParam(paramType = "query", dataType = "String", name = "msku", value = "商品sku", required = true),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "shopId", value = "店铺Id", required = true)
            }
    )
    @ResponseBody
    public Result checkMsku(String msku, String shopId) {
        return mskuInfoService.checkMsku(msku, shopId);
    }

    /**
     * 添加墓志铭
     *
     * @param epitaphSaveVo
     * @return
     */
    @PostMapping(value = "/epitaph/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "添加墓志铭", notes = "为商品添加备注，内容不能为空")
    @ResponseBody
    public Result saveEpitaph(@Valid @RequestBody EpitaphSaveVo epitaphSaveVo, BindingResult bindingResult) {
        return mskuInfoService.saveEpitaph(epitaphSaveVo, bindingResult);
    }

    /**
     * 编辑墓志铭
     *
     * @param epitaphSaveVo
     * @return
     */
    @PutMapping(value = "/epitaph/edit")
    @ApiOperation(value = "编辑墓志铭", notes = "对商品备注进行修改，若内容为空则进行删除")
    @ResponseBody
    public Result editEpitaph(@Valid @RequestBody EpitaphSaveVo epitaphSaveVo, BindingResult bindingResult) {
        return mskuInfoService.editEpitaph(epitaphSaveVo, bindingResult);
    }

    /**
     * 变更负责人
     *
     * @param chargeEditVo
     * @return
     */
    @PutMapping("/charge/edit")
    @ApiOperation(value = "变更负责人", notes = "批量更改商品负责人")
    @ResponseBody
    public Result chargeEdit(@RequestBody IdsBatchVo chargeEditVo) {
        return mskuInfoService.changeManager(chargeEditVo);
    }

    @PutMapping("/charge/edit/sales/stock")
    @ApiOperation(value = "批量更新msku的销量与库存")
    @ResponseBody
    public Result updateSalesStock(@Valid @RequestBody SetBatchMskuStockSalesInfoVO vo,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, errorList.get(0).getDefaultMessage(), vo);
        }
        return mskuInfoService.updateSalesStock(vo);
    }

    @RequestMapping(value = "/msku/basis/info", method = RequestMethod.POST)
    @ResponseBody
    public Result searchMskuInfo(@RequestParam("pageNum") Integer pageNum,
                                 @RequestParam("pageSize") Integer pageSize,
                                 @RequestParam(value = "platformName", required = false) String platformName,
                                 @RequestParam(value = "shopName", required = false) String shopName,
                                 @RequestParam(value = "mskuId", required = false) String mskuId,
                                 @RequestParam(value = "excludingMskuIds", required = false) String[] excludingMskuIds) {
        return mskuInfoService.searchMskuInfo(pageNum, pageSize, platformName, shopName, mskuId, excludingMskuIds);
    }

    @GetMapping("/msku/Info/batch")
    @ApiOperation("根据commodityId批量查询msku信息")
    @ResponseBody
    public Result batchGetMskuInfo(@RequestParam("commodityIds") List<String> commodityIds){
        try {
            return mskuInfoService.batchGetMsku(commodityIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390,e.getMessage(),"");
        }
    }

    @ApiOperation(value = "判断该店铺的商品是否有fnCode")
    @RequestMapping(value = "/check/fnCode", method = RequestMethod.GET)
    @ResponseBody
    public Result checkFnCode(@RequestParam("mskuId")String mskuId,
                              @RequestParam("shopId")String shopId) {
        try {
            String result = mskuInfoService.checkFnCode(mskuId, shopId);
            if(result == null || result.equals("")) {
                return Result.success(390, "该商品没有FnCode信息","");
            } else {
                return Result.success(200,"成功", result);
            }
        }catch (Exception e) {
            return  Result.failure(390,"失败","");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/generator/commodity", method = RequestMethod.POST)
    public Result updateCommodity() {
        return mskuInfoService.updateCommodity();
    }

    @ApiOperation(value = "导出msku")
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    @ApiImplicitParams(value =
            {@ApiImplicitParam(paramType = "query", dataType = "String", name = "shopId", value = "店铺id"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "team", value = "小组"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "manager", value = "小组负责人"),
                    @ApiImplicitParam(paramType = "query", dataType = "int", name = "deliveryMode", value = "配送方式"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "salesStatus", value = "销售状态"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "mskuStatus", value = "商品状态"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "findKey", value = "关键字"),
                    @ApiImplicitParam(paramType = "query", dataType = "String", name = "sort", value = "排序方式", defaultValue = "{prop:\"updateTime\",order:\"descending\"}"),
                    @ApiImplicitParam(paramType = "header", dataType = "String", name = "X-AUTH-ID", value = "当前帐号", required = true)
            }
    )

    @ResponseBody
    public void mskuExcel (@RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String userId,
                           String shopId, String team, String manager, Integer deliveryMode, String salesStatus, String mskuStatus, String findKey,
                           String sort, HttpServletResponse response, HttpServletRequest request) {
        Integer salesStatusInt = null;
        Integer mskuStatusInt = null;
        if (salesStatus != null) {
            salesStatusInt = Integer.parseInt(salesStatus);
        }
        if (mskuStatus != null) {
            mskuStatusInt = Integer.parseInt(mskuStatus);
        }
        mskuInfoService.mskuExcel(userId, shopId, team, manager, deliveryMode, salesStatusInt, mskuStatusInt, findKey, sort, response, request);

    }
}
