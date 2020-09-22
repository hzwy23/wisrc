package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.controller.ProductDefineController;
import com.wisrc.product.webapp.dao.ProductInfoDao;
import com.wisrc.product.webapp.service.*;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.ResultCode;
import com.wisrc.product.webapp.utils.Time;
import com.wisrc.product.webapp.vo.declareLabel.add.AddDeclareLabelVO;
import com.wisrc.product.webapp.vo.declareLabel.set.SetDeclareLabelVO;
import com.wisrc.product.webapp.vo.declareLabel.show.ShowDeclareLabelVO;
import com.wisrc.product.webapp.vo.productAccessory.add.AddProductAccessoryVO;
import com.wisrc.product.webapp.vo.productAccessory.set.SetProductAccessoryVO;
import com.wisrc.product.webapp.vo.productInfo.NewAddProductInfoVO;
import com.wisrc.product.webapp.vo.productInfo.NewSetProductInfoVO;
import com.wisrc.product.webapp.vo.productInfo.get.BatchSkuId;
import com.wisrc.product.webapp.vo.productPackingInfo.ProductPackingInfoVO;
import com.wisrc.product.webapp.vo.productSales.ProductSalesVO;
import com.wisrc.product.webapp.vo.wms.ProductInfoVO;
import com.wisrc.product.webapp.vo.wms.ProductPackInfoVO;
import com.wisrc.product.webapp.entity.*;
import com.wisrc.product.webapp.vo.productInfo.add.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.*;

@Service
public class ProductInfoServiceImp implements ProductInfoService {
    private final Logger logger = LoggerFactory.getLogger(ProductDefineController.class);
    @Autowired
    private ProductInfoDao productInfoDao;
    @Autowired
    private ProductClassifyDefineService productClassifyDefineService;
    @Autowired
    private ProductDeclareInfoService productDeclareInfoService;
    @Autowired
    private ProductDeclareLabelService productDeclareLabelService;
    @Autowired
    private ProductDefineService productDefineService;
    @Autowired
    private ProductDetailsInfoService productDetailsInfoService;
    @Autowired
    private ProductImagesService productImagesService;
    @Autowired
    private ProductMachineInfoService productMachineInfoService;
    @Autowired
    private ProductSpecificationsInfoService productSpecificationsInfoService;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;
    @Autowired
    private ProductAccessoryService productAccessoryService;
    @Autowired
    private ProductPackingInfoService productPackingInfoService;
    @Autowired
    private ProductSalesInfoService productSalesInfoService;


    @Override
    @Transactional(transactionManager = "retailProductTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void delete(String skuId) {

        //删除时候  某个产品是其他产品的依赖加工产品怎么办？？？

        productDeclareLabelService.deleteBySkuId(skuId);
        productDeclareInfoService.delete(skuId);
        productDetailsInfoService.delete(skuId);
        productMachineInfoService.delete(skuId);
        productImagesService.deleteBySkuId(skuId);
        productSpecificationsInfoService.delete(skuId);
        productModifyHistoryService.deleteBySkuId(skuId);
        productDefineService.delete(skuId);

    }


    /**
     * 获取根节点对象
     *
     * @param classifyCd
     * @return
     */
    private ProductClassifyDefineEntity getRootClassify(String classifyCd) {
        // 获取所有的产品分类信息
        List<ProductClassifyDefineEntity> list = productInfoDao.findAllProductClassifyDefineEntity();
        if (list == null) {
            return null;
        } else {
            //找根节点
            ProductClassifyDefineEntity result = new ProductClassifyDefineEntity();
            ProductClassifyDefineEntity result2 = getRoot(list, classifyCd, result);
            return result2;
        }
    }

