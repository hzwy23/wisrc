package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.entity.ProductClassifyDefineEntity;
import com.wisrc.product.webapp.service.ProductClassifyDefineService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.ResultCode;
import com.wisrc.product.webapp.vo.FuzzyProductClassifyDefineVO;
import com.wisrc.product.webapp.vo.productInfo.add.AddProductClassifyDefineVO;
import com.wisrc.product.webapp.vo.set.SetProductClassifyDefineVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(value = "产品分类", tags = "产品分类管理")
@RequestMapping(value = "/product")
public class ProductClassifyDefineController {
    private final Logger logger = LoggerFactory.getLogger(ProductClassifyDefineController.class);
    @Autowired
    private ProductClassifyDefineService productClassifyDefineService;

    @RequestMapping(value = "/classify/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有产品分类信息", notes = "获取所有产品分类信息")
    public Result findAll() {
        try {
            List<ProductClassifyDefineEntity> list = productClassifyDefineService.findAll();
            return Result.success(list);
        } catch (Exception e) {
            logger.warn("获取所有产品分类信息失败", e);
            return new Result(ResultCode.FIND_FAILED);
        }
    }

    @RequestMapping(value = "/classify/info/{classifyCd}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "classifyCd", value = "产品分类编码", required = true, dataType = "String", paramType = "path")
    @ApiOperation(value = "查询某一个产品分类的信息", notes = "查询某一个产品分类的信息")
    public Result findById(@PathVariable("classifyCd") String classifyCd) {
        try {
            Map<String, Object> map = productClassifyDefineService.findById(classifyCd);
            return Result.success(map);
        } catch (Exception e) {
            logger.warn("查询某一个产品分类的信息", e);
            return new Result(ResultCode.FIND_FAILED);
        }
    }

    @RequestMapping(value = "/classify/info/posterity/{classifyCd}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "classifyCd", value = "产品分类编码", required = true, dataType = "String", paramType = "path")
    @ApiOperation(value = "查询某一个产品分类的下级信息", notes = "查询某一个产品分类的下级信息")
    public Result posterity(@PathVariable("classifyCd") String classifyCd) {
        try {
            List<ProductClassifyDefineEntity> list = productClassifyDefineService.findPosterity(classifyCd);
            return Result.success(list);
        } catch (Exception e) {
            logger.warn("查询某一个产品分类的下级信息", e);
            return new Result(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "新增产品分类信息", notes = "新增产品分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classifyNameCh", value = "产品分类中文名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "classifyNameEn", value = "产品分类英文名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "levelCd", value = "产品分类层级", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "parentCd", value = "产品分类上级节点编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "classifyShortName", value = "产品分类缩写", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "X-AUTH-ID", value = "用户信息", required = true, dataType = "String", paramType = "header")
    })
    @RequestMapping(value = "/classify/info", method = RequestMethod.POST)
    public Result add(AddProductClassifyDefineVO ele, @RequestHeader("X-AUTH-ID") String userId) {
        try {
            ProductClassifyDefineEntity productClassifyDefineEntity = new ProductClassifyDefineEntity();
            BeanUtils.copyProperties(ele, productClassifyDefineEntity);
            productClassifyDefineEntity.setCreateUser(userId);
            productClassifyDefineEntity.setCreateUser(userId);
            Map<String, String> map = productClassifyDefineService.insert(productClassifyDefineEntity);
            if (map.get("error").equals("1")) {
                return new Result(9999, map.get("errorMsg"), null);
            } else {
                return Result.success();
            }
        } catch (Exception e) {
            logger.warn("新增产品分类信息失败", e);
            return new Result(ResultCode.CREATE_FAILED);
        }
    }


    /**
     * （仅支持修改中文名与英文名）
     *
     * @param ele
     * @param userId
     * @return
     */
    @ApiOperation(value = "更新产品分类信息（仅支持修改中文名与英文名）", notes = "更新产品分类信息（仅支持修改中文名与英文名）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classifyCd", value = "产品分类编码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "classifyNameCh", value = "产品分类中文名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "classifyNameEn", value = "产品分类英文名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "X-AUTH-ID", value = "用户信息", required = true, dataType = "String", paramType = "header")
    })
    @RequestMapping(value = "/classify/info", method = RequestMethod.PUT)
    public Result updatePart(SetProductClassifyDefineVO ele, @RequestHeader("X-AUTH-ID") String userId) {
        try {
            ProductClassifyDefineEntity productClassifyDefineEntity = new ProductClassifyDefineEntity();
            BeanUtils.copyProperties(ele, productClassifyDefineEntity);
            productClassifyDefineEntity.setCreateUser(userId);
            Map<String, String> map = productClassifyDefineService.updatePart(productClassifyDefineEntity);
            if (map.get("error").equals("1")) {
                return new Result(9999, map.get("errorMsg"), null);
            } else {
                return Result.success();
            }
        } catch (Exception e) {
            logger.warn("更新产品分类信息失败", e);
            return Result.failure(ResultCode.UPDATE_FAILED);
        }
    }


    @ApiOperation(value = "删除产品分类信息", notes = "删除用户产品分类信息")
    @ApiImplicitParam(name = "classifyCd", value = "产品分类编码", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/classify/info/{classifyCd}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("classifyCd") String classifyCd) {
        try {
            logger.info("删除的产品分类节点信息是：{}", classifyCd);
            Map<String, String> map = productClassifyDefineService.deleteSafe(classifyCd);
            if (map.get("error").equals("1")) {
                return new Result(9999, map.get("errorMsg"), null);
            } else {
                return Result.success();
            }
        } catch (Exception e) {
            logger.warn("删除产品分类信息失败", e);
            return Result.failure(ResultCode.DELETE_FAILED);
        }
    }

    @ApiOperation(value = "模糊查询产品家族分类信息", notes = "模糊查询产品家族分类信息")
    @RequestMapping(value = "/classify/fuzzyQueryFamily", method = RequestMethod.GET)
    public Result fuzzyQueryFamily(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, FuzzyProductClassifyDefineVO ele) {
        try {
            Map<String, Object> map = productClassifyDefineService.fuzzyQueryFamilyNew(pageNum, pageSize, ele);
            return Result.success(map);
        } catch (Exception e) {
            logger.warn("模糊查询产品家族分类信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "查询单级产品分类信息", notes = "查询单级产品分类信息")
    @ApiImplicitParam(name = "levelCd", value = "产品分类层级", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/classify/info/level/{levelCd}", method = RequestMethod.GET)
    public Result findLevel(@PathVariable("levelCd") int levelCd) {
        try {
            logger.info("查询单级产品节点信息是：{}", levelCd);
            List<ProductClassifyDefineEntity> list = productClassifyDefineService.findLevel(levelCd);
            return Result.success(list);
        } catch (Exception e) {
            logger.warn("查询单级产品分类信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @RequestMapping(value = "/classify/info/ancestor/{classifyCd}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "classifyCd", value = "产品分类编码", required = true, dataType = "String", paramType = "path")
    @ApiOperation(value = "查询某一个产品分类及其直系分类", notes = "查询某一个产品分类的直系分类")
    public Result findAncestor(@PathVariable("classifyCd") String classifyCd) {
        return productClassifyDefineService.findItselfAndAncestor(classifyCd);
    }


}
