package com.wisrc.basic.controller;

import com.wisrc.basic.vo.brandInfo.AddBrandInfoVO;
import com.wisrc.basic.vo.brandInfo.BrandInfoStatuCdVO;
import com.wisrc.basic.vo.brandInfo.SetBrandInfoVO;
import com.wisrc.basic.entity.BrandInfoEntity;
import com.wisrc.basic.service.BrandInfoService;
import com.wisrc.basic.utils.Result;
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

@RestController
@Api(tags = "品牌管理")
@RequestMapping(value = "/basic")
public class BrandInfoController {
    private final Logger logger = LoggerFactory.getLogger(BrandModifyHistoryController.class);
    @Autowired
    private BrandInfoService brandInfoService;


    @ApiOperation(value = "新增品牌信息", notes = "新增品牌信息")
    @ApiImplicitParam(name = "X-AUTH-ID", value = "用户信息", required = true, dataType = "String", paramType = "header")
    @RequestMapping(value = "/brand/info", method = RequestMethod.POST)
    public Result add(@Valid AddBrandInfoVO vo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        if (bindingResult.hasErrors()) {
            logger.error("新增品牌失败，参数格式不正确。{}", vo.toString());
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        BrandInfoEntity entity = new BrandInfoEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setCreateUser(userId);
        entity.setModifyUser(userId);
        logger.info("新增品牌信息：{}", vo.toString());
        return brandInfoService.insert(entity);
    }


    @ApiOperation(value = "更新品牌信息", notes = "更新品牌信息")
    @ApiImplicitParam(name = "X-AUTH-ID", value = "用户信息", required = true, dataType = "String", paramType = "header")
    @RequestMapping(value = "/brand/info", method = RequestMethod.PUT)
    public Result update(@Valid SetBrandInfoVO vo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        BrandInfoEntity entity = new BrandInfoEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setModifyUser(userId);
        logger.info("跟新品牌信息，{}", vo);
        return brandInfoService.update(entity);
    }

    @ApiOperation(value = "根据品牌名分页模糊查询品牌信息", notes = "根据品牌名分页模糊查询品牌信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandName", value = "产品分类编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", required = false, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/brand/info/fuzzy", method = RequestMethod.GET)
    public Result update(Integer pageNum, Integer pageSize, String brandName) {
        BrandInfoEntity entity = new BrandInfoEntity();
        entity.setBrandName(brandName);
        return brandInfoService.fuzzyFind(pageNum, pageSize, entity);
    }

    @RequestMapping(value = "/brand/info/{brandId}", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "品牌编码", required = true, dataType = "String", paramType = "path"),
    })
    @ApiOperation(value = "根据ID查询品牌信息", notes = "根据ID查询品牌信息")
    public Result findById(@PathVariable("brandId") String brandId) {
        BrandInfoEntity entity = new BrandInfoEntity();
        entity.setBrandId(brandId);
        return brandInfoService.findById(entity);
    }

    @RequestMapping(value = "/brand/restrict", method = RequestMethod.PUT)
    @ApiOperation(value = "禁用启用品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "品牌编码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "状态", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(value = "用户ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    })
    public Result restrict(@RequestHeader("X-AUTH-ID") String loginUser, @Valid BrandInfoStatuCdVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("禁用品牌失败，参数格式不正确，{}", vo.toString());
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        BrandInfoEntity entity = new BrandInfoEntity();
        entity.setBrandId(vo.getBrandId());
        entity.setStatusCd(vo.getStatusCd());
        entity.setModifyUser(loginUser);
        logger.info("禁用品牌：{}", vo.toString());
        return brandInfoService.restrict(entity);
    }

}