    /**
     * 递归找根节点
     */
    private ProductClassifyDefineEntity getRoot(List<ProductClassifyDefineEntity> list, String
            classifyCd, ProductClassifyDefineEntity result) {
        if (classifyCd.equals("-1") || classifyCd == null || classifyCd.isEmpty() || list.size() == 0 || list == null) {
            return result;
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getClassifyCd().equals(classifyCd)) {
                    result = list.get(i);
                    break;
                }
            }
            return getRoot(list, result.getParentCd(), result);
        }
    }


    public Map<String, String> checkMachine(List<ProductMachineInfoVO> list2) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("error", "0");
        if (list2.size() == 1) {
            if (list2.get(0).getQuantity() < 1) {
                resultMap.put("error", "1");
                resultMap.put("errorMsg", "加工商品的总数量必须大于1");
                return resultMap;
            }
        }

        for (int i = 0; i < list2.size(); i++) {
            Integer quantity = list2.get(i).getQuantity();
            if (quantity == null || quantity < 1 || quantity > 100) {//参数错误
                resultMap.put("error", "1");
                resultMap.put("errorMsg", "参数[quantity]不能为null且只能为1到100的正整数");
                return resultMap;
            }
        }
        return resultMap;
    }

    private Map<String, String> checkSpecifications(ProductSpecificationsInfoVO specificationsInfo) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("error", "0");
        Map<String, Double> map = new HashMap();
        double fbaLength = specificationsInfo.getFbaLength();
        double fbaQuantity = (double) specificationsInfo.getFbaQuantity();
        double fbaVolume = specificationsInfo.getFbaVolume();
        double fbaWeight = specificationsInfo.getFbaWeight();
        double fbaWidth = specificationsInfo.getFbaWidth();
        double height = specificationsInfo.getHeight();
        double length = specificationsInfo.getLength();
        double weight = specificationsInfo.getWeight();
        double width = specificationsInfo.getWidth();
        map.put("fbaLength", fbaLength);
        map.put("fbaQuantity", fbaQuantity);
        map.put("fbaVolume", fbaVolume);
        map.put("fbaWeight", fbaWeight);
        map.put("fbaWidth", fbaWidth);
        map.put("height", height);
        map.put("length", length);
        map.put("weight", weight);
        map.put("width", width);
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            if (value == null || value < 0) {
                resultMap.put("error", "1");
                resultMap.put("errorMsg", "参数[" + key + "]不能为负数,不能为null");
                return resultMap;
            }
        }
        return resultMap;
    }

    //=====================标签逻辑新改---START====================================================================================
    @Override
    @Transactional(transactionManager = "retailProductTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result newInsert(@Valid NewAddProductInfoVO newAddProductInfoVO, String userId) {
        ProductInfoVO wmsProductInfo = new ProductInfoVO();
        //============>>> START <<<===============
        if (newAddProductInfoVO.getDefine().getMachineFlag() == 1) {
            Map<String, String> MachineFlag = checkMachine(newAddProductInfoVO.getMachineInfoList());
            if (!MachineFlag.get("error").equals("0")) {
                return new Result(9999, MachineFlag.get("errorMsg"), newAddProductInfoVO);
            }
        }
        //============>>> END <<<===============

        ProductDefineVO productDefineVO = newAddProductInfoVO.getDefine();
        if (productDefineVO == null) {
            return new Result(9999, "产品定义信息缺失", newAddProductInfoVO);
        }
        Map<String, String> map = new HashMap<>();
        ProductDefineEntity productDefineEntity = new ProductDefineEntity();
        BeanUtils.copyProperties(productDefineVO, productDefineEntity);
        ProductClassifyDefineEntity productClassifyDefineEntity = productClassifyDefineService.getRootClassify(productDefineEntity.getClassifyCd());
        if (productClassifyDefineEntity == null) {
            // 判断新增产品分类是否存在，如果不存在，直接返回
            return new Result(9999, "无法获取一级分类的缩写", newAddProductInfoVO);
        } else {
            productDefineEntity.setCreateUser(userId);
            // 首先插入产品定义信息
            map = productDefineService.insert(productDefineEntity, productClassifyDefineEntity.getClassifyShortName());
            if (!map.get("error").equals("0")) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new Result(9999, map.get("errorMsg"), productDefineEntity);
            } else {
                String skuId = productDefineEntity.getSkuId();
                //设置产品skuId,名称,类别
                wmsProductInfo.setGoodsCode(skuId);
                wmsProductInfo.setGoodsName(productDefineEntity.getSkuNameZh());
                wmsProductInfo.setGoodsType(productClassifyDefineEntity.getClassifyNameCh());
                wmsProductInfo.setDeclareGoodsName(newAddProductInfoVO.getDeclareInfo().getDeclareNameZh());

                List<ProductImagesVO> list = newAddProductInfoVO.getImagesList();
                if (list != null) {
                    ProductImagesEntity productImagesEntity = new ProductImagesEntity();
                    for (int i = 0; i < list.size(); i++) {
                        BeanUtils.copyProperties(list.get(i), productImagesEntity);
                        productImagesEntity.setSkuId(skuId);
                        map = productImagesService.insert(productImagesEntity);
                        if (!map.get("error").equals("0")) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return new Result(9999, map.get("errorMsg"), null);
                        }
                    }
                }

                //前端无此对象，后台也新增  ，防止  更新操作并发新增重复
                ProductDetailsInfoVO productDetailsInfoVO = newAddProductInfoVO.getDetailsInfo();
                ProductDetailsInfoEntity productDetailsInfoEntity = new ProductDetailsInfoEntity();
                if (productDetailsInfoVO != null) {
                    BeanUtils.copyProperties(productDetailsInfoVO, productDetailsInfoEntity);
                }
                try {
                    productDetailsInfoEntity.setSkuId(skuId);
                    productDetailsInfoService.insert(productDetailsInfoEntity);
                    //设置产品描述信息
                    wmsProductInfo.setRemark(productDetailsInfoEntity.getDescription());
                } catch (Exception e) {
                    logger.info("保存产品详情过程出错", e);
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(9999, "保存产品详情过程出错", null);
                }

                Integer machineFlag = productDefineEntity.getMachineFlag();
                if (machineFlag != null && machineFlag == 1) {
                    List<ProductMachineInfoVO> list2 = newAddProductInfoVO.getMachineInfoList();
                    if (list2 != null) {
                        ProductMachineInfoEntity productMachineInfoEntity = new ProductMachineInfoEntity();
                        //类型为加工产品
                        productMachineInfoEntity.setTypeCd(1);

                        for (int i = 0; i < list2.size(); i++) {
                            BeanUtils.copyProperties(list2.get(i), productMachineInfoEntity);
                            productMachineInfoEntity.setSkuId(skuId);
                            //不能将当前产品作为当前的加工产品
                            if (skuId.equals(productMachineInfoEntity.getDependencySkuId())) {
                                logger.debug("BOM单中的原料含有当前产品");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return new Result(9999, "BOM单中的原料含有当前产品", null);
                            }
                            Integer skuTypeCd = productMachineInfoService.getTypeOfProdect(productMachineInfoEntity.getDependencySkuId());
                            if (skuTypeCd == null || skuTypeCd != 1) {
                                //类型不对
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return new Result(9991, "BOM单中的原料不是产品类型！", null);
                            }
                            productMachineInfoService.insert(productMachineInfoEntity);
                        }
                    }
                }

                //包材
                Integer packingFlag = productDefineEntity.getPackingFlag();
                if (packingFlag != null && packingFlag == 1) {
                    List<ProductMachineInfoVO> packingMaterialList = newAddProductInfoVO.getPackingMaterialList();
                    if (packingMaterialList != null) {
                        ProductMachineInfoEntity productMachineInfoEntity = new ProductMachineInfoEntity();
                        //类型为包材
                        productMachineInfoEntity.setTypeCd(2);
                        for (ProductMachineInfoVO o : packingMaterialList) {
                            BeanUtils.copyProperties(o, productMachineInfoEntity);
                            productMachineInfoEntity.setSkuId(skuId);
                            //不能将当前产品作为当前的包材产品
                            if (skuId.equals(productMachineInfoEntity.getDependencySkuId())) {
                                logger.debug("包材单中的原料含有当前产品");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return new Result(9999, "包材单中的原料含有当前产品", null);
                            }
                            Integer skuTypeCd = productMachineInfoService.getTypeOfProdect(productMachineInfoEntity.getDependencySkuId());
                            if (skuTypeCd == null || skuTypeCd != 2) {
                                //类型不对
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return new Result(9991, "包材单中的sku不是包材类型！", null);
                            }
                            productMachineInfoService.insert(productMachineInfoEntity);
                        }
                    }
                }

                ProductSpecificationsInfoVO productSpecificationsInfoVO = newAddProductInfoVO.getSpecificationsInfo();
                ProductSpecificationsInfoEntity productSpecificationsInfoEntity = new ProductSpecificationsInfoEntity();
                if (productSpecificationsInfoVO != null) {
                    BeanUtils.copyProperties(productSpecificationsInfoVO, productSpecificationsInfoEntity);
                }
                try {
                    productSpecificationsInfoEntity.setSkuId(skuId);
                    productSpecificationsInfoService.insert(productSpecificationsInfoEntity);
                    // 设置WMS单品包装信息
                    List<ProductPackInfoVO> goodsPackList = new ArrayList<>();
                    ProductPackInfoVO packInfoVO = new ProductPackInfoVO();
                    packInfoVO.setBarcode(skuId);
                    packInfoVO.setWeight(productSpecificationsInfoEntity.getWeight());
                    packInfoVO.setLength(productSpecificationsInfoEntity.getLength() * 10);
                    packInfoVO.setWidth(productSpecificationsInfoEntity.getWidth() * 10);
                    packInfoVO.setHeight(productSpecificationsInfoEntity.getHeight() * 10);
                    goodsPackList.add(packInfoVO);
                    wmsProductInfo.setGoodsPackList(goodsPackList);

                } catch (Exception e) {
                    logger.info("保存产品规格过程出错", e);
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(9999, "保存产品规格过程出错", null);
                }

                ProductDeclareInfoVO productDeclareInfoVO = newAddProductInfoVO.getDeclareInfo();
                ProductDeclareInfoEntity productDeclareInfoEntity = new ProductDeclareInfoEntity();
                if (productDeclareInfoVO != null) {
                    BeanUtils.copyProperties(productDeclareInfoVO, productDeclareInfoEntity);
                }
                try {
                    productDeclareInfoEntity.setSkuId(skuId);
                    productDeclareInfoService.insert(productDeclareInfoEntity);
                } catch (Exception e) {
                    logger.info("保存产品申报信息出错", e);
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(9999, "保存产品申报信息出错", null);
                }

                //标签
                List<AddDeclareLabelVO> declareLabelList = newAddProductInfoVO.getDeclareLabelList();
                if (declareLabelList != null) {
                    //
                    NewProductDeclareLabelEntity nPDLEntity = new NewProductDeclareLabelEntity();
                    nPDLEntity.setSkuId(skuId);
                    CustomLabelEntity customLabelEntity = new CustomLabelEntity();
                    customLabelEntity.setSkuId(skuId);
                    for (AddDeclareLabelVO o : declareLabelList) {
                        // typeCd 1为基础标签，2为自定义标签
                        if (o.getTypeCd() != null && o.getTypeCd().equals(1)) {
                            BeanUtils.copyProperties(o, nPDLEntity);
                            productDeclareLabelService.insertLabel(nPDLEntity);
                        } else if (o.getTypeCd() != null && o.getTypeCd().equals(2)) {
                            BeanUtils.copyProperties(o, customLabelEntity);
                            productDeclareLabelService.insertCustomLabel(customLabelEntity);
                        }
                    }
                }

                //配件
                List<AddProductAccessoryVO> accessoryVOList = newAddProductInfoVO.getAccessoryList();
                if (accessoryVOList != null) {
                    ProductAccessoryEntity pAEntity = new ProductAccessoryEntity();
                    pAEntity.setSkuId(skuId);
                    // typeCd 1为基础配件，2为自定义配件
                    for (AddProductAccessoryVO o : accessoryVOList) {
                        if (o.getTypeCd() != null && o.getTypeCd().equals(1)) {
                            BeanUtils.copyProperties(o, pAEntity);
                            productAccessoryService.insert(pAEntity);
                        } else if (o.getTypeCd() != null && o.getTypeCd().equals(2)) {
                            BeanUtils.copyProperties(o, pAEntity);
                            productAccessoryService.insert(pAEntity);
                        }
                    }
                }

                ProductPackingInfoVO packingInfo = newAddProductInfoVO.getPackingInfo();
                ProductPackingInfoEntity pPIEntity = new ProductPackingInfoEntity();
                if (packingInfo != null) {
                    BeanUtils.copyProperties(packingInfo, pPIEntity);
                }
                pPIEntity.setSkuId(skuId);
                productPackingInfoService.insert(pPIEntity);


                ProductSalesVO productSalesVO = newAddProductInfoVO.getProductSales();
                ProductSalesInfoEntity productSalesEntity = new ProductSalesInfoEntity();
                if (productSalesVO != null) {
                    BeanUtils.copyProperties(productSalesVO, productSalesEntity);
                }
                productSalesEntity.setSkuId(skuId);
                productSalesInfoService.add(productSalesEntity);


                return Result.success(wmsProductInfo);
            }
        }
    }


    @Override
    @Transactional(transactionManager = "retailProductTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result updateNew(@Valid NewSetProductInfoVO newSetProductInfoVO, String userId) {
        ProductInfoVO wmsProductInfo = new ProductInfoVO();
        try {
            //============>>> START <<<===============
            Map<String, String> MachineFlag = checkMachine(newSetProductInfoVO.getMachineInfoList());
            if (!MachineFlag.get("error").equals("0")) {
                return new Result(9999, MachineFlag.get("errorMsg"), null);
            }
            //============>>> END <<<===============
            String time = Time.getCurrentTime();
            ProductDefineEntity productDefineEntity = new ProductDefineEntity();
            BeanUtils.copyProperties(newSetProductInfoVO.getDefine(), productDefineEntity);
            productDefineEntity.setUpdateUser(userId);

            int length = productDefineEntity.getSkuShortName().length();
            if (length > 5) {
                return new Result(9997, "分类简写长度超过5位", null);
            }
            productDefineEntity.setSkuShortName(productDefineEntity.getSkuShortName().toUpperCase());
            ProductClassifyDefineEntity productClassifyDefineEntity = productClassifyDefineService.getRootClassify(productDefineEntity.getClassifyCd());
            productDefineService.update(productDefineEntity, time, userId);

            String skuId = productDefineEntity.getSkuId();
            wmsProductInfo.setGoodsCode(skuId);
            wmsProductInfo.setGoodsName(productDefineEntity.getSkuNameZh());
            wmsProductInfo.setGoodsType(productClassifyDefineEntity.getClassifyNameCh());
            wmsProductInfo.setDeclareGoodsName(newSetProductInfoVO.getDeclareInfo().getDeclareNameZh());


            ProductDetailsInfoEntity productDetailsInfoEntity = new ProductDetailsInfoEntity();
            BeanUtils.copyProperties(newSetProductInfoVO.getDetailsInfo(), productDetailsInfoEntity);
            productDetailsInfoEntity.setSkuId(skuId);
            productDetailsInfoEntity.setUpdateUser(userId);
            productDetailsInfoService.update(productDetailsInfoEntity, time, userId);
            wmsProductInfo.setRemark(productDetailsInfoEntity.getDescription());


            //加工
            if (productDefineEntity.getMachineFlag() == 1) {
                List<ProductMachineInfoVO> list2 = newSetProductInfoVO.getMachineInfoList();
                if (list2 != null) {
                    List<ProductMachineInfoEntity> list3 = new ArrayList<>();
                    for (int i = 0; i < list2.size(); i++) {
                        ProductMachineInfoEntity productMachineInfoEntity = new ProductMachineInfoEntity();
                        BeanUtils.copyProperties(list2.get(i), productMachineInfoEntity);
                        productMachineInfoEntity.setSkuId(skuId);
                        //类型为加工
                        productMachineInfoEntity.setTypeCd(1);
                        //不能将当前产品作为当前的加工产品
                        if (skuId.equals(productMachineInfoEntity.getDependencySkuId())) {
                            logger.debug("BOM单中的原料含有当前产品");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return new Result(9999, "BOM单中的原料含有当前产品", null);
                        }
                        Integer skuTypeCd = productMachineInfoService.getTypeOfProdect(productMachineInfoEntity.getDependencySkuId());
                        if (skuTypeCd == null || skuTypeCd != 1) {
                            //类型不对
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return new Result(9991, "BOM单中的原料不是产品类型！", null);
                        }
                        productMachineInfoEntity.setUpdateUser(userId);
                        list3.add(productMachineInfoEntity);
                    }
                    productMachineInfoService.updateListNewTwo(list3, time, skuId, userId);
                }
            } else {
                productMachineInfoService.updateListNewTwo(null, time, skuId, userId);
            }

            //包材
            if (productDefineEntity.getPackingFlag() == 1) {
                List<ProductMachineInfoVO> list2 = newSetProductInfoVO.getPackingMaterialList();
                List<ProductMachineInfoEntity> list3 = new ArrayList<>();
                for (int i = 0; i < list2.size(); i++) {
                    ProductMachineInfoEntity productMachineInfoEntity = new ProductMachineInfoEntity();
                    BeanUtils.copyProperties(list2.get(i), productMachineInfoEntity);
                    productMachineInfoEntity.setSkuId(skuId);
                    //类型为加工
                    productMachineInfoEntity.setTypeCd(2);
                    //不能将当前产品作为当前的包装产品
                    if (skuId.equals(productMachineInfoEntity.getDependencySkuId())) {
                        logger.debug("BOM单中的原料含有当前产品");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return new Result(9999, "BOM单中的原料含有当前产品", null);
                    }
                    Integer skuTypeCd = productMachineInfoService.getTypeOfProdect(productMachineInfoEntity.getDependencySkuId());
                    if (skuTypeCd == null || skuTypeCd != 2) {
                        //类型不对
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return new Result(9991, "包材单中的sku不是包材类型！", null);
                    }
                    productMachineInfoEntity.setUpdateUser(userId);
                    list3.add(productMachineInfoEntity);
                }
                productMachineInfoService.updateListNewThree(list3, time, skuId, userId);
            } else {
                productMachineInfoService.updateListNewThree(null, time, skuId, userId);
            }


            ProductSpecificationsInfoEntity productSpecificationsInfoEntity = new ProductSpecificationsInfoEntity();
            BeanUtils.copyProperties(newSetProductInfoVO.getSpecificationsInfo(), productSpecificationsInfoEntity);
            productSpecificationsInfoEntity.setSkuId(skuId);
            productSpecificationsInfoEntity.setUpdateUser(userId);
            productSpecificationsInfoService.update(productSpecificationsInfoEntity, time, userId);
            List<ProductPackInfoVO> goodsPackList = new ArrayList<>();
            ProductPackInfoVO packInfoVO = new ProductPackInfoVO();
            packInfoVO.setBarcode(skuId);
            packInfoVO.setWeight(productSpecificationsInfoEntity.getWeight());
            packInfoVO.setLength(productSpecificationsInfoEntity.getLength() * 10);
            packInfoVO.setWidth(productSpecificationsInfoEntity.getWidth() * 10);
            packInfoVO.setHeight(productSpecificationsInfoEntity.getHeight() * 10);
            goodsPackList.add(packInfoVO);
            wmsProductInfo.setGoodsPackList(goodsPackList);


            ProductDeclareInfoEntity productDeclareInfoEntity = new ProductDeclareInfoEntity();
            BeanUtils.copyProperties(newSetProductInfoVO.getDeclareInfo(), productDeclareInfoEntity);
            productDeclareInfoEntity.setSkuId(skuId);
            productDeclareInfoEntity.setUpdateUser(userId);
            productDeclareInfoService.update(productDeclareInfoEntity, time, userId);

            // （新改）基础标签 与 自定义标签 的更新逻辑
            List<SetDeclareLabelVO> declareLabelList = newSetProductInfoVO.getDeclareLabelList();
            productDeclareLabelService.updateLabelList(declareLabelList, time, skuId, userId);


            // todo
            //(新改) 基础配件 与 自定义配件
            List<SetProductAccessoryVO> accessoryVOList = newSetProductInfoVO.getAccessoryList();
            productAccessoryService.update(accessoryVOList, time, skuId, userId);

            //装箱
            ProductPackingInfoVO packingInfo = newSetProductInfoVO.getPackingInfo();
            ProductPackingInfoEntity pPIEntity = new ProductPackingInfoEntity();
            pPIEntity.setSkuId(skuId);
            BeanUtils.copyProperties(packingInfo, pPIEntity);
            productPackingInfoService.update(pPIEntity, time, userId);


            List<ProductImagesVO> list4 = newSetProductInfoVO.getImagesList();
            List<ProductImagesEntity> list5 = new ArrayList<>();
            for (int i = 0; i < list4.size(); i++) {
                ProductImagesEntity productImagesEntity = new ProductImagesEntity();
                BeanUtils.copyProperties(list4.get(i), productImagesEntity);
                productImagesEntity.setSkuId(skuId);
                productImagesEntity.setUpdateUser(userId);
                list5.add(productImagesEntity);
            }
            productImagesService.updateListNewTwo(list5, time, skuId, userId);

            ProductSalesVO productSalesVO = newSetProductInfoVO.getProductSales();
            ProductSalesInfoEntity productSalesEntity = new ProductSalesInfoEntity();
            productSalesEntity.setSkuId(skuId);
            BeanUtils.copyProperties(productSalesVO, productSalesEntity);
            productSalesInfoService.update(productSalesEntity, time, userId);

            return Result.success(wmsProductInfo);
        } catch (Exception e) {
            logger.info("产品汇总更新出错", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//加上之后，抛了异常,也会回滚
            return Result.failure(ResultCode.UPDATE_FAILED);
        }
    }

    @Override
    public Map<String, Object> newFindBySkuId(String skuId) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductImagesEntity> imagesList = productImagesService.findBySkuId(skuId);

            ProductDefineEntity define = productDefineService.findBySkuId(skuId);

            Map classifymap = productClassifyDefineService.findByClassifyCd(define.getClassifyCd());

            ProductMachineInfoEntity pMIEntity = new ProductMachineInfoEntity();
            pMIEntity.setSkuId(skuId);
            pMIEntity.setTypeCd(1);
            List<ProductMachineInfoEntity> machineInfoList = productMachineInfoService.findPMIEntity(pMIEntity);
            pMIEntity.setTypeCd(2);
            List<ProductMachineInfoEntity> pakingList = productMachineInfoService.findPMIEntity(pMIEntity);

            //获取加工信息的图片
            List machingDetailList = new ArrayList();
            for (int i = 0; i < machineInfoList.size(); i++) {
                Map<String, Object> machingMap = new HashMap<>();
                List<ProductImagesEntity> reList = productImagesService.findBySkuId(machineInfoList.get(i).getDependencySkuId());
                machingMap.put("dependencySkuId", machineInfoList.get(i).getDependencySkuId());
                machingMap.put("uuid", machineInfoList.get(i).getUuid());
                machingMap.put("quantity", machineInfoList.get(i).getQuantity());
                //加工产品的图片信息
                machingMap.put("imageArr", reList);
                //加工产品的基本信息
                ProductDefineEntity reProduct = productDefineService.findBySkuId(machineInfoList.get(i).getDependencySkuId());
                machingMap.put("skuNameZh", reProduct.getSkuNameZh());
                machingDetailList.add(machingMap);
            }

            //获取包材的图片
            List pakingDetailList = new ArrayList();
            for (int i = 0; i < pakingList.size(); i++) {
                Map<String, Object> pachingMap = new HashMap<>();
                List<ProductImagesEntity> reList = productImagesService.findBySkuId(pakingList.get(i).getDependencySkuId());
                pachingMap.put("dependencySkuId", pakingList.get(i).getDependencySkuId());
                pachingMap.put("uuid", pakingList.get(i).getUuid());
                pachingMap.put("quantity", pakingList.get(i).getQuantity());
                //加工产品的图片信息
                pachingMap.put("imageArr", reList);
                //加工产品的基本信息
                ProductDefineEntity reProduct = productDefineService.findBySkuId(pakingList.get(i).getDependencySkuId());
                pachingMap.put("skuNameZh", reProduct.getSkuNameZh());
                pakingDetailList.add(pachingMap);
            }


            ProductDetailsInfoEntity detailsInfo = productDetailsInfoService.findBySkuId(skuId);
            ProductSpecificationsInfoEntity specificationsInfo = productSpecificationsInfoService.findBySkuId(skuId);
            ProductDeclareInfoEntity declareInfo = productDeclareInfoService.findBySkuId(skuId);

            //  (新改造 基础标签与自定义标签)
            List<LinkedHashMap> declareLabelList = productDeclareLabelService.newFindBySkuId(skuId);
            map.put("declareLabelList", declareLabelList);


            ProductPackingInfoEntity pPIEntity = productPackingInfoService.findBySkuId(skuId);
            ProductPackingInfoVO pPIVO = new ProductPackingInfoVO();
            if (pPIEntity != null) {
                BeanUtils.copyProperties(pPIEntity, pPIVO);
                map.put("packingInfo", pPIVO);
            } else {
                map.put("packingInfo", pPIVO);
            }


            List<LinkedHashMap> accessoryList = productAccessoryService.getBasicAndCustomBySkuId(skuId);
            map.put("accessoryList", accessoryList);


            ProductSalesInfoEntity productSalesInfoEntity = productSalesInfoService.findBySkuId(skuId);
            ProductSalesVO productSalesVO = new ProductSalesVO();
            if (productSalesInfoEntity != null) {
                BeanUtils.copyProperties(productSalesInfoEntity, productSalesVO);
            }
            map.put("productSalesInfo", productSalesVO);


            map.put("classify", classifymap);
            map.put("imagesList", imagesList);
            map.put("define", define);
            map.put("machineInfoList", machingDetailList);
            map.put("detailsInfo", detailsInfo);
            map.put("specificationsInfo", specificationsInfo);
            map.put("declareInfo", declareInfo);
            map.put("packingMaterialList", pakingDetailList);

            map.put("error", "0");
            map.put("errorMsg", null);
        } catch (Exception e) {
            logger.info("数据查询失败", e);
            map.put("error", "1");
            map.put("errorMsg", "数据查询失败");
        }
        return map;
    }

    private List<ShowDeclareLabelVO> toVOList(List<NewProductDeclareLabelEntity> declareLabelList) {
        List<ShowDeclareLabelVO> list = new ArrayList<>();
        for (NewProductDeclareLabelEntity o : declareLabelList) {
            ShowDeclareLabelVO showDeclareLabelVO = new ShowDeclareLabelVO();
            BeanUtils.copyProperties(o, showDeclareLabelVO);
            list.add(showDeclareLabelVO);
        }
        return list;
    }

    @Override
    public Result findByBatchSkuId(@Valid BatchSkuId batchSkuId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        List<String> skuIdList = batchSkuId.getSkuIdList();
        //去重
        List result = new ArrayList<>();
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String o : skuIdList) {
            map.put(o, null);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey() != null) {
                Map<String, Object> singleMap = newFindBySkuId(entry.getKey());
                if (singleMap.get("error").equals("0")) {
                    result.add(singleMap);
                }
            }
        }
        return Result.success(result);
    }

    /**
     * 根据sku获取他的配件信息与装箱信息
     *
     * @param skuId
     * @return
     */
    @Override
    public Result getAccessoryAndPacing(String skuId) {
        //配件与sku
        List<ProductAccessoryEntity> pAList = productAccessoryService.findBySkuId(skuId);
        //配件码表
        List<ProductAccessoryCdAttrEntity> pACAList = productAccessoryService.getAttr();
        //装箱信息
        ProductPackingInfoEntity packingInfoEntity = productPackingInfoService.findBySkuId(skuId);

        //包装vo
        List<Map> voList = paListToVOList(pAList, pACAList);
        Map resultMap = new HashMap();
        resultMap.put("accessory", voList);
        if (packingInfoEntity == null) {
            //没有装箱信息，为他赋一个初始值
            packingInfoEntity = new ProductPackingInfoEntity();
        }
        ProductPackingInfoVO vo = packingInfoEntityToProductPackingInfoVO(packingInfoEntity);
        resultMap.put("packingInfo", vo);

        return Result.success(resultMap);
    }


    //PO转VO
    private ProductPackingInfoVO packingInfoEntityToProductPackingInfoVO(ProductPackingInfoEntity packingInfoEntity) {
        ProductPackingInfoVO vo = new ProductPackingInfoVO();
        BeanUtils.copyProperties(packingInfoEntity, vo);
        return vo;
    }

    /**
     * 将装箱信息与装箱码表匹配 包装VO
     *
     * @param pAList
     * @param pACAList
     * @return
     */
    private List<Map> paListToVOList(List<ProductAccessoryEntity> pAList, List<ProductAccessoryCdAttrEntity> pACAList) {
        Map kvMap = new HashMap();
        for (ProductAccessoryCdAttrEntity o : pACAList) {
            kvMap.put(o.getAccessoryCd(), o.getAccessoryName());
        }
        List<Map> resultList = new ArrayList<>();
        for (ProductAccessoryEntity o : pAList) {
            Map map = new HashMap();
            map.put("accessoryCd", o.getAccessoryCd());
            map.put("accessoryText", o.getAccessoryText());
            map.put("accessoryText", o.getAccessoryText());
            //这里后端命名为accessoryName，前端使用accessoryDesc
            map.put("accessoryDesc", kvMap.get(o.getAccessoryCd()));
            resultList.add(map);
        }
        return resultList;
    }


//===========================标签逻辑新改---END====================================================================================


}
