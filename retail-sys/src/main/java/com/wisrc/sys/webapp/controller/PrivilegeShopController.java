package com.wisrc.sys.webapp.controller;

import com.wisrc.sys.webapp.vo.privilegeShop.BatchPrivilegeShopMSKUVO;
import com.wisrc.sys.webapp.entity.SysPrivilegeShopEntity;
import com.wisrc.sys.webapp.service.PrivilegeShopService;
import com.wisrc.sys.webapp.utils.Crypto;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import com.wisrc.sys.webapp.utils.Time;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * modify time 2018-07-09
 * modify user zhanwei_huang
 * Reason: adjust shopId to commodityId
 */

@RestController
@Api(tags = "授权店铺信息")
@RequestMapping(value = "/sys")
public class PrivilegeShopController {
    private final Logger logger = LoggerFactory.getLogger(PrivilegeShopController.class);

    @Autowired
    private PrivilegeShopService storesService;


    /**
     * 修改店铺ID为商品ID
     * shopId to commodityId
     */
    @RequestMapping(value = "/auth/store", method = RequestMethod.POST)
    @ApiOperation(value = "批量店铺MSKU授权")
    public Result addPrivilegeShop(@RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String loginId,
                                   @Valid BatchPrivilegeShopMSKUVO mskuvo,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        String privilegeCd = mskuvo.getAuthId();
        List<SysPrivilegeShopEntity> list = new ArrayList<>();
        for (String commodityId : mskuvo.getCommodityIdList()) {
            SysPrivilegeShopEntity entity = new SysPrivilegeShopEntity();
            entity.setPrivilegeCd(privilegeCd);
            entity.setCreateUser(loginId);
            entity.setCreateTime(Time.getCurrentTime());
            entity.setCommodityId(commodityId);
            entity.setUuid(Crypto.sha(privilegeCd, commodityId));
            list.add(entity);
        }
        return storesService.insert(list);
    }

    @ApiOperation(value = "撤销店铺授权", notes = "撤销店铺授权")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "commodityId", value = "店铺MSKU编码", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "authId", value = "授权编码", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = "/auth/store/{authId}/{commodityId}", method = RequestMethod.DELETE)
    public Result deletePrivilegeShop(@RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String loginId,
                                      @PathVariable("commodityId") String commodityId,
                                      @PathVariable("authId") String authId) {
        if (commodityId == null || commodityId.isEmpty()) {
            return Result.failure(ResultCode.UNKNOW_ERROR, "店铺编码为空，无法删除");
        }
        return storesService.deletePrivilegeShop(commodityId, authId);
    }


    @RequestMapping(value = "/auth/store", method = RequestMethod.GET)
    @ApiOperation(value = "通过权限代码查找授权店铺列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "platformName", value = "平台名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "storeName", value = "店铺名", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "mskuId", value = "MSKU编码", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", required = true, dataType = "int", paramType = "query")
    })
    public Result getPrivilegeShop(@RequestHeader("X-AUTH-ID") String loginId,
                                   @RequestParam("pageNum") int pageNum,
                                   @RequestParam("pageSize") int pageSize,
                                   @RequestParam("authId") String authId,
                                   String platformName,
                                   String mskuId,
                                   String storeName) {
        return storesService.getPrivilegeShop(pageNum, pageSize, authId, platformName, storeName, mskuId);
    }

    @RequestMapping(value = "/auth/store/unauth", method = RequestMethod.GET)
    @ApiOperation(value = "通过权限代码查找未授权店铺")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "authId", value = "数据权限编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "platformName", value = "平台名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "storeName", value = "店铺名", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "mskuId", value = "MSKU编码", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", required = true, dataType = "int", paramType = "query")
    })
    public Result getPrivilegeShopUnauth(@RequestHeader("X-AUTH-ID") String loginId,
                                         @RequestParam("pageNum") int pageNum,
                                         @RequestParam("pageSize") int pageSize,
                                         @RequestParam("authId") String authId,
                                         String platformName,
                                         String mskuId,
                                         String storeName) {
        return storesService.getPrivilegeShopUnauth(pageNum, pageSize, authId, platformName, storeName, mskuId);
    }

    @ApiOperation(value = "获取用户拥有的MSKU信息")
    @RequestMapping(value = "/commodity/privilege", method = RequestMethod.POST)
    public Result searchUserCommodityPrivilege(@RequestHeader("X-AUTH-ID") String userId,
                                               @RequestParam(value = "commodityIdList", required = false) String[] commodityIdList,
                                               @RequestParam(value = "roleIdList", required = false) String[] roleIdList,
                                               @RequestParam(value = "privilegeCdList", required = false) String[] privilegeCdList,
                                               @RequestParam(value = "deptCd", required = false) String deptCd,
                                               @RequestParam(value = "positionCdList", required = false) String[] positionCdList,
                                               @RequestParam(value = "employeeIdList", required = false) String[] employeeIdList) {
        return storesService.searchUserCommodity(userId, commodityIdList, roleIdList, privilegeCdList, deptCd, positionCdList, employeeIdList);
    }
}
