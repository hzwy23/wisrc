package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.service.ProductImagesService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "产品图片", tags = "产品图片")
@RestController
@RequestMapping(value = "/product")
public class ProductImagesController {

    private final Logger logger = LoggerFactory.getLogger(ProductImagesController.class);
    @Autowired
    private ProductImagesService productImagesService;


    //不对外单独提供该接口
//    @ApiOperation(value = "删除产品的所有图片", notes = "删除产品的所有图片")
//    @ApiImplicitParam(name = "skuId", value = "SKU库存skuId", required = true, dataType = "String", paramType = "path")
//    @RequestMapping(value = "/images/deleteBySkuId/{skuId}", method = RequestMethod.DELETE)
//    public Result deleteBySkuId(@PathVariable("skuId") String skuId) {
//        try {
//            productImagesService.deleteBySkuId(skuId);
//            return Result.success();
//        } catch (Exception e) {
//            logger.warn("删除产品的所有图片失败", e);
//            return Result.failure(ResultCode.DELETE_FAILED);
//        }
//    }


    @ApiOperation(value = "查询产品的所有图片", notes = "查询产品的所有图片")
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/images/{skuId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("skuId") String skuId) {
        try {
            return Result.success(productImagesService.findBySkuId(skuId));
        } catch (Exception e) {
            logger.warn("查询产品的所有图片失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    //只要skuId与图片Url,图片类型,简略部分数据
    @ApiOperation(value = "批量查询产品图片信息", notes = "批量查询产品图片信息")
    @RequestMapping(value = "/images/batch", method = RequestMethod.GET)
    @ApiParam(name = "skuId", value = "SKU编码信息,多个参数使用逗号分隔", required = true)
    public Result getImageBatch(@RequestParam("skuId") String[] skuId) {
        try {
            List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
            // 接受前端传送过来的sku数据
            for (String m : skuId) {
                Map map = new HashMap();
                // 只要skuId与图片Url,图片类型,简略部分数据
                List<Map<String, String>> list = productImagesService.findBySkuISimple(m);
                map.put("skuId", m);
                map.put("image", list);
                resultList.add(map);
            }
            return Result.success(resultList);
        } catch (Exception e) {
            logger.warn("批量查询产品图片信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "批量查询产品图片信息", notes = "批量查询产品图片信息")
    @RequestMapping(value = "/images/list", method = RequestMethod.POST)
    public Result getImageList(@ApiParam(name = "skuId", value = "SKU编码信息集合 [\"skuId\",\"skuId\"]", required = true) @RequestBody List<String> skuId) {
        try {
            List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
            // 接受前端传送过来的sku数据
            for (String m : skuId) {
                Map map = new HashMap();
                List<Map<String, String>> imageList = productImagesService.findBySkuISimple(m);
                map.put("skuId", m);
                map.put("image", imageList);
                resultList.add(map);
            }
            return Result.success(resultList);
        } catch (Exception e) {
            logger.warn("批量查询产品图片信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @RequestMapping(value = "/images/all",method = RequestMethod.GET)
    @ApiOperation("查询所有产品的图片")
    public Result getAllSkuImages(){
        try {
            return productImagesService.getAllSkuImgs();
        } catch (Exception e) {
            e.printStackTrace();
            return  Result.failure(390,"查询失败",e.getMessage());
        }
    }
}
