package com.wisrc.merchandise.controller;


import com.wisrc.merchandise.entity.BasicPlatformInfoEntity;
import com.wisrc.merchandise.service.BasicPlatformInfoService;
import com.wisrc.merchandise.utils.Crypto;
import com.wisrc.merchandise.utils.PageData;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.utils.Time;
import com.wisrc.merchandise.vo.PlatformInfoVO;
import com.wisrc.merchandise.vo.swagger.PlatformInfoResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Api(tags = "平台信息管理")
@RequestMapping(value = "/operation")
public class BasicPlatformInfoController {

    private final Logger logger = LoggerFactory.getLogger(BasicPlatformInfoController.class);

    @Autowired
    private BasicPlatformInfoService basicPlatformInfoService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "platform", value = "平台名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "siteName", value = "站点", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "api", value = "API接口地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "marketplaceId", value = "MarketplaceId", required = true, dataType = "String", paramType = "query")
    })
    @ApiOperation(value = "新增平台信息", notes = "平台名称和站点组合成唯一索引，每新增一条平台信息，都会为这条数据生成一个平台ID. <br/>" +
            "平台ID具有唯一性，通过平台ID，可以获取，修改平台的详细信息")
    @RequestMapping(value = "/platform", method = RequestMethod.POST)
    public Result add(PlatformInfoVO vo,
                      @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        BasicPlatformInfoEntity ele = toEntity(vo);
        ele.setPlatId(Crypto.sha(ele.getPlatName(), ele.getPlatSite()));
        ele.setModifyUser(userId);
        ele.setStatusCd(1);
        try {
            return basicPlatformInfoService.add(ele);
        } catch (DuplicateKeyException e) {
            return new Result(400, "站点已存在，请重新输入", vo);
        }
    }

    @ApiOperation(value = "获取平台细信息", notes = "根据平台ID，获取指定平台的详细信息<br/>" +
            "1.如果平台ID存在，则返回这个平台的详细信息<br/>" +
            "2.如果平台ID不存在，则返回提示信息，提示需要查询的平台ID不存在")
    @ApiImplicitParam(name = "platId", value = "平台ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/platform/{platId}", method = RequestMethod.GET)
    public Result getDetails(@PathVariable("platId") String platId) {
        BasicPlatformInfoEntity res = basicPlatformInfoService.findById(platId);
        if (res == null) {
            return Result.failure(500, "平台ID不存在", "platform id [" + platId + "] is not found");
        }
        return Result.success(PlatformInfoVO.toVO(res));
    }

    @ApiOperation(value = "编辑平台信息", notes = "平台ID不能编辑，其余字段可以编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platId", value = "平台ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "platform", value = "平台名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "siteName", value = "站点", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "api", value = "API接口地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "平台状态", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "marketplaceId", value = "MarketplaceId", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/platform", method = RequestMethod.PUT)
    public Result update(PlatformInfoVO vo,
                         @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "--") @ApiIgnore String userId) {
        BasicPlatformInfoEntity ele = toEntity(vo);
        ele.setModifyUser(userId);
        try {
            basicPlatformInfoService.updateById(ele);
        } catch (DuplicateKeyException e) {
            return new Result(400, "站点已存在，请重新输入", vo);
        }
        return Result.success("编辑平台信息成功");
    }

    @RequestMapping(value = "/platform/status", method = RequestMethod.PUT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platId", value = "平台ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "状态编码", required = true, dataType = "int", paramType = "query")
    })
    @ApiOperation(value = "修改平台状态信息", notes = "平台状态编码，1：正常，2：禁用")
    public Result chageStatus(@RequestParam("platId") String platId,
                              @RequestParam("statusCd") int statusCd,
                              @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {

        BasicPlatformInfoEntity ele = new BasicPlatformInfoEntity();
        ele.setModifyUser(userId);
        ele.setModifyTime(Time.getCurrentDateTime());

        basicPlatformInfoService.changeStatus(platId, statusCd);
        return Result.success("修改状态信息成功");
    }

    @ApiOperation(value = "查询所有的平台信息", notes = "当pageNum或pageSize为无效数字时，将会查询全部的数据<br/>" +
            "当pageNum和pageSize为有效数字时，将会分页查询.<br/>" +
            "当返回值中total和pages为-1时，表示默认全表查询。<br/>" +
            "platformName 为空时，表示在查询的时候忽略这个参数，如果这个参数有值，则使用模糊查询来匹配<br/>" +
            "statusCd 为空时，表示在查询的时候忽略这个参数，如果这个参数有值，则使用精准匹配来查询，状态编码只有两种情况，1：正常，2：禁用", response = PlatformInfoResponseModel.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "platformName", value = "平台编码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "状态编码", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/platform", method = RequestMethod.GET)
    public Result findAll(@RequestParam(value = "pageNum", required = false) String pageNum,
                          @RequestParam(value = "pageSize", required = false) String pageSize,
                          @RequestParam(value = "platformName", required = false) String platformName,
                          @RequestParam(value = "statusCd", required = false) String statusCd
    ) {
        try {
            int size = Integer.valueOf(pageSize);
            int num = Integer.valueOf(pageNum);

            if (platformName != null || statusCd != null) {
                LinkedHashMap list = basicPlatformInfoService.search(num, size, platformName, statusCd);
                return Result.success(list);
            }

            LinkedHashMap list = basicPlatformInfoService.findAll(num, size);
            return Result.success(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            List<PlatformInfoVO> result = new ArrayList<>();
            List<BasicPlatformInfoEntity> mlist = basicPlatformInfoService.findAll();
            for (BasicPlatformInfoEntity m : mlist) {
                result.add(PlatformInfoVO.toVO(m));
            }
            return Result.success(200, "分页值格式不正确，默认查询全部数据", PageData.pack(-1, -1, "platformInfoList", result));
        }
    }

    @ApiOperation(value = "查询指定平台下素有的站点", notes = "一个平台有多个站点，通过指定平台名称，可以获取这个平台下所有的站点信息")
    @ApiImplicitParam(name = "platName", value = "平台名称", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/platform/{platName}/site/list", method = RequestMethod.GET)
    public Result getSiteList(@PathVariable("platName") String platName) {
        List<BasicPlatformInfoEntity> mlist = basicPlatformInfoService.findSiteById(platName);
        if (mlist == null) {
            return Result.success("指定的平台名称【" + platName + "】不存在");
        }
        List<PlatformInfoVO> result = new ArrayList<>();
        for (BasicPlatformInfoEntity m : mlist) {
            result.add(PlatformInfoVO.toVO(m));
        }
        return Result.success(result);
    }

    private BasicPlatformInfoEntity toEntity(PlatformInfoVO vo) {
        BasicPlatformInfoEntity be = new BasicPlatformInfoEntity();
        BeanUtils.copyProperties(vo, be);
        be.setModifyTime(Time.getCurrentDateTime());
        be.setApiUrl(vo.getApi());
        be.setMarketPlaceId(vo.getMarketplaceId());
        be.setPlatName(vo.getPlatform());
        be.setPlatSite(vo.getSiteName());
        return be;
    }
}
