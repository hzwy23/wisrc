package com.wisrc.purchase.webapp.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.purchase.webapp.dao.OrderBasisDao;
import com.wisrc.purchase.webapp.dao.OrderDetailsProductInfoDao;
import com.wisrc.purchase.webapp.dao.PurchaseReturnDao;
import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.service.*;
import com.wisrc.purchase.webapp.service.externalService.ProductService;
import com.wisrc.purchase.webapp.service.externalService.ProductSkuService;
import com.wisrc.purchase.webapp.utils.Arith;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.utils.Toolbox;
import com.wisrc.purchase.webapp.vo.GetPurchaseReturnNewVo;
import com.wisrc.purchase.webapp.vo.purchaseReturn.add.AddPurchaseReturnDetailsVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.add.AddPurchaseReturnInfoVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.add.AddPurchaseReturnVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.delete.ReturnBillPara;
import com.wisrc.purchase.webapp.vo.purchaseReturn.get.GetPurchaseReturnVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.set.SetPurchaseReturnDetailsVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.set.SetPurchaseReturnInfoVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.set.SetPurchaseReturnVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.show.GatherPurchaseReturnVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.show.PurchaseReturnDetailsVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.show.PurchaseReturnInfoVO;
import com.wisrc.purchase.webapp.vo.syncvo.GoodsInfoVO;
import com.wisrc.purchase.webapp.vo.syncvo.PurchaseRefundBillSyncVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class PurchaseReturnImplService implements PurchaseReturnService {

    private final Logger logger = LoggerFactory.getLogger(PurchaseReturnImplService.class);

    @Autowired
    private PurchaseReturnDao purchaseReturnDao;
    @Autowired
    private PurchaseEmployeeService purchaseEmployeeService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private OrderDetailsProductInfoDao orderDetailsProductInfoDao;
    @Autowired
    private OrderBasisDao orderBasisDao;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private SupplierOutsideService supplierOutsideService;

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result add(@Valid AddPurchaseReturnVO vo, BindingResult bindingResult, String userId) {
        //校验参数
        PurchaseRefundBillSyncVO wmsReturnVO = new PurchaseRefundBillSyncVO();
        List<GoodsInfoVO> goodsList = new ArrayList<>();
        Result result = checkParaAdd(vo, bindingResult);
        String orderId = vo.getPurchaseReturnInfo().getOrderId();
        if (result.getCode() != 200) {
            return result;
        }

        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        //检查备品数量和退货数量是否至少一项大于0
        List<AddPurchaseReturnDetailsVO> purchaseReturnDetailsList = vo.getPurchaseReturnDetailsList();
        if (null != purchaseReturnDetailsList && !purchaseReturnDetailsList.isEmpty()) {
            StringBuilder message = new StringBuilder("退货产品");
            for (int i = 0; i < purchaseReturnDetailsList.size(); i++) {
                AddPurchaseReturnDetailsVO purchaseReturnDetailsVO = purchaseReturnDetailsList.get(i);
                if (purchaseReturnDetailsVO.getReturnQuantity() + purchaseReturnDetailsVO.getSpareQuantity() <= 0) {
                    message.append("第").append(i + 1).append("项").append("、");
                }
            }
            if (message.length() > 5) {
                message = message.deleteCharAt(message.length() - 1);
                message.append("备品数量和退货数量至少需要一项大于0");
                return new Result(9999, message.toString(), null);
            }
        }
        //日期
        String createDateStr = vo.getPurchaseReturnInfo().getCreateDateStr();
        Date date = null;
        try {
            date = DateUtil.parse(createDateStr);
        } catch (Exception e) {
            logger.error("拒收日期[rejectionDateStr]格式不符合要求", e);
            return new Result(9999, "拒收日期[rejectionDateStr]格式不符合要求", null);
        }
        Integer year = DateUtil.year(date);
        if (year <= 2000) {
            return new Result(9999, "拒收日期[rejectionDateStr]必须大于2000年", null);
        }

        String newReturnBill = Toolbox.randomUUID();

        //赋值，插入
        PurchaseReturnInfoEntity pRIEntity = new PurchaseReturnInfoEntity();
        BeanUtils.copyProperties(vo.getPurchaseReturnInfo(), pRIEntity);

        pRIEntity.setCreateUser(userId);
        pRIEntity.setModifyUser(userId);
        pRIEntity.setReturnBill(newReturnBill);
        pRIEntity.setCreateDate(date);
        Timestamp current = Time.getCurrentTimestamp();
        pRIEntity.setCreateTime(current);
        pRIEntity.setModifyTime(current);
        pRIEntity.setDeleteStatus(0);
        purchaseReturnDao.insertPRI(pRIEntity);
        PurchaseReturnInfoEntity newpRIEntity = purchaseReturnDao.getPRIEntity(pRIEntity.getReturnBill());
        wmsReturnVO.setVoucherCode(newpRIEntity.getReturnBill());
        wmsReturnVO.setVoucherType("CGT");
        wmsReturnVO.setCreateTime(Time.getCurrentDateTime());
        wmsReturnVO.setPreDeliveryVocuherCode(pRIEntity.getOrderId());
        wmsReturnVO.setSupplierCode(pRIEntity.getSupplierId());
        wmsReturnVO.setRemark(pRIEntity.getRemark());
        wmsReturnVO.setSectionCode(pRIEntity.getWarehouseId());


        // 获取更新之后的退货单ID
        newReturnBill = purchaseReturnDao.findId(newReturnBill);

        //赋值 插入
        List<AddPurchaseReturnDetailsVO> list = vo.getPurchaseReturnDetailsList();
        if (list != null && list.size() != 0) {
            PurchaseReturnDetailsEntity pRDEntity = new PurchaseReturnDetailsEntity();
            int i = 1;
            for (AddPurchaseReturnDetailsVO o : list) {
                GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                BeanUtils.copyProperties(o, pRDEntity);
                pRDEntity.setReturnBill(newReturnBill);
                String uuid = Toolbox.randomUUID();
                pRDEntity.setUuid(uuid);
                if (pRDEntity.getSpareQuantity() == null) {
                    //备品数量
                    pRDEntity.setSpareQuantity(0);
                }
                goodsInfoVO.setLineNum(i);
                goodsInfoVO.setGoodsCode(o.getSkuId());
                //备品数可能为null  设置为0
                Integer spareQuantity = o.getSpareQuantity();
                spareQuantity = spareQuantity == null ? 0 : spareQuantity;
                goodsInfoVO.setUnitQuantity(o.getReturnQuantity() + spareQuantity);
                goodsInfoVO.setPackageQuantity(0);
                goodsInfoVO.setTotalQuantity(o.getReturnQuantity() + spareQuantity);
                goodsList.add(goodsInfoVO);
                i++;
                purchaseReturnDao.insertPRD(pRDEntity);
            }
        }
        wmsReturnVO.setGoodsList(goodsList);
        int statusCd = toOrderStatus(orderId);
        orderBasisDao.changeStatus(statusCd, orderId);
        return Result.success(wmsReturnVO);
    }

    @Override
    public Result changeStatus(Integer statusCd, String returnBill) {
        purchaseReturnDao.changeStatus(statusCd, Time.getCurrentDateTime(), returnBill);
        return Result.success();
    }


    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result update(@Valid SetPurchaseReturnVO vo, BindingResult bindingResult, String userId) {
        //校验参数
        Result result = checkParaUpdate(vo, bindingResult);
        if (result.getCode() != 200) {
            return result;
        }
        //检查备品数量和退货数量是否至少一项大于0
        List<SetPurchaseReturnDetailsVO> purchaseReturnDetailsList = vo.getPurchaseReturnDetailsList();
        if (null != purchaseReturnDetailsList && !purchaseReturnDetailsList.isEmpty()) {
            StringBuilder message = new StringBuilder("退货产品");
            for (int i = 0; i < purchaseReturnDetailsList.size(); i++) {
                SetPurchaseReturnDetailsVO purchaseReturnDetailsVO = purchaseReturnDetailsList.get(i);
                if (purchaseReturnDetailsVO.getReturnQuantity() + purchaseReturnDetailsVO.getSpareQuantity() <= 0) {
                    message.append("第").append(i + 1).append("项").append("、");
                }
            }
            if (message.length() > 5) {
                message = message.deleteCharAt(message.length() - 1);
                message.append("备品数量和退货数量至少需要一项大于0");
                return new Result(9999, message.toString(), null);
            }
        }

        //日期
        String createDateStr = vo.getPurchaseReturnInfo().getCreateDateStr();
        Date date = null;
        try {
            date = DateUtil.parse(createDateStr);
        } catch (Exception e) {
            logger.error("拒收日期[rejectionDateStr]格式不符合要求", e);
            return new Result(9999, "拒收日期[rejectionDateStr]格式不符合要求", null);
        }
        Integer year = DateUtil.year(date);
        if (year <= 2000) {
            return new Result(9999, "拒收日期[rejectionDateStr]必须大于2000年", null);
        }

        //赋值，更新
        PurchaseReturnInfoEntity pRIEntity = new PurchaseReturnInfoEntity();
        BeanUtils.copyProperties(vo.getPurchaseReturnInfo(), pRIEntity);
        pRIEntity.setCreateDate(date);
        pRIEntity.setModifyUser(userId);
        pRIEntity.setModifyTime(Time.getCurrentTimestamp());
        purchaseReturnDao.updatePRI(pRIEntity);

        // 更新退货详细信息
        String returnBill = vo.getPurchaseReturnInfo().getReturnBill();

        //物理删除上次的数据
        purchaseReturnDao.realDeletePRD(returnBill);

        //赋值 重新插入
        List<SetPurchaseReturnDetailsVO> list = vo.getPurchaseReturnDetailsList();
        if (list != null && list.size() != 0) {
            PurchaseReturnDetailsEntity pRDEntity = new PurchaseReturnDetailsEntity();
            for (SetPurchaseReturnDetailsVO o : list) {
                pRDEntity.setReturnBill(returnBill);
                if (o.getSpareQuantity() == null) {
                    o.setSpareQuantity(0);
                }
                BeanUtils.copyProperties(o, pRDEntity);
                pRDEntity.setUuid(Toolbox.randomUUID());
                purchaseReturnDao.insertPRD(pRDEntity);
            }
        }

        return Result.success();
    }


    @Override
    public Result findByReturnBill(String returnBill) {
        PurchaseReturnInfoEntity pRIEntity = purchaseReturnDao.getPRI(returnBill);
        return handleReturnBill(pRIEntity);
    }

    @Override
    public Result handleReturnBill(PurchaseReturnInfoEntity pRIEntity) {
        if (pRIEntity == null) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("purchaseReturnInfo", pRIEntity);
            resultMap.put("purchaseRejectionDetailsList", new ArrayList<>());
            return Result.success(resultMap);
        } else {

            List<PurchaseReturnDetailsEntity> pRDList = purchaseReturnDao.getPRD(pRIEntity.getReturnBill());

            //调用外部接口 获取额外关联数据
            //人员
            List<String> paraList = new ArrayList<>();
            paraList.add(pRIEntity.getEmployeeId());
            Map employeeMap;
            try {
                employeeMap = purchaseEmployeeService.getEmployeeNameMap(paraList);
            } catch (Exception e) {
                logger.error("调用人员管理出现错误", e);
                return new Result(9999, "调用人员管理出现错误！", null);
            }


            //供应商
            List<String> supplierIdsList = new ArrayList<>();
            supplierIdsList.add(pRIEntity.getSupplierId());
            Map supplierMap;
            try {
                supplierMap = supplierService.getSupplierNameMap(supplierIdsList);
            } catch (Exception e) {
                logger.error("调用外部供应商接口出错", e);
                return new Result(9999, "调用外部供应商接口出错！请稍后重试或者联系管理员", e);
            }


            //仓库
            List<String> warehouseIdsList = new ArrayList<>();
            warehouseIdsList.add(pRIEntity.getWarehouseId());
            warehouseIdsList.add(pRIEntity.getPackWarehouseId());
            Map warehouseNameMap;
            try {
                warehouseNameMap = warehouseService.getWarehouseNameMap(warehouseIdsList);
            } catch (Exception e) {
                logger.error("调用外部仓库接口出错", e);
                return new Result(9999, "调用外部仓库接口出错", e);
            }


            //sku产品
            Map productNameMap = new HashMap();
            List<String> skuIdList = new ArrayList();
            for (PurchaseReturnDetailsEntity o : pRDList) {
                skuIdList.add(o.getSkuId());
            }

            if (skuIdList.size() > 0) {
                try {
                    Result productResult = productService.getProductCN(skuIdList);
                    if (productResult.getCode() != 200) {
                        return productResult;
                    }
                    productNameMap = (Map) productResult.getData();
                } catch (Exception e) {
                    logger.error("调用产品管理出现错误", e);
                    return new Result(9999, "调用产品管理出现错误！", e);
                }
            }

            //PO转VO
            PurchaseReturnInfoVO pRIVO = new PurchaseReturnInfoVO();
            BeanUtils.copyProperties(pRIEntity, pRIVO);
            pRIVO.setEmployeeName((String) employeeMap.get(pRIVO.getEmployeeId()));
            pRIVO.setSupplierName((String) supplierMap.get(pRIVO.getSupplierId()));
            pRIVO.setWarehouseName((String) warehouseNameMap.get(pRIVO.getWarehouseId()));
            pRIVO.setPackWarehouseName((String) warehouseNameMap.get(pRIVO.getPackWarehouseId()));
            pRIVO.setCreateDateStr(pRIEntity.getCreateDate());

            List<PurchaseReturnDetailsVO> pRDVOList = poListToVO(productNameMap, pRDList);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("purchaseReturnInfo", pRIVO);
            resultMap.put("purchaseRejectionDetailsList", pRDVOList);
            return Result.success(resultMap);
        }
    }

    private List<PurchaseReturnDetailsVO> poListToVO(Map productNameMap, List<PurchaseReturnDetailsEntity> pRDList) {
        List<PurchaseReturnDetailsVO> resultList = new ArrayList<>();
        if (pRDList != null || pRDList.size() != 0) {
            for (PurchaseReturnDetailsEntity obj : pRDList) {
                PurchaseReturnDetailsVO pRDVO = new PurchaseReturnDetailsVO();
                BeanUtils.copyProperties(obj, pRDVO);
                pRDVO.setSkuNameZh((String) productNameMap.get(obj.getSkuId()));
                resultList.add(pRDVO);
            }
        }
        return resultList;
    }

    @Override
    public Result fuzzy(@Valid GetPurchaseReturnVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        PurchaseReturnInfoEntity pRIEntity = new PurchaseReturnInfoEntity();
        BeanUtils.copyProperties(vo, pRIEntity);
        //时间处理
        try {
            pRIEntity.setCreateDateStart(DateUtil.parse(vo.getCreateDateStartStr()));
            pRIEntity.setCreateDateEnd(DateUtil.parse(vo.getCreateDateEndStr()));
        } catch (Exception e) {
            logger.error("拒收日期格式不符合要求", e);
            return new Result(9999, "拒收日期格式不符合要求", null);
        }

        List<PurchaseReturnInfoEntity> list;
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            list = purchaseReturnDao.fuzzy(pRIEntity);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            list = purchaseReturnDao.fuzzy(pRIEntity);
        }


        if (list == null || list.size() == 0) {
            Map reMap = new HashMap();
            reMap.put("total", 0);
            reMap.put("pages", 1);
            reMap.put("PurchaseReturnInfoList", new ArrayList());
            return Result.success(reMap);
        } else {

            //调用外部接口 获取额外关联数据
            //人员
            List<String> paraList = new ArrayList<>();
            for (PurchaseReturnInfoEntity obj : list) {
                paraList.add(obj.getEmployeeId());
            }
            Map employeeMap;
            try {
                employeeMap = purchaseEmployeeService.getEmployeeNameMap(paraList);
            } catch (Exception e) {
                logger.error("调用人员管理出现错误", e);
                return new Result(9999, "调用人员管理出现错误！", null);
            }


            //供应商
            List<String> supplierIdsList = new ArrayList<>();
            for (PurchaseReturnInfoEntity obj : list) {
                supplierIdsList.add(obj.getSupplierId());
            }
            Map supplierMap;
            try {
                supplierMap = supplierService.getSupplierNameMap(supplierIdsList);
            } catch (Exception e) {
                logger.error("调用外部供应商接口出错", e);
                return new Result(9999, "调用外部供应商接口出错！请稍后重试或者联系管理员", e);
            }


            //仓库
            List<String> warehouseIdsList = new ArrayList<>();
            for (PurchaseReturnInfoEntity obj : list) {
                warehouseIdsList.add(obj.getWarehouseId());
            }
            Map warehouseNameMap;
            try {
                warehouseNameMap = warehouseService.getWarehouseNameMap(warehouseIdsList);
            } catch (Exception e) {
                logger.error("调用外部仓库接口出错", e);
                return new Result(9999, "调用外部仓库接口出错", e);
            }

            //PO转VO
            List<PurchaseReturnInfoVO> resultList = new ArrayList<>();
            for (PurchaseReturnInfoEntity obj : list) {
                PurchaseReturnInfoVO pRIVO = new PurchaseReturnInfoVO();
                BeanUtils.copyProperties(obj, pRIVO);
                pRIVO.setEmployeeName((String) employeeMap.get(obj.getEmployeeId()));
                pRIVO.setSupplierName((String) supplierMap.get(obj.getSupplierId()));
                pRIVO.setWarehouseName((String) warehouseNameMap.get(obj.getWarehouseId()));
                pRIVO.setCreateDateStr(obj.getCreateDate());
                resultList.add(pRIVO);
            }

            Map reMap = new HashMap();
            PageInfo pageInfo = new PageInfo(list);
            long total = pageInfo.getTotal();
            int pages = pageInfo.getPages();
            reMap.put("total", total);
            reMap.put("pages", pages);
            reMap.put("PurchaseReturnInfoList", resultList);
            return Result.success(reMap);
        }
    }


    @Override
    public Result delete(@Valid ReturnBillPara returnBillArray, BindingResult bindingResult, String userId) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        List<String> returnBillList = returnBillArray.getReturnBillList();
        if (returnBillList != null) {
            for (String returnBill : returnBillList) {
                purchaseReturnDao.realDeletePRD(returnBill);
                purchaseReturnDao.deletePRI(returnBill);
            }
        }

        return Result.success();
    }

    @Override
    public void export(HttpServletResponse response, @Valid ReturnBillPara returnBillArray, BindingResult bindingResult) throws IOException {
        //检测参数
        if (bindingResult.hasErrors()) {
            logger.info(bindingResult.getFieldError().getDefaultMessage());
            doPromptException(response, bindingResult.getFieldError().getDefaultMessage(), null);
            return;
        }

        List<String> idList = returnBillArray.getReturnBillList();
        String idPara = listToParaString(idList);
        Map paraMap = new HashMap();
        paraMap.put("returnBill", idPara);
        List<GatherPurchaseReturnEntity> list = purchaseReturnDao.getAllPR(paraMap);
        if (list.size() == 0) {

            //方式二  输出空excel
            doExport(response, null);
            return;
        } else {


            //调用外部接口 获取额外关联数据
            //人员
            List<String> paraList = new ArrayList<>();
            for (GatherPurchaseReturnEntity o : list) {
                paraList.add(o.getEmployeeId());
            }
            Map employeeMap = new HashMap();
            try {
                employeeMap = purchaseEmployeeService.getEmployeeNameMap(paraList);
            } catch (Exception e) {
                logger.error("调用人员管理出现错误", e);
                doPromptException(response, "调用人员管理出现错误", e);
            }


            //供应商
            List<String> supplierIdsList = new ArrayList<>();
            for (GatherPurchaseReturnEntity o : list) {
                supplierIdsList.add(o.getSupplierId());
            }
            Map supplierMap = new HashMap();
            try {
                supplierMap = supplierService.getSupplierNameMap(supplierIdsList);
            } catch (Exception e) {
                logger.error("调用外部供应商接口出错", e);
                doPromptException(response, "调用外部供应商接口出错！请稍后重试或者联系管理员！", e);
            }
            Result result = productService.getAllSkuPackingMaterial();
            if (result.getCode() != 200) {
                logger.error("获取所有需要包材商品接口出错");
                doPromptException(response, "调用外部获取所有需要包材商品接口出错！请稍后重试或者联系管理员！", null);
            }
            Map<String, List> packMap = (Map<String, List>) result.getData();
            //仓库
            List<String> warehouseIdsList = new ArrayList<>();
            for (GatherPurchaseReturnEntity o : list) {
                warehouseIdsList.add(o.getWarehouseId());
                warehouseIdsList.add(o.getPackWarehouseId());
            }
            Map warehouseNameMap = new HashMap();
            try {
                warehouseNameMap = warehouseService.getWarehouseNameMap(warehouseIdsList);
            } catch (Exception e) {
                logger.error("调用外部仓库接口出错", e);
                doPromptException(response, "调用外部仓库接口出错", e);
            }


            //sku产品
            Map productNameMap = new HashMap();
            List<String> skuIdList = new ArrayList();
            for (GatherPurchaseReturnEntity o : list) {
                skuIdList.add(o.getSkuId());
            }
            try {
                Result productResult = productService.getProductCN(skuIdList);
                if (productResult.getCode() != 200) {
                    doPromptException(response, productResult.getMsg(), productResult.getData());
                }
                productNameMap = (Map) productResult.getData();
            } catch (Exception e) {
                logger.error("调用产品管理出现错误", e);
                doPromptException(response, "调用产品管理出现错误", e);
            }

            //PO转VO
            List<GatherPurchaseReturnVO> resultList = new ArrayList<>();
            for (GatherPurchaseReturnEntity obj : list) {
                GatherPurchaseReturnVO gPRVO = new GatherPurchaseReturnVO();
                BeanUtils.copyProperties(obj, gPRVO);
                //数值的null转为字符的空白显示，防止poi的空指针
                if (obj.getSpareQuantity() == null) {
                    gPRVO.setSpareQuantity("");
                } else {
                    gPRVO.setSpareQuantity(String.valueOf(obj.getSpareQuantity()));
                }
                if (obj.getReturnQuantity() == null) {
                    gPRVO.setReturnQuantity("");
                } else {
                    gPRVO.setReturnQuantity(String.valueOf(obj.getReturnQuantity()));
                }
                if (obj.getAmountWithoutTax() == null) {
                    gPRVO.setAmountWithoutTax("");
                } else {
                    gPRVO.setAmountWithoutTax(String.valueOf(obj.getAmountWithoutTax()));
                }
                if (obj.getAmountWithTax() == null) {
                    gPRVO.setAmountWithTax("");
                } else {
                    gPRVO.setAmountWithTax(String.valueOf(obj.getAmountWithTax()));
                }
                if (obj.getUnitPriceWithoutTax() == null) {
                    gPRVO.setUnitPriceWithoutTax("");
                } else {
                    gPRVO.setUnitPriceWithoutTax(String.valueOf(obj.getUnitPriceWithoutTax()));
                }
                if (obj.getUnitPriceWithTax() == null) {
                    gPRVO.setUnitPriceWithTax("");
                } else {
                    gPRVO.setUnitPriceWithTax(String.valueOf(obj.getUnitPriceWithTax()));
                }

                if (obj.getTaxRate() == null) {
                    gPRVO.setTaxRate("");
                } else {
                    gPRVO.setTaxRate(String.valueOf(obj.getTaxRate()));
                }
                String enskuId = obj.getSkuId();
                List<Map> packList = packMap.get(enskuId);
                List<ProductMachineInfoEntity> productMachineInfoEntityList = new ArrayList<>();
                if (packList != null) {
                    gPRVO.setIsNeedPacking("Y");
                    for (Map map : packList) {
                        ProductMachineInfoEntity productMachineInfoEntity = new ProductMachineInfoEntity();
                        productMachineInfoEntity.setSkuNameZh((String) map.get("skuNameZh"));
                        productMachineInfoEntity.setQuantity((Integer) map.get("quantity"));
                        productMachineInfoEntity.setDependencySkuId((String) map.get("dependencySkuId"));
                        productMachineInfoEntityList.add(productMachineInfoEntity);
                    }
                    gPRVO.setProductMachineInfoEntityList(productMachineInfoEntityList);
                } else {
                    gPRVO.setIsNeedPacking("N");
                }
                gPRVO.setEmployeeName((String) employeeMap.get(obj.getEmployeeId()));
                gPRVO.setSupplierName((String) supplierMap.get(obj.getSupplierId()));
                gPRVO.setWarehouseName((String) warehouseNameMap.get(obj.getWarehouseId()));
                gPRVO.setSkuNameZh((String) productNameMap.get(obj.getSkuId()));
                gPRVO.setCreateDateStr(obj.getCreateDate());
                gPRVO.setPackWarehouseName((String) warehouseNameMap.get(obj.getPackWarehouseId()));
                resultList.add(gPRVO);
            }

            //excel生成
            doExport(response, resultList);
            return;
        }
    }

    @Override
    public void fuzzyExport(HttpServletResponse response, GetPurchaseReturnNewVo vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) throws IOException {
        List<PurchaseReturnInfoVO> resultList = new ArrayList<>();
        String productName = vo.getProductName();
        String supplierName = vo.getSupplierName();
        List<String> skuIdList = new ArrayList<>();
        List<String> supplierList = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            logger.info(bindingResult.getFieldError().getDefaultMessage());
            doPromptException(response, bindingResult.getFieldError().getDefaultMessage(), null);
            return;
        }

        PurchaseReturnInfoNewEntity pRIEntity = new PurchaseReturnInfoNewEntity();
        BeanUtils.copyProperties(vo, pRIEntity);
        //时间处理
        try {
            pRIEntity.setCreateDateStart(DateUtil.parse(vo.getCreateDateStartStr()));
            pRIEntity.setCreateDateEnd(DateUtil.parse(vo.getCreateDateEndStr()));
            //如果是查询条件中为yyyy-MM-dd的形式，结尾加上一天
            if (vo.getCreateDateEndStr() != null && vo.getCreateDateEndStr().matches("\\d{4}-\\d{2}-\\d{2}")) {
                DateTime offsetDay = DateUtil.offsetMillisecond(DateUtil.offsetDay(pRIEntity.getCreateDateEnd(), 1), -1);
                pRIEntity.setCreateDateEnd(offsetDay);
            }
        } catch (Exception e) {
            logger.info("拒收日期格式不符合要求", e);
            doPromptException(response, "拒收日期格式不符合要求", null);
            return;
        }
        if (StringUtils.isNotEmpty(productName)) {
            Result proResult = productSkuService.getProductSkuInfo(null, null, productName);
            if (proResult.getCode() != 200) {
                throw new RuntimeException("产品外部接口调用异常");
            }
            Map proResultMap = (Map) proResult.getData();
            List<Map> proMapList = (List<Map>) proResultMap.get("productData");
            if (proMapList == null || proMapList.size() <= 0) {
                return;
            }
            for (Map skuMap : proMapList) {
                String skuId = (String) skuMap.get("sku");
                skuIdList.add(skuId);
            }
        }
        if (supplierName != null) {
            try {
                Map map = supplierOutsideService.getSupplierList(supplierName, 1, Integer.MAX_VALUE);
                Integer count = (Integer) map.get("supplierCount");
                if (count.equals(0)) {
                    doExport(response, null);
                    return;
                }
                List<Map> supplierMapList = (List<Map>) map.get("suppliers");
                for (Map supplierMap : supplierMapList) {
                    String supplierId = (String) supplierMap.get("supplierId");
                    supplierList.add(supplierId);
                }
            } catch (Exception e) {
                throw new RuntimeException("供应商接口调用异常");
            }
        }
        String skuIds = "";
        String suppliers = "";
        for (String skuId : skuIdList) {
            skuIds += "'" + skuId + "'" + ",";
        }
        if (skuIds.endsWith(",")) {
            int index = skuIds.lastIndexOf(",");
            skuIds = skuIds.substring(0, index);
        }
        if (StringUtils.isEmpty(skuIds)) {
            skuIds = null;
        }
        for (String supplierId : supplierList) {
            suppliers += "'" + supplierId + "'" + ",";
        }
        if (suppliers.endsWith(",")) {
            int index = suppliers.lastIndexOf(",");
            suppliers = suppliers.substring(0, index);
        }
        if (StringUtils.isEmpty(suppliers)) {
            suppliers = null;
        }
        String returnBills = "";
        List<String> returnBillList = null;
        if (productName != null) {
            if (vo.getSkuId() != null) {
                returnBillList = purchaseReturnDao.getReturnBillIdAndSkuId(skuIds, vo.getSkuId());
            } else {
                returnBillList = purchaseReturnDao.getReturnBillId(skuIds);
            }
            if (returnBillList == null || returnBillList.size() <= 0) {
                doExport(response, null);
                return;
            }
            for (String returnBillId : returnBillList) {
                returnBills += "'" + returnBillId + "'" + ",";
            }
            if (returnBills.endsWith(",")) {
                int index = returnBills.lastIndexOf(",");
                returnBills = returnBills.substring(0, index);
            }
        } else {
            if (vo.getSkuId() != null) {
                returnBillList = purchaseReturnDao.getReturnBillIdBySkuId(vo.getSkuId());
                if (returnBillList == null || resultList.size() <= 0) {
                    doExport(response, null);
                    return;
                }
                for (String returnBillId : returnBillList) {
                    returnBills += "'" + returnBillId + "'" + ",";
                }
                if (returnBills.endsWith(",")) {
                    int index = skuIds.lastIndexOf(",");
                    returnBills = returnBills.substring(0, index);
                }
            }
        }
        if (StringUtils.isEmpty(returnBills)) {
            returnBills = null;
        }
        pRIEntity.setReturnBills(returnBills);
        pRIEntity.setSupplierIds(suppliers);
        List<PurchaseReturnInfoEntity> pRIList;
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            pRIList = purchaseReturnDao.fuzzyNew(pRIEntity);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            pRIList = purchaseReturnDao.fuzzyNew(pRIEntity);
        }
        if (pRIList != null && pRIList.size() != 0) {
            ReturnBillPara returnBillArray = new ReturnBillPara();
            List<String> returnBillsList = new ArrayList<>();
            for (PurchaseReturnInfoEntity o : pRIList) {
                returnBillsList.add(o.getReturnBill());
            }
            returnBillArray.setReturnBillList(returnBillsList);
            export(response, returnBillArray, bindingResult);
            return;
        } else { // pRIList为null 或者 pRIList的size为0，即无结果
            //方式二  输出空excel
            doExport(response, null);
            return;
        }
    }

    @Override
    public Result fuzzyNew(GetPurchaseReturnNewVo vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) {
        List<PurchaseReturnInfoVO> resultList = new ArrayList<>();
        String productName = vo.getProductName();
        String supplierName = vo.getSupplierName();
        List<String> skuIdList = new ArrayList<>();
        List<String> supplierList = new ArrayList<>();
        PurchaseReturnInfoNewEntity pRIEntity = new PurchaseReturnInfoNewEntity();
        BeanUtils.copyProperties(vo, pRIEntity);
        try {
            pRIEntity.setCreateDateStart(DateUtil.parse(vo.getCreateDateStartStr()));
            pRIEntity.setCreateDateEnd(DateUtil.parse(vo.getCreateDateEndStr()));
            //如果是查询条件中为yyyy-MM-dd的形式，结尾加上一天
            if (vo.getCreateDateEndStr() != null && vo.getCreateDateEndStr().matches("\\d{4}-\\d{2}-\\d{2}")) {
                DateTime offsetDay = DateUtil.offsetMillisecond(DateUtil.offsetDay(pRIEntity.getCreateDateEnd(), 1), -1);
                pRIEntity.setCreateDateEnd(offsetDay);
            }
        } catch (Exception e) {
            throw new RuntimeException("拒收日期格式不符合要求");
        }
        if (StringUtils.isNotEmpty(productName)) {
            Result proResult = productSkuService.getProductSkuInfo(null, null, productName);
            if (proResult.getCode() != 200) {
                throw new RuntimeException("产品外部接口调用异常");
            }
            Map proResultMap = (Map) proResult.getData();
            List<Map> proMapList = (List<Map>) proResultMap.get("productData");
            if (proMapList == null || proMapList.size() <= 0) {
                Map reMap = new HashMap();
                reMap.put("total", 0);
                reMap.put("pages", 1);
                reMap.put("PurchaseReturnInfoList", resultList);
                return Result.success(reMap);
            }
            for (Map skuMap : proMapList) {
                String skuId = (String) skuMap.get("sku");
                skuIdList.add(skuId);
            }
        }
        if (supplierName != null) {
            try {
                Map map = supplierOutsideService.getSupplierList(supplierName, 1, Integer.MAX_VALUE);
                Integer count = (Integer) map.get("supplierCount");
                if (count.equals(0)) {
                    Map reMap = new HashMap();
                    reMap.put("total", 0);
                    reMap.put("pages", 1);
                    reMap.put("PurchaseReturnInfoList", resultList);
                    return Result.success(reMap);
                }
                List<Map> supplierMapList = (List<Map>) map.get("suppliers");
                for (Map supplierMap : supplierMapList) {
                    String supplierId = (String) supplierMap.get("supplierId");
                    supplierList.add(supplierId);
                }
            } catch (Exception e) {
                throw new RuntimeException("供应商接口调用异常");
            }
        }
        String skuIds = "";
        String suppliers = "";
        for (String skuId : skuIdList) {
            skuIds += "'" + skuId + "'" + ",";
        }
        if (skuIds.endsWith(",")) {
            int index = skuIds.lastIndexOf(",");
            skuIds = skuIds.substring(0, index);
        }
        if (StringUtils.isEmpty(skuIds)) {
            skuIds = null;
        }
        for (String supplierId : supplierList) {
            suppliers += "'" + supplierId + "'" + ",";
        }
        if (suppliers.endsWith(",")) {
            int index = suppliers.lastIndexOf(",");
            suppliers = suppliers.substring(0, index);
        }
        if (StringUtils.isEmpty(suppliers)) {
            suppliers = null;
        }
        String returnBills = "";
        List<String> returnBillList = null;
        if (productName != null) {
            if (vo.getSkuId() != null) {
                returnBillList = purchaseReturnDao.getReturnBillIdAndSkuId(skuIds, vo.getSkuId());
            } else {
                returnBillList = purchaseReturnDao.getReturnBillId(skuIds);
            }
            if (returnBillList == null || returnBillList.size() <= 0) {
                Map reMap = new HashMap();
                reMap.put("total", 0);
                reMap.put("pages", 1);
                reMap.put("PurchaseReturnInfoList", resultList);
                return Result.success(reMap);
            }
            for (String returnBillId : returnBillList) {
                returnBills += "'" + returnBillId + "'" + ",";
            }
            if (returnBills.endsWith(",")) {
                int index = returnBills.lastIndexOf(",");
                returnBills = returnBills.substring(0, index);
            }
        } else {
            if (vo.getSkuId() != null) {
                returnBillList = purchaseReturnDao.getReturnBillIdBySkuId(vo.getSkuId());
                if (returnBillList == null || returnBillList.size() <= 0) {
                    Map reMap = new HashMap();
                    reMap.put("total", 0);
                    reMap.put("pages", 1);
                    reMap.put("PurchaseReturnInfoList", resultList);
                    return Result.success(reMap);
                }
                for (String returnBillId : returnBillList) {
                    returnBills += "'" + returnBillId + "'" + ",";
                }
                if (returnBills.endsWith(",")) {
                    int index = returnBills.lastIndexOf(",");
                    returnBills = returnBills.substring(0, index);
                }
            }
        }
        if (StringUtils.isEmpty(returnBills)) {
            returnBills = null;
        }
        pRIEntity.setReturnBills(returnBills);
        pRIEntity.setSupplierIds(suppliers);
        List<PurchaseReturnInfoEntity> list;
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            list = purchaseReturnDao.fuzzyNew(pRIEntity);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            list = purchaseReturnDao.fuzzyNew(pRIEntity);
        }
        if (list == null || list.size() == 0) {
            Map reMap = new HashMap();
            reMap.put("total", 0);
            reMap.put("pages", 1);
            reMap.put("PurchaseReturnInfoList", new ArrayList());
            return Result.success(reMap);
        } else {

            //调用外部接口 获取额外关联数据
            //人员
            List<String> paraList = new ArrayList<>();
            for (PurchaseReturnInfoEntity obj : list) {
                paraList.add(obj.getEmployeeId());
            }
            Map employeeMap;
            try {
                employeeMap = purchaseEmployeeService.getEmployeeNameMap(paraList);
            } catch (Exception e) {
                logger.error("调用人员管理出现错误", e);
                return new Result(9999, "调用人员管理出现错误！", null);
            }


            //供应商
            List<String> supplierIdsList = new ArrayList<>();
            for (PurchaseReturnInfoEntity obj : list) {
                supplierIdsList.add(obj.getSupplierId());
            }
            Map supplierMap;
            try {
                supplierMap = supplierService.getSupplierNameMap(supplierIdsList);
            } catch (Exception e) {
                logger.error("调用外部供应商接口出错", e);
                return new Result(9999, "调用外部供应商接口出错！请稍后重试或者联系管理员", e);
            }


            //仓库
            List<String> warehouseIdsList = new ArrayList<>();
            for (PurchaseReturnInfoEntity obj : list) {
                warehouseIdsList.add(obj.getWarehouseId());
            }
            Map warehouseNameMap;
            try {
                warehouseNameMap = warehouseService.getWarehouseNameMap(warehouseIdsList);
            } catch (Exception e) {
                logger.error("调用外部仓库接口出错", e);
                return new Result(9999, "调用外部仓库接口出错", e);
            }

            //PO转VO
            List<PurchaseReturnInfoVO> resultsList = new ArrayList<>();
            for (PurchaseReturnInfoEntity obj : list) {
                PurchaseReturnInfoVO pRIVO = new PurchaseReturnInfoVO();
                BeanUtils.copyProperties(obj, pRIVO);
                pRIVO.setEmployeeName((String) employeeMap.get(obj.getEmployeeId()));
                pRIVO.setSupplierName((String) supplierMap.get(obj.getSupplierId()));
                pRIVO.setWarehouseName((String) warehouseNameMap.get(obj.getWarehouseId()));
                pRIVO.setCreateDateStr(obj.getCreateDate());
                resultsList.add(pRIVO);
            }

            Map reMap = new HashMap();
            PageInfo pageInfo = new PageInfo(list);
            long total = pageInfo.getTotal();
            int pages = pageInfo.getPages();
            reMap.put("total", total);
            reMap.put("pages", pages);
            reMap.put("PurchaseReturnInfoList", resultsList);
            return Result.success(reMap);
        }
    }


    private void doExport(HttpServletResponse response, List<GatherPurchaseReturnVO> list) throws IOException {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("采购退货单信息表");
            for (int i = 0; i <= 24; i++) {
                sheet.setColumnWidth(i, 20 * 256);
            }

            //新增数据行，并且设置单元格数据

            int rowNum = 1;

            String[] headers = {"采购退货单号", "日期", "供应商", "采购订单号", "处理人", "仓库", "备注",
                    "库存sku", "产品中文名", "退货数量", "备品数", "批次", "不含税单价", "不含税金额", "税率(%)", "含税单价", "含税金额", "需要包材", "包材仓库", "包材sku", "包材名称", "每产品数量", "消耗数量"
            };
            //headers表示excel表中第一行的表头

            HSSFRow row = sheet.createRow(0);
            //在excel表中添加表头

            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            if (list != null) {
                //在表中存放查询到的数据放入对应的列
                for (int i = 0; i < list.size(); i++) {
                    HSSFRow row1 = sheet.createRow(rowNum);
                    HSSFCellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setWrapText(true);
                    row1.createCell(0).setCellValue(list.get(i).getReturnBill());
                    row1.createCell(1).setCellValue(list.get(i).getCreateDateStr());
                    row1.createCell(2).setCellValue(list.get(i).getSupplierName());
                    row1.createCell(3).setCellValue(list.get(i).getOrderId());
                    row1.createCell(4).setCellValue(list.get(i).getEmployeeName());
                    row1.createCell(5).setCellValue(list.get(i).getWarehouseName());
                    row1.createCell(6).setCellValue(list.get(i).getRemark());
                    row1.createCell(7).setCellValue(list.get(i).getSkuId());
                    row1.createCell(8).setCellValue(list.get(i).getSkuNameZh());
                    row1.createCell(9).setCellValue(list.get(i).getReturnQuantity());
                    row1.createCell(10).setCellValue(list.get(i).getSpareQuantity());
                    row1.createCell(11).setCellValue(list.get(i).getBatchNumber());
                    row1.createCell(12).setCellValue(list.get(i).getUnitPriceWithoutTax());
                    row1.createCell(13).setCellValue(list.get(i).getAmountWithoutTax());
                    row1.createCell(14).setCellValue(list.get(i).getTaxRate());
                    row1.createCell(15).setCellValue(list.get(i).getUnitPriceWithTax());
                    row1.createCell(16).setCellValue(list.get(i).getAmountWithTax());
                    row1.createCell(17).setCellValue(list.get(i).getIsNeedPacking());
                    String reNum = list.get(i).getReturnQuantity();
                    String reSpareNum = list.get(i).getSpareQuantity();
                    int quantity = 0;
                    int spareQuantity = 0;
                    if (StringUtils.isNotEmpty(reNum)) {
                        quantity = Integer.parseInt(reNum);
                    }
                    if (StringUtils.isNotEmpty(reSpareNum)) {
                        spareQuantity = Integer.parseInt(reSpareNum);
                    }
                    Cell cell20 = row1.createCell(18);
                    CellStyle cellStyle2 = workbook.createCellStyle();
//                    cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//                    cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                    cell20.setCellStyle(cellStyle2);
                    cell20.setCellValue(list.get(i).getPackWarehouseName());
                    if (list.get(i).getIsNeedPacking() != null && list.get(i).getIsNeedPacking().equals("Y")) {
                        List<ProductMachineInfoEntity> productMachineInfoEntityList = list.get(i).getProductMachineInfoEntityList();
                        StringBuilder stringBuilderSku = new StringBuilder();
                        StringBuilder stringBuilderSkuName = new StringBuilder();
                        StringBuilder stringBuilderNum = new StringBuilder();
                        StringBuilder stringBuilderUseNum = new StringBuilder();
                        for (ProductMachineInfoEntity productMachineInfoEntity : productMachineInfoEntityList) {
                            int size = 1;
                            if (productMachineInfoEntityList.size() <= 1) {
                                stringBuilderSku.append(productMachineInfoEntity.getDependencySkuId());
                                stringBuilderSkuName.append(productMachineInfoEntity.getSkuNameZh());
                                stringBuilderNum.append(productMachineInfoEntity.getQuantity());
                                stringBuilderUseNum.append(productMachineInfoEntity.getQuantity() * (quantity + spareQuantity));
                            } else {
                                if (size == productMachineInfoEntityList.size()) {
                                    stringBuilderSku.append(productMachineInfoEntity.getDependencySkuId());
                                    stringBuilderSkuName.append(productMachineInfoEntity.getSkuNameZh());
                                    stringBuilderNum.append(productMachineInfoEntity.getQuantity());
                                    stringBuilderUseNum.append(productMachineInfoEntity.getQuantity() * (quantity + spareQuantity));
                                }
                                stringBuilderSku.append(productMachineInfoEntity.getDependencySkuId() + "\r\n");
                                stringBuilderSkuName.append(productMachineInfoEntity.getSkuNameZh() + "\r\n");
                                stringBuilderNum.append(productMachineInfoEntity.getQuantity() + "\r\n");
                                stringBuilderUseNum.append(productMachineInfoEntity.getQuantity() * (quantity + spareQuantity) + "\r\n");
                            }
                            size++;
                        }
                        Cell cell21 = row1.createCell(19);
                        cell21.setCellStyle(cellStyle);
                        cell21.setCellValue(stringBuilderSku.toString());
                        Cell cell22 = row1.createCell(20);
                        cell22.setCellStyle(cellStyle);
                        cell22.setCellValue(stringBuilderSkuName.toString());
                        Cell cell23 = row1.createCell(21);
                        cell23.setCellStyle(cellStyle);
                        cell23.setCellValue(stringBuilderNum.toString());
                        Cell cell24 = row1.createCell(22);
                        cell24.setCellStyle(cellStyle);
                        cell24.setCellValue(stringBuilderUseNum.toString());
                    }
                    rowNum++;
                }
            } else {
                //好歹还是加上表头
                HSSFRow row1 = sheet.createRow(1);
            }
            response.setContentType("application/octet-stream");
            //设置要导出的文件的名字
            String fileName = "purchaseReturnInfoTable" + ".xls";
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            //response.addHeader("Content-Disposition", "attachment;filename=" + new String("采购退货单.xls".getBytes(), "iso-8859-1"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            doPromptException(response, "导出过程出错！请稍后重试或联系管理员", null);
        }
    }

    private String listToParaString(List<String> list) {
        String result;
        if (list == null || list.size() == 0) {
            result = "('')";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                String id = list.get(i);
                if (i == 0) {
                    sb.append("(" + "'" + id + "'");
                } else {
                    sb.append("," + "'" + id + "'");
                }

                if (i == (list.size() - 1)) {
                    sb.append(")");
                }
            }
            result = sb.toString();
        }
        return result;
    }


    private Result checkParaAdd(@Valid AddPurchaseReturnVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        Result result2 = checkParaAdd(vo.getPurchaseReturnInfo(), bindingResult);
        if (result2.getCode() != 200) {
            return result2;
        }

        List<AddPurchaseReturnDetailsVO> list = vo.getPurchaseReturnDetailsList();
        Result result3 = Result.success();
        for (AddPurchaseReturnDetailsVO o : list) {
            result3 = checkParaAdd(o, bindingResult);
            if (result3.getCode() != 200) {
                break;
            }
        }
        return result3;
    }

    private Result checkParaAdd(@Valid AddPurchaseReturnInfoVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        return Result.success();
    }

    private Result checkParaAdd(@Valid AddPurchaseReturnDetailsVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        //税率(%)
        Double taxRate = vo.getTaxRate();
        if (taxRate != null) {
            if (taxRate > 99.99) {
                return new Result(9999, "税率(%)[taxRate]超过额定数额，请修改", null);
            }
        }

        //不含税单价
        Double unitPriceWithoutTax = vo.getUnitPriceWithoutTax();
        if (unitPriceWithoutTax != null) {
            if (unitPriceWithoutTax > 999999999.99) {
                return new Result(9999, "不含税单价[unitPriceWithoutTax]超过额定数额，请修改", null);
            }
        }
        //含税单价
        Double unitPriceWithTax = vo.getUnitPriceWithTax();
        if (unitPriceWithTax != null) {
            if (unitPriceWithTax > 999999999.99) {
                return new Result(9999, "含税单价[unitPriceWithTax]超过额定数额，请修改", null);
            }
        }

        //含税单价
        if (unitPriceWithTax != null) {
            if (taxRate == null) {
                return new Result(9999, "税率(%)[taxRate]缺失，含税单价[unitPriceWithTax]无法计算", null);
            } else {
                if (unitPriceWithoutTax == null) {
                    return new Result(9999, "不含税单价[unitPriceWithoutTax]缺失，含税单价[unitPriceWithTax]无法计算", null);
                }
            }
        }
        if (unitPriceWithoutTax == null) {
            if (taxRate == null) {
                return new Result(9999, "税率(%)[taxRate]缺失,不含税单价[unitPriceWithoutTax]无法计算", null);
            } else {
                if (unitPriceWithTax == null) {
                    return new Result(9999, "含税单价[unitPriceWithTax]缺失，不含税单价[unitPriceWithoutTax]无法计算", null);
                }
            }
        }


        if (vo.getUnitPriceWithoutTax() != null && vo.getReturnQuantity() != null) {
            Double amountWithoutTax = Arith.mul(vo.getUnitPriceWithoutTax(), vo.getReturnQuantity());
            if (!vo.getAmountWithoutTax().equals(Arith.round(amountWithoutTax, 2))) {
                return new Result(9999, "不含税金额数值计算不通过，请修改", null);
            }
        }
        if (vo.getUnitPriceWithTax() != null && vo.getReturnQuantity() != null) {
            Double amountWithTax = Arith.mul(vo.getUnitPriceWithTax(), vo.getReturnQuantity());
            if (!vo.getAmountWithTax().equals(Arith.round(amountWithTax, 2))) {
                return new Result(9999, "含税金额数值计算不通过，请修改", null);
            }
        }
        return Result.success();
    }


    private Result checkParaUpdate(@Valid SetPurchaseReturnVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        Result result2 = checkParaUpdate(vo.getPurchaseReturnInfo(), bindingResult);
        if (result2.getCode() != 200) {
            return result2;
        }

        List<SetPurchaseReturnDetailsVO> list = vo.getPurchaseReturnDetailsList();
        Result result3 = Result.success();
        for (SetPurchaseReturnDetailsVO o : list) {
            result3 = checkParaUpdate(o, bindingResult);
            if (result3.getCode() != 200) {
                break;
            }
        }
        return result3;
    }

    private Result checkParaUpdate(@Valid SetPurchaseReturnInfoVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        return Result.success();
    }

    private Result checkParaUpdate(@Valid SetPurchaseReturnDetailsVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }

        //税率(%)
        Double taxRate = vo.getTaxRate();
        if (taxRate != null) {
            if (taxRate > 99.99) {
                return new Result(9999, "税率(%)[taxRate]超过额定数额，请修改", null);
            }
        }

        //不含税单价
        if (vo.getUnitPriceWithoutTax() != null) {
            if (vo.getUnitPriceWithoutTax() > 999999999.99) {
                return new Result(9999, "不含税单价[unitPriceWithoutTax]超过额定数额，请修改", null);
            }
        }
        //含税单价
        if (vo.getUnitPriceWithTax() != null) {
            if (vo.getUnitPriceWithTax() > 999999999.99) {
                return new Result(9999, "含税单价[unitPriceWithTax]超过额定数额，请修改", null);
            }
        }

        //含税单价
        Double unitPriceWithTax = vo.getUnitPriceWithTax();
        //不含税单价
        Double unitPriceWithoutTax = vo.getUnitPriceWithoutTax();
        if (unitPriceWithTax != null) {
            //不含税单价
            if (taxRate == null) {
                return new Result(9999, "税率(%)[taxRate]缺失，含税单价[unitPriceWithTax]无法计算", null);
            } else {
                if (unitPriceWithoutTax == null) {
                    return new Result(9999, "不含税单价[unitPriceWithoutTax]缺失，含税单价[unitPriceWithTax]无法计算", null);
                } else {
                    double i = Arith.div(taxRate, 100, 2);
                    i = Arith.add(1, i);
                    i = Arith.mul(unitPriceWithoutTax, i);
                    i = Arith.round(i, 2);
                    Double unitPriceWithTaxTwo = Arith.round(unitPriceWithTax, 2);
                    if (!unitPriceWithTaxTwo.equals(i)) {
                        return new Result(9999, "含税单价[unitPriceWithTax]计算不通过，请修改", null);
                    }
                }
            }
        }
        if (unitPriceWithoutTax == null) {
            if (taxRate == null) {
                return new Result(9999, "税率(%)[taxRate]缺失,不含税单价[unitPriceWithoutTax]无法计算", null);
            } else {
                if (unitPriceWithTax == null) {
                    return new Result(9999, "含税单价[unitPriceWithTax]缺失，不含税单价[unitPriceWithoutTax]无法计算", null);
                } else {
                    double i = Arith.div(taxRate, 100, 2);
                    i = Arith.add(1, i);
                    i = Arith.div(unitPriceWithTax, i, 2);
                    i = Arith.round(i, 2);
                    Double unitPriceWithoutTaxTwo = Arith.round(unitPriceWithoutTax, 2);
                    if (!unitPriceWithoutTaxTwo.equals(i)) {
                        return new Result(9999, "不含税单价[unitPriceWithoutTax]计算不通过，请修改", null);
                    }
                }
            }
        }

        //不含税单价  退货数量
        if (vo.getUnitPriceWithoutTax() != null && vo.getReturnQuantity() != null) {
            Double amountWithoutTax = Arith.mul(vo.getUnitPriceWithoutTax(), vo.getReturnQuantity());
            if (!vo.getAmountWithoutTax().equals(Arith.round(amountWithoutTax, 2))) {
                return new Result(9999, "不含税金额数值计算不通过，请修改", null);
            }
            vo.setAmountWithoutTax(amountWithoutTax);
        }
        if (vo.getUnitPriceWithTax() != null && vo.getReturnQuantity() != null) {
            Double amountWithTax = Arith.mul(vo.getUnitPriceWithTax(), vo.getReturnQuantity());
            if (!vo.getAmountWithTax().equals(Arith.round(amountWithTax, 2))) {
                return new Result(9999, "含税金额数值计算不通过，请修改", null);
            }
        }
        return Result.success();
    }

    private void doPromptException(HttpServletResponse response, String exceptionMsg, Object e) throws IOException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        Result result = new Result(9999, exceptionMsg, e);
        response.getWriter().print(result);
    }


    /**
     * 动态验证采购订单状态
     *
     * @param orderId
     * @return
     */
    private int toOrderStatus(String orderId) {
        //动态验证产品状态
        try {
            List<OrderDetailsProductInfoEntity> eles = orderDetailsProductInfoDao.findInfoById(orderId);
            //此订单下sku对应的状态有多少条数据
            int a = 0;//为1--待交货的条数
            int b = 0;//为2--部分交货的条数
            int c = 0;//为3--完成 和4--中止的条数
            for (OrderDetailsProductInfoEntity ele : eles) {
                if (ele.getDeleteStatus() == 1) {
                    c += 1;
                    continue;
                }
                String skuId = ele.getSkuId();
                EntrySumNumEntity entrySumNumEntity = orderDetailsProductInfoDao.EntrySumNumEntity(skuId, orderId);
                EntrySumNumReturn entrySumNumReturn = orderDetailsProductInfoDao.EntrySumNumReturn(skuId, orderId);
                int quantity = 0;
                int sumEntry = 0;
                int sumReturn = 0;
                if (entrySumNumEntity != null) {
                    quantity = entrySumNumEntity.getQuantity();
                    sumEntry = entrySumNumEntity.getSumEntry();
                }
                if (entrySumNumReturn != null) {
                    sumReturn = entrySumNumReturn.getSumReturn();
                }
                if (entrySumNumEntity == null && entrySumNumReturn == null) {
                    a += 1;
                    continue;
                }

                //欠货数量
                int lessQuantity = quantity - sumEntry + sumReturn;
                if (lessQuantity == quantity) {
                    a += 1;
                    continue;
                }
                if (lessQuantity > 0 && lessQuantity < quantity) {
                    b += 1;
                    continue;
                } else if (lessQuantity <= 0) {
                    c += 1;
                    continue;
                }

            }
            if (a != 0 && b == 0 && c == 0) {//当订单产品所有状态都是1--待交货，订单状态为1--待交货
                return 1;
            } else if (a == 0 && b == 0 && c != 0) {//当订单产品所有状态都是3--完成或者4--中止时候订单状态为 3--完成
                return 3;
            } else if (b != 0) {//2-部分交货
                return 2;
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

}
