package com.wisrc.merchandise.controller;


import com.wisrc.merchandise.entity.BasicPlatformInfoEntity;
import com.wisrc.merchandise.entity.BasicShopInfoEntity;
import com.wisrc.merchandise.service.BasicPlatformInfoService;
import com.wisrc.merchandise.service.BasicShopInfoService;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.utils.Time;
import com.wisrc.merchandise.utils.Toolbox;
import com.wisrc.merchandise.vo.ShopInfoVO;
import com.wisrc.merchandise.vo.swagger.ShopInfoResponseModel;
import com.wisrc.merchandise.vo.swagger.ShopInfoResponseModelDetails;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;

@RestController
@Api(tags = "店铺信息管理")
@RequestMapping(value = "/operation")
public class BasicShopInfoController {

    @Autowired
    private BasicShopInfoService basicShopInfoService;

    @Autowired
    private BasicPlatformInfoService basicPlatformInfoService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回值数据结构，请参考Example Value。 " +
                    "当返回值中total和pages为-1时，表示全表查询，未分页",
                    response = ShopInfoResponseModel.class),
            @ApiResponse(code = 401, message = "权限不足", response = Result.class),
            @ApiResponse(code = 404, message = "请求的资源不存在", response = Result.class)
    })
    @ApiOperation(value = "获取店铺信息列表",
            notes = "<strong>模糊查询</strong><br/> " +
                    "当 平台名称，店铺编码，店铺名称，店铺状态 中的某一个字段不为空时，将会进行模糊查询 <br/>" +
                    "<strong>精准匹配</strong><br/> " +
                    "1.当pageNum或者pageSize为空时，将会查询全部店铺信息，<br/> " +
                    "2.如果pageNum和pageSize是数字，将会进行分页查询，<br/>" +
                    "3.如果pageSize或pageNum是非空的非数字，则会查询全部数据，<br/>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", defaultValue = "1", value = "页码", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "pageSize", defaultValue = "5", value = "每页条数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "platformName", value = "平台名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "shopId", value = "店铺编码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "shopName", value = "店铺名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "statusCd", value = "店铺状态", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public Result findAll(@RequestParam(value = "pageNum", required = false) String pageNum,
                          @RequestParam(value = "pageSize", required = false) String pageSize,
                          @RequestParam(value = "platformName", required = false) String platformName,
                          @RequestParam(value = "shopId", required = false) String shopId,
                          @RequestParam(value = "statusCd", required = false) String statusCd,
                          @RequestParam(value = "shopName", required = false) String shopName) {

        // 如果platfornName、shopId、shopName其中一个字段值不为空，则进行搜索查询
        if (platformName != null || shopId != null || shopName != null || statusCd != null) {
            try {
                int num = Integer.valueOf(pageNum);
                int size = Integer.valueOf(pageSize);
                return Result.success(basicShopInfoService.searchAndPage(platformName, shopId, shopName, statusCd, num, size));
            } catch (Exception e) {
                return Result.success(basicShopInfoService.search(platformName, shopId, shopName, statusCd));
            }
        }

        // 如果参数为空，则查询直接查询全部
        if (pageNum == null || pageSize == null) {
            return Result.success(basicShopInfoService.findAll());
        }

        try {
            int num = Integer.valueOf(pageNum);
            int size = Integer.valueOf(pageSize);
            LinkedHashMap data = basicShopInfoService.findAll(num, size);
            return Result.success(data);
        } catch (NumberFormatException e) {
            return Result.success(200, "pageNum或pageSize值异常，分页值只能是数字，默认查询全部店铺数据", basicShopInfoService.findAll());
        }
    }

    @ApiOperation(value = "查询店铺详细信息", notes = "根据店铺ID，获取店铺的详细信息", response = ShopInfoResponseModelDetails.class)
    @RequestMapping(value = "/shop/{shopId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("shopId") String shopId) {
        BasicShopInfoEntity dt = basicShopInfoService.findById(shopId);
        ShopInfoVO vo = new ShopInfoVO();
        BeanUtils.copyProperties(dt, vo);
        vo.setStoreName(dt.getShopName());
        vo.setUpdateTime(dt.getModifyTime());
        vo.setSellerNo(dt.getShopOwnerId());
        vo.setKey(dt.getSecurityKey());
        vo.setAws(dt.getAwsAccessKey());

        BasicPlatformInfoEntity be = basicPlatformInfoService.findById(dt.getPlatId());
        vo.setPlatform(be.getPlatName());
        vo.setSiteName(be.getPlatSite());
        return Result.success(vo);
    }

    @ApiOperation(value = "修改店铺状态", notes = "改变店铺状态，1：正常，2：禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "状态编码", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/shop/status", method = RequestMethod.PUT)
    public Result changeStatus(@RequestParam("shopId") String shopId, @RequestParam("statusCd") int statusCd) {
        basicShopInfoService.changeStatus(shopId, statusCd);
        return Result.success("更新状态成功");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopId", value = "店铺编码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platId", value = "平台ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "店铺状态", required = true, dataType = "int", paramType = "query")
    })
    @ApiOperation(value = "更新店铺信息", notes = "更新店铺信息过程，店铺ID不能编辑. 更新程序执行过程中，将会更新店铺ID对应行的其他字段值<br/>" +
            "1.如果在提交更新请求的过程中，有字段值没有设置值，则将会被职位空<br/>" +
            "2.如果指定的店铺ID不存在，则不会更新任何数据<br/>" +
            "<strong>字段介绍</strong><br/>" +
            "statusCd：1 正常，2 禁用<br/>" +
            "platId： 填充平台ID，如果平台信息表中还没有信息，需要先录入平台信息")
    @RequestMapping(value = "/shop", method = RequestMethod.PUT)
    public Result update(ShopInfoVO ele, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        BasicShopInfoEntity bsie = args(ele);
        bsie.setModifyUser(userId);
        bsie.setModifyTime(Time.getCurrentDateTime());
        bsie.setShopId(ele.getShopId());
        basicShopInfoService.update(bsie);
        return Result.success("编辑店铺信息成功");
    }

    @ApiOperation(value = "新增店铺信息", notes = "添加店铺信息时，需要有平台ID，可以通过查询平台名和站点的方法获取平台ID<br/>" +
            "平台ID是唯一编码，店铺管理信息中，平台ID，店铺名称，卖家编号，这三个字段组合起来具有唯一性。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeName", value = "店铺名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sellerNo", value = "卖家编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "key", value = "密钥", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "aws", value = "AWS访问键编码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platId", value = "平台编码", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/shop", method = RequestMethod.POST)
    public Result add(ShopInfoVO ele,
                      @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        BasicShopInfoEntity bsie = args(ele);
        bsie.setModifyUser(userId);
        bsie.setModifyTime(Time.getCurrentDateTime());
        return basicShopInfoService.add(bsie);
    }

    private BasicShopInfoEntity args(ShopInfoVO ele) {
        BasicShopInfoEntity bsie = new BasicShopInfoEntity();
        BeanUtils.copyProperties(ele, bsie);

        bsie.setPlatId(ele.getPlatId());
        bsie.setShopName(ele.getStoreName());
        bsie.setStatusCd(1);
        bsie.setAwsAccessKey(ele.getAws());
        bsie.setSecurityKey(ele.getKey());
        bsie.setShopOwnerId(ele.getSellerNo());
        bsie.setShopId(Toolbox.randomUUID());
        return bsie;
    }

    @RequestMapping(value = "/shop/warehouse", method = RequestMethod.GET)
    @ApiOperation(value = "根据店铺ID获取平台对应的FBA仓库")
    public Result getWarehouseByShop(@RequestParam(value = "shopId") String shopId) {
        try {
            String warehouseId = basicShopInfoService.getWarehouseByShop(shopId);
            return Result.success(warehouseId);
        } catch (Exception e) {
            return Result.failure(300, e.getMessage(), "该店铺对应的平台没有仓库");
        }

    }
}
