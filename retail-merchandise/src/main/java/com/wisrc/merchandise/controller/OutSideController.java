package com.wisrc.merchandise.controller;

import com.wisrc.merchandise.dto.msku.GetMskuFBADto;
import com.wisrc.merchandise.dto.msku.GetMskuInfoDTO;
import com.wisrc.merchandise.dto.msku.GetMskuOutsideDTO;
import com.wisrc.merchandise.dto.msku.MskuPageOutsideDTO;
import com.wisrc.merchandise.entity.MskuSaleNumEnity;
import com.wisrc.merchandise.service.MskuInfoService;
import com.wisrc.merchandise.service.ShopOutsideService;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.vo.*;
import com.wisrc.merchandise.vo.outside.GetIdByNumAndShopVo;
import com.wisrc.merchandise.vo.outside.MskuSafetyDayEditVo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "外部接口服务调用")
@RequestMapping(value = "/operation")
public class OutSideController {
    @Autowired
    private ShopOutsideService shopOutsideService;
    @Autowired
    private MskuInfoService mskuInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OutSideController.class);

    @ApiOperation(value = "店铺选择信息", notes = "外部接口，用于其他模块需要下拉框模式等店铺数据时，返回所有店铺信息。")
    @RequestMapping(value = "/shop/selector", method = RequestMethod.GET)
    public Result selector() {
        return shopOutsideService.getShopSelector();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetMskuOutsideDTO.class)
    })
    @ApiOperation(value = "获取商品基本信息", notes = "外部接口，用于其他模块需要下拉框模式等店铺数据时，返回所有店铺信息。")
    @RequestMapping(value = "/merchase/{mskuId}", method = RequestMethod.GET)
    @ApiImplicitParams(value =
            {@ApiImplicitParam(paramType = "path", dataType = "String", name = "mskuId", value = "msku编号", required = true)
            })
    public Result getMsku(@PathVariable("mskuId") String mskuId) {
        return mskuInfoService.getMskuOutSide(mskuId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = MskuPageOutsideDTO.class)
    })
    @ApiOperation(value = "获取商品信息列表", notes = "外部接口，用于其他模块需要拼接商品信息。")
    @RequestMapping(value = "/merchandise/msku", method = RequestMethod.GET)
    public Result getMskuList(@Valid MskuInfoPageOutsideVo mskuInfoPageOutsideVo, BindingResult bindingResult, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "查询商品列表信息失败", errorList.get(0).getDefaultMessage());
        }
        return mskuInfoService.getMskuListOutside(mskuInfoPageOutsideVo, userId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetMskuInfoDTO.class)
    })
    @ApiOperation(value = "根据id获取商品信息", notes = "")
    @RequestMapping(value = "/merchandise/msku/batch", method = RequestMethod.GET)
    public Result getMskuListById(@Valid GetMskuListByIdVo getMskuListByIdVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return mskuInfoService.getMskuInfo(getMskuListByIdVo);
    }

    @ApiOperation(value = "根据店铺和msku获取商品id", notes = "")
    @RequestMapping(value = "/merchandise/msku/id", method = RequestMethod.GET)
    public Result getMskuId(@Valid GetMskuIdVo getMskuIdVo, BindingResult bindingResult, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return mskuInfoService.getMskuId(getMskuIdVo, userId);
    }

    @ApiOperation(value = "根据店铺和msku获取商品id", notes = "")
    @RequestMapping(value = "/merchandise/msku/id/batch", method = RequestMethod.POST)
    public Result getIdByNumAndShop(@Valid @RequestBody GetIdByNumAndShopVo getMskuIdVo, BindingResult bindingResult, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return mskuInfoService.getIdByNumAndShop(getMskuIdVo, userId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = MskuPageOutsideDTO.class)
    })
    @ApiOperation(value = "根据商品编号和名称获取商品id", notes = "外部接口，用于其他模块需要拼接商品信息。")
    @RequestMapping(value = "/merchandise/msku/ids", method = RequestMethod.GET)
    public Result getIdByMskuIdAndName(@Valid GetByMskuIdAndNameVo getByMskuIdAndNameVo, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String userId) {
        return mskuInfoService.getIdByMskuIdAndName(getByMskuIdAndNameVo, userId);
    }

    @ApiOperation(value = "根据asin,产品编号和商品名称获取商品", notes = "外部接口，用于其他模块需要拼接商品信息。")
    @RequestMapping(value = "/merchandise/msku/search", method = RequestMethod.GET)
    public Result mskuSearch(MskuSearchVo mskuSearchVo, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String userId) {
        return mskuInfoService.mskuSearch(mskuSearchVo, userId);
    }

    @ApiOperation(value = "根据fnsku获取商品", notes = "外部接口，根据fnsku获取商品商品信息。")
    @RequestMapping(value = "/merchandise/msku/fnsku/{fnsku}", method = RequestMethod.GET)
    public Result mskuFnsku(@PathVariable("fnsku") String fnsku) {
        return mskuInfoService.mskuFnsku(fnsku);
    }

    @ApiOperation(value = "根据fnsku获取商品", notes = "外部接口，根据fnsku模糊获取商品商品信息。")
    @RequestMapping(value = "/merchandise/msku/fnsku/like", method = RequestMethod.GET)
    public Result getProductsByFnskuBatch(@RequestParam("fnsku") String fnsku) {
        return Result.success(mskuInfoService.mskuByFnSkuLike(fnsku));
    }

    @ApiOperation(value = "根据销售状态获取商品的产品", notes = "外部接口，用于其他模块需要拼接商品信息。")
    @RequestMapping(value = "/merchandise/product/status", method = RequestMethod.GET)
    public Result getSkuId(@RequestParam("saleStatusList") List saleStatusList) {
        return mskuInfoService.getSkuId(saleStatusList);
    }

    @ApiOperation(value = "更新商品销量信息", notes = "外部接口，用于更新商品销量表信息。")
    @RequestMapping(value = "/merchandise/updateMskuSale", method = RequestMethod.POST)
    public Result updateMskuSale(@RequestBody List<MskuSaleNumEnity> mskuSaleNumEnityList) {
        return mskuInfoService.updateSaleList(mskuSaleNumEnityList);
    }

    @ApiOperation(value = "关键字不同时筛选获取商品", notes = "外部接口，用于其他模块需要拼接商品信息。")
    @RequestMapping(value = "/merchandise/msku/key", method = RequestMethod.GET)
    public Result getByKeyword(GetByKeywordVo getByKeywordVo, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String userId) {
        return mskuInfoService.getByKeyword(getByKeywordVo, userId);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetMskuFBADto.class)
    })
    @ApiOperation(value = "获取fba补货管理所需商品信息", notes = "只查询 推广，在售，清仓销售状态")
    @RequestMapping(value = "/merchandise/msku/fba", method = RequestMethod.GET)
    public Result getMskuFBAVo(@Valid GetMskuFBAVo getMskuFBAVo, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") String userId) {
        return mskuInfoService.getMskuFBA(getMskuFBAVo, userId);
    }

    @ApiOperation(value = "更新商品fba在仓库存", notes = "外部接口，更新商品fba在仓库存。")
    @RequestMapping(value = "/merchandise/updateMskuStock", method = RequestMethod.POST)
    public Result updateMskuStock(@RequestBody List<Map> mapList) {
        try {
            mskuInfoService.updateStockList(mapList);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, "更新商品fba在仓库存失败", e.getMessage());
        }
    }

    @ApiOperation(value = "获取所有没有上架的商品", notes = "外部接口，获取所有没有上架的商品。")
    @RequestMapping(value = "/merchandise/unShelve", method = RequestMethod.GET)
    public Result getUnShelve() {
        try {
            List<String> unshelveList = mskuInfoService.getUnShelve();
            return Result.success(unshelveList);
        } catch (Exception e) {
            return Result.failure(390, "获取所有没有上架的商品失败", e.getMessage());
        }
    }

    @ApiOperation(value = "更新商品上架时间", notes = "外部接口，更新商品上架时间。")
    @RequestMapping(value = "/merchandise/updateShelveInfo", method = RequestMethod.POST)
    public Result updateShelveInfo(@RequestBody List<Map> mapList) {
        try {
            mskuInfoService.updateShelveInfo(mapList);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, "更新商品上架时间失败", e.getMessage());
        }
    }

    @ApiOperation(value = "更新商品安全库存天数", notes = "")
    @RequestMapping(value = "/merchandise/safety", method = RequestMethod.PUT)
    public Result editSafetyDay(@RequestBody List<MskuSafetyDayEditVo> safetyDays) {
        return mskuInfoService.editSafetyDay(safetyDays);
    }

    @ApiOperation(value = "获取仓库Id和fnsku和shopId", notes = "外部接口，获取仓库Id和fnsku。")
    @RequestMapping(value = "/merchandise/WarehouseIdAndFnsku", method = RequestMethod.POST)
    public Result getWarehouseIdAndFnsku(@RequestBody List<Map> mapList) {
        try {
            Map<String,Map> map=mskuInfoService.getWarehouseIdAndFnsku(mapList);
            return Result.success(map);
        } catch (Exception e) {
            return Result.failure(390, "获取仓库Id和fnsku失败", e.getMessage());
        }
    }

    @ApiOperation(value = "检查亚马逊fnsku", notes = "检查亚马逊fnsku。")
    @RequestMapping(value = "/merchandise/checkFnsku", method = RequestMethod.POST)
    public Result checkFnsku(@RequestBody List<Map> mapList) {
        try {
            Result result=mskuInfoService.checkFnsku(mapList);
            if(result.getCode()!=200){
                return result;
            }
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, "更新商品上架时间失败", e.getMessage());
        }
    }

    @ApiOperation(value = "根据负责人查询负责的商品", notes = "若没有权限，则返回空数组，若为超级管理员，则返回null值")
    @RequestMapping(value = "/merchandise/employee", method = RequestMethod.GET)
    public Result getMskuByEmployee(@RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {
        return mskuInfoService.getMskuByEmployee(userId);
    }

    @RequestMapping(value = "/merchandise/skuInfo/{fnSkuId}",method = RequestMethod.GET)
    public Result getSkuInfoByFnSkuId(@PathVariable("fnSkuId") String fnSkuId){
        try {
           return mskuInfoService.getSkuInfoByFnSkuId(fnSkuId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390,"查询失败",e.getMessage());
        }
    }

    @RequestMapping(value = "/merchandise/skuInfo/{shopId}/{mskuId}",method = RequestMethod.GET)
    @ApiOperation("根据店铺id和商品id查询出产品信息")
    public Result getSkuInfoByShopIdAndMskuId(@PathVariable("shopId") String shopId,
                                              @PathVariable("mskuId") String mskuId){
        try {
            return mskuInfoService.getSkuInfoByShopIdAndMskuId(shopId,mskuId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Result.failure(390,"查询失败",e.getMessage());
        }
    }
}




