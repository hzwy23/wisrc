package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.entity.*;
import com.wisrc.product.webapp.service.ProductClassifyDefineService;
import com.wisrc.product.webapp.service.ProductDefineService;
import com.wisrc.product.webapp.service.ProductInfoService;
import com.wisrc.product.webapp.service.ProductService;
import com.wisrc.product.webapp.utils.ExcelTools;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.ResultCode;
import com.wisrc.product.webapp.vo.GetProductInfoVO;
import com.wisrc.product.webapp.vo.Status;
import com.wisrc.product.webapp.vo.productDefine.SetBatchCostPriceVO;
import com.wisrc.product.webapp.vo.productSales.ProductSalesVO;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Api(value = "产品定义", tags = "产品定义信息")
@RestController
@RequestMapping(value = "/product")
public class ProductDefineController {

    private final Logger logger = LoggerFactory.getLogger(ProductDefineController.class);
    @Autowired
    private ProductDefineService productDefineService;
    @Autowired
    private ProductClassifyDefineService productClassifyDefineService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductClassifyDefineController productClassifyDefineController;



    @ApiOperation(value = "查询产品详细信息", notes = "查询产品详细信息")
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/define/{skuId}", method = RequestMethod.GET)
    public Result findBySkuId(@PathVariable("skuId") String skuId) {
        try {
            return Result.success(productDefineService.findBySkuId(skuId));
        } catch (Exception e) {
            logger.warn("查询产品详细信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "改变产品状态", notes = "改变产品状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "statusCd", value = "产品状态", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = "/define/{skuId}/{statusCd}", method = RequestMethod.PUT)
    public Result changeStatus(@PathVariable("skuId") String skuId, @PathVariable("statusCd") int statusCd) {
        try {
            productDefineService.changeStatus(skuId, statusCd);
            return Result.success();
        } catch (Exception e) {
            logger.warn("改变产品状态失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }


    @ApiOperation(value = "批量禁用产品信息", notes = "批量禁用产品信息")
    @RequestMapping(value = "/define/batch", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "skuId", value = "SKU编码信息,多个参数使用逗号分隔", required = true, paramType = "query")
    public Result update(@RequestParam("skuId") String[] skuId) {
        try {
            // 接受前端传送过来的sku数据
            for (String m : skuId) {
                // statusCd = 2  禁用产品
                productDefineService.changeStatus(m, 2);
            }
            return Result.success();
        } catch (Exception e) {
            logger.warn("批量禁用产品信息失败", e);
            return Result.failure(ResultCode.UPDATE_FAILED);
        }
    }

    @ApiOperation(value = "批量禁用或者启用产品信息", notes = "批量禁用或者启用产品信息")
    @RequestMapping(value = "/define/list", method = RequestMethod.PUT)
    public Result update(@RequestBody List<Status> list) {
        try {
            // 接受前端传送过来的sku数据
            for (Status m : list) {
                productDefineService.changeStatus(m.getSkuId(), m.getStatusCd());
            }
            return Result.success();
        } catch (Exception e) {
            logger.warn("批量禁用或者启用产品信息失败", e);
            return Result.failure(ResultCode.UPDATE_FAILED);
        }
    }

    @ApiOperation(value = "模糊查询产品简要信息", notes = "模糊查询产品简要信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页(null或者小于1为全查询，不分页)", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数(null或者小于1为全查询，不分页)", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "ignoreImages", value = "忽略图片信息", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字搜索，可以是skuId，也可以是产品中文名", required = false, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/define/brieffuzzy", method = RequestMethod.GET)
    public Result briefBlurry(Integer pageNum,
                              Integer pageSize,
                              GetProductInfoVO getProductInfoVO) {
        try {
            // 读取参数
            List<String> classifyCdList = new ArrayList<>();
            if (getProductInfoVO.getClassifyCd() != null) {
                //查询某一个产品分类的下级信息
                List<ProductClassifyDefineEntity> pCDlist = productClassifyDefineService.findPosterity(getProductInfoVO.getClassifyCd());
                for (ProductClassifyDefineEntity o : pCDlist) {
                    classifyCdList.add(o.getClassifyCd());
                }
            }
            return Result.success(productDefineService.fuzzyQueryNew(
                    pageNum,
                    pageSize,
                    getProductInfoVO,
                    classifyCdList
            ));
        } catch (Exception e) {
            logger.warn("模糊查询产品简要信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "模糊查询产品简要信息", notes = "模糊查询产品简要信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ignoreImages", value = "忽略图片信息", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字搜索，可以是skuId，也可以是产品中文名", required = false, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/define/machine/excel", method = RequestMethod.GET)
    public void machineExcel(GetProductInfoVO getProductInfoVO,
                            HttpServletResponse response,
                            HttpServletRequest request) {
        // 读取参数
        List<String> classifyCdList = new ArrayList<>();
        if (getProductInfoVO.getClassifyCd() != null) {
            //查询某一个产品分类的下级信息
            List<ProductClassifyDefineEntity> pCDlist = productClassifyDefineService.findPosterity(getProductInfoVO.getClassifyCd());
            for (ProductClassifyDefineEntity o : pCDlist) {
                classifyCdList.add(o.getClassifyCd());
            }
        }
        productDefineService.skuExcel(getProductInfoVO, classifyCdList, response, request);
    }

    @ApiOperation(value = "模糊查询产品简要信息", notes = "模糊查询产品简要信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ignoreImages", value = "忽略图片信息", required = false, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字搜索，可以是skuId，也可以是产品中文名", required = false, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/define/excel", method = RequestMethod.GET)
    public void excel(GetProductInfoVO getProductInfoVO,
                            HttpServletResponse response,
                            HttpServletRequest request) {
        // 读取参数
        List<String> classifyCdList = new ArrayList<>();
        if (getProductInfoVO.getClassifyCd() != null) {
            //查询某一个产品分类的下级信息
            List<ProductClassifyDefineEntity> pCDlist = productClassifyDefineService.findPosterity(getProductInfoVO.getClassifyCd());
            for (ProductClassifyDefineEntity o : pCDlist) {
                classifyCdList.add(o.getClassifyCd());
            }
        }
        Map<String, Object> skuMap = productDefineService.fuzzyQueryNew(
                1,
                99999,
                getProductInfoVO,
                classifyCdList
        );

        List<Map> skuList = ((List) skuMap.get("productData"));
        List<SkuExcelDto> skuExcel = new ArrayList<>();
        for (Map sku : skuList) {
            if (sku.get("sku") != null) {
                Map<String, Object> productResult = productInfoService.newFindBySkuId((String) sku.get("sku"));
                SkuExcelDto excel = new SkuExcelDto();

                ProductDefineEntity productDefine = (ProductDefineEntity) productResult.get("define");
                if (productDefine != null) {
                    excel.setSkuId(productDefine.getSkuId());
                    excel.setSkuNameZh(productDefine.getSkuNameZh());
                    excel.setPurchaseReferencePrice(productDefine.getPurchaseReferencePrice());
                    excel.setCostPrice(productDefine.getCostPrice());
                    try {
                        if (productDefine.getMachineFlag() == 1) {
                            excel.setMachineFlag("是" );
                        } else if (productDefine.getMachineFlag() == 0) {
                            excel.setMachineFlag("否" );
                        }

                        if (productDefine.getPackingFlag() == 1) {
                            excel.setPackingFlag("是" );
                        } else if (productDefine.getPackingFlag() == 0) {
                            excel.setPackingFlag("否" );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    excel.setSkuNameEn(productDefine.getSkuNameEn());

                }

                Map productClassify = (Map) productResult.get("classify");
                if (productClassify != null) {
                    String classifyCd = (String) productClassify.get("classifyCd");
                    Result classifyResult = productClassifyDefineController.findAncestor(classifyCd);
                    if (classifyResult.getCode() == 200) {
                        List<ProductClassifyDefineEntity> classifyList = (List) classifyResult.getData();
                        String classify = "";
                        for (int m = 0; m < classifyList.size(); m++) {
                            if (m != 0 || m != classifyList.size() - 1) {
                                classify += " / ";
                            }
                            classify += classifyList.get(m).getClassifyNameCh();
                        }
                        excel.setClassify(classify);
                    }
                }

                ProductSalesVO producSales = (ProductSalesVO) productResult.get("productSalesInfo");
                if (producSales != null) {
                    excel.setSafetyStockDays(producSales.getSafetyStockDays());
                    excel.setInternationalTransportDays(producSales.getInternationalTransportDays());
                }

                ProductDetailsInfoEntity producDetail = (ProductDetailsInfoEntity ) productResult.get("detailsInfo");
                if (producDetail != null) {
                    excel.setDescription(producDetail.getDescription());
                }

                ProductDeclareInfoEntity producDeclare = (ProductDeclareInfoEntity) productResult.get("declareInfo");
                if (producDeclare != null) {
                    excel.setCustomsNumber(producDeclare.getCustomsNumber());
                    excel.setTaxRebatePoint(producDeclare.getTaxRebatePoint());
                    excel.setIssuingOffice(producDeclare.getIssuingOffice());
                    excel.setDeclareNameZh(producDeclare.getDeclareNameZh());
                    excel.setDeclareNameEn(producDeclare.getDeclareNameEn());
                    excel.setDeclarePrice(producDeclare.getDeclarePrice());
                    excel.setOriginPlace(producDeclare.getOriginPlace());
                    excel.setMaterials(producDeclare.getMaterials());
                    excel.setTypicalUse(producDeclare.getTypicalUse());
                    excel.setBrands(producDeclare.getBrands());
                    excel.setModel(producDeclare.getModel());
                    excel.setDeclarationElements(producDeclare.getDeclarationElements());
                }

                skuExcel.add(excel);
            }
        }

        String[] title = {"库存SKU", "产品中文名", "采购参考价", "成本价", "是否需要加工", "是否需要包材", "产品分类", "产品英文名", "安全库存天数", "国际运输天数", "产品信息描述", "海关编号", "退税税点", "开票单位", "申报品名(中文)", "申报品名(英文)", "申报价值", "原产地"
                , "材质", "用途", "品牌", "型号", "其他要素"};
        try {
            ExcelTools.exportExcel("产品管理", title, skuExcel, "产品管理", response, request);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @ApiOperation(value = "批量查询产品中文名", notes = "批量查询产品中文名")
    @RequestMapping(value = "/define/product/name/batch", method = RequestMethod.GET)
    @ApiParam(name = "skuId", value = "SKU编码信息,多个参数使用逗号分隔", required = true)
    public Result getNameBatch(String[] skuId) {
        return getName(Arrays.asList(skuId));
    }

    @ApiOperation(value = "批量查询产品中文名", notes = "批量查询产品中文名")
    @RequestMapping(value = "/define/product/name/list", method = RequestMethod.POST)
    public Result getNameList(@ApiParam(name = "skuIdList", value = "SKU编码信息集合 例子[\"zyq00001\",\"zyq00002\"]", required = true) @RequestBody List<String> skuIdList) {
        return getName(skuIdList);
    }

    @ApiOperation(value = "获取全部SKU", notes = "获取全部SKU")
    @RequestMapping(value = "/define/sku", method = RequestMethod.GET)
    public Result getSku() {
        try {
            List<String> list = productDefineService.getAllSKU();
            return Result.success(list);
        } catch (Exception e) {
            logger.info("获取全部SKU", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "获取全部产品基础信息", notes = "获取全部产品基础信息")
    @RequestMapping(value = "/define/findALL", method = RequestMethod.GET)
    public Result findALL() {
        return productDefineService.findAll();
    }

    private Result getName(List<String> skuIdList) {
        try {
            Map<String, String> resultMap = new HashMap<>();
            // 接受前端传送过来的sku数据
            for (String m : skuIdList) {
                Map<String, String> map = productDefineService.getName(m);
                if (map == null) {
                    resultMap.put(m, null);
                } else {
                    resultMap.put(m, map.get("skuNameZh"));
                }
            }
            return Result.success(resultMap);
        } catch (Exception e) {
            logger.info("批量查询产品中文名过程失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "批量更新产品成本价", notes = "批量更新产品成本价")
    @RequestMapping(value = "/define/cost/price/{skuId}", method = RequestMethod.PUT)
    public Result updateCostPrice(@Valid @RequestBody SetBatchCostPriceVO vo, BindingResult bindingResult) {
        return productDefineService.updateCostPrice(vo, bindingResult);
    }

    @ApiOperation(value = "获取全部包材基础信息", notes = "获取全部产品基础信息")
    @RequestMapping(value = "/define/packing/material", method = RequestMethod.GET)
    public Result findPackingMaterial() {
        return productDefineService.findPackingMaterial();
    }


    @ApiOperation(value = "批量获取产品基础定义信息", notes = "批量获取产品基础定义信息")
    @RequestMapping(value = "/define/info/batch", method = RequestMethod.POST)
    public Result getDefineBatch(@RequestBody List<String> skuIds) {
        return productDefineService.getDefineBatch(skuIds);
    }

//    @ApiOperation(value = "批量获取产品基础定义信息", notes = "批量获取产品基础定义信息")
//    @RequestMapping(value = "/define/test", method = RequestMethod.POST)
//    public Result add(ProductDefineVO productDefineVO) {
//        ProductDefineEntity productDefineEntity = new ProductDefineEntity();
//        BeanUtils.copyProperties(productDefineVO, productDefineEntity);
//        ProductClassifyDefineEntity productClassifyDefineEntity = productClassifyDefineService.getRootClassify(productDefineEntity.getClassifyCd());
//        if (productClassifyDefineEntity == null) {
//            return new Result(9999, "无法获取一级分类的缩写", null);
//        } else {
//            productDefineEntity.setCreateUser("admin");
//            Map<String, String> map = productDefineService.insert(productDefineEntity, productClassifyDefineEntity.getClassifyShortName());
//            return Result.success(map);
//        }
//    }

//    @ApiOperation(value = "判断是否更新10.56", notes = "判断是否更新10.56")
//    @RequestMapping(value = "/define/test", method = RequestMethod.POST)
//    public Result test() {
//        return Result.success();
//    }
}


