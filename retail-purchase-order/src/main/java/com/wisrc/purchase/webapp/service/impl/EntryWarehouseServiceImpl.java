package com.wisrc.purchase.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wisrc.purchase.webapp.dao.*;
import com.wisrc.purchase.webapp.dto.purchasePlan.SkuInfo;
import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.service.*;
import com.wisrc.purchase.webapp.service.externalService.ProductService;
import com.wisrc.purchase.webapp.service.externalService.ProductSkuService;
import com.wisrc.purchase.webapp.utils.PageData;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.utils.Toolbox;
import com.wisrc.purchase.webapp.vo.AddDetailsProdictAllVO;
import com.wisrc.purchase.webapp.vo.EntryWarehouseExportVo;
import com.wisrc.purchase.webapp.vo.OrderBasisInfoAllVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.EntryWarehouseAllVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.EntryWarehouseProductVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.EntryWarehouseVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.ReturnEntryPara;
import com.wisrc.purchase.webapp.vo.purchaseReturn.show.PurchaseReturnDetailsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Service
public class EntryWarehouseServiceImpl implements EntryWarehouseService {
    @Autowired
    private EntryWarehouseDao entryWarehouseDao;
    @Autowired
    private EntryWarehouseProductDao entryWarehouseProductDao;
    @Autowired
    private PurchaseReturnDao purchaseReturnDao;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private PurchaseEmployeeService purchaseEmployeeService;
    @Autowired
    private ProductHandleService productHandleService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private PurchcaseOrderBasisInfoService purchcaseOrderBasisInfoService;
    @Autowired
    private OrderDetailsProductInfoDao orderDetailsProductInfoDao;
    @Autowired
    private PurchaseReturnService purchaseReturnService;
    @Autowired
    private OrderBasisDao orderBasisDao;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierOutsideService supplierOutsideService;

    @Override
    public LinkedHashMap infoList(int startPage, int pageSize, String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierId) {

        PageHelper.startPage(startPage, pageSize);
        List<EntryWarehouseEntity> enList = entryWarehouseDao.findInfo(entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierId);
        List<EntryWarehouseVO> voList = new ArrayList<>();
        for (EntryWarehouseEntity ele : enList) {
            EntryWarehouseVO vo = new EntryWarehouseVO();
            vo = EntryWarehouseVO.toVO(ele);
            voList.add(vo);
        }
        PageInfo info = new PageInfo(enList);
        toVoInfo(voList);
        return PageData.pack(info.getTotal(), info.getPages(), "entryWarehouseList", voList);
    }

    @Override
    public LinkedHashMap infoList(String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierId) {

        List<EntryWarehouseEntity> enList = entryWarehouseDao.findInfo(entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierId);
        List<EntryWarehouseVO> voList = new ArrayList<>();
        for (EntryWarehouseEntity ele : enList) {
            EntryWarehouseVO vo = new EntryWarehouseVO();
            vo = EntryWarehouseVO.toVO(ele);
            voList.add(vo);
        }
        toVoInfo(voList);
        return PageData.pack(voList.size(), 1, "entryWarehouseList", voList);
    }


    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result updateInfoById(EntryWarehouseEntity ele, List<EntryWarehouseProductEntity> productList) {
        entryWarehouseDao.updateInfoById(ele);
        for (EntryWarehouseProductEntity prEle : productList) {
            List<SkuInfo> skuInfos = getSkuInfos(ele.getOrderId());
            for (SkuInfo skuInfo : skuInfos) {
                if (skuInfo.getSkuId().equals(prEle.getSkuId()) && skuInfo.getQuantity() < prEle.getEntryNum()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.failure(9990,
                            String.format("%s的数量不能超过可入库数量%d", skuInfo.getSkuName(), skuInfo.getQuantity()),
                            null);
                }
            }
            if (prEle.getUuid() == null || prEle.getUuid().equals("")) {
                prEle.setUuid(UUID.randomUUID().toString());
                entryWarehouseProductDao.addInfo(prEle);
            } else {
                int s = entryWarehouseProductDao.numById(prEle.getUuid());
                if (s == 0) {
                    entryWarehouseProductDao.updateStatusById(prEle.getUuid());
                } else if (s == 1) {
                    entryWarehouseProductDao.updateInfoById(prEle);
                }
            }
        }
        return Result.success("采购入库单修改成功");
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result addInfo(EntryWarehouseAllVO entryAllVO, String userId) {
        // 校验 入库数量、入备品数至少有一项大于0
        List<EntryWarehouseProductVO> entryWarehouseProductVOList = entryAllVO.getEntryWarehouseProductVO();
        StringBuilder message = new StringBuilder("入库通知单产品");
        for (int i = 0; i < entryWarehouseProductVOList.size(); i++) {
            EntryWarehouseProductVO entryWarehouseProductVO = entryWarehouseProductVOList.get(i);
            if (entryWarehouseProductVO.getEntryNum() + entryWarehouseProductVO.getEntryFrets() <= 0) {
                message.append("第").append(i + 1).append("项").append("、");
            }
        }
        if (message.length() > 8) {
            message = message.deleteCharAt(message.length() - 1);
            message.append("入库数量和入备品数至少需要一项大于0");
            return new Result(670, message.toString(), null);
        }

        String entryId = Toolbox.randomUUID();
        EntryWarehouseEntity en = new EntryWarehouseEntity();
        BeanUtils.copyProperties(entryAllVO.getEntryWarehouseVO(), en);
        en.setEntryId(entryId);
        Timestamp current = Time.getCurrentTimestamp();
        en.setCreateTime(current);
        en.setCreateUser(userId);
        en.setModifyTime(current);
        en.setModifyUser(userId);
        en.setDeleteStatus(0);
        entryWarehouseDao.addInfo(en);
        entryId = entryWarehouseDao.findEntryId(entryId);
        String orderId = en.getOrderId();
        List<SkuInfo> skuInfos = getSkuInfos(orderId);


        for (EntryWarehouseProductVO prvo : entryAllVO.getEntryWarehouseProductVO()) {
            EntryWarehouseProductEntity ele = new EntryWarehouseProductEntity();
            BeanUtils.copyProperties(prvo, ele);
            ele.setCreateTime(new Timestamp(System.currentTimeMillis()));
            ele.setEntryId(entryId);
            ele.setCreateUser(userId);
            ele.setDeleteStatus(0);
            ele.setUuid(Toolbox.randomUUID());
            entryWarehouseProductDao.addInfo(ele);
        }
        int statusCd = toOrderStatus(orderId);
        orderBasisDao.changeStatus(statusCd, orderId);
        return Result.success(entryId);
    }

    private List<SkuInfo> getSkuInfos(String orderId) {
        // fix 入库单sku数量等于采购单-之前的入库单（可能多张）+退货单（可能多张）
        List<SkuInfo> skuInfos = new ArrayList<>(); //所有的sku的信息集合
        //获得采购单信息
        OrderBasisInfoAllVO orderBasisInfoAllVO = purchcaseOrderBasisInfoService.findInfoAllVoById(orderId);
        List<AddDetailsProdictAllVO> allDetailsProduct = orderBasisInfoAllVO.getEleVOList();
        //获得所有sku信息
        for (AddDetailsProdictAllVO addDetailsProdictAllVO : allDetailsProduct) {
            SkuInfo skuInfo = new SkuInfo(addDetailsProdictAllVO.getSkuId(),
                    addDetailsProdictAllVO.getQuantity(), addDetailsProdictAllVO.getSkuName());
            skuInfos.add(skuInfo);
        }

        //获得所有入库订单信息
        List<EntryWarehouseEntity> entryWarehouseEntities = entryWarehouseDao.findInfoxById(orderId);

        //从所有sku中减去相应的入库单中的sku数量
        for (EntryWarehouseEntity entryWarehouseEntity : entryWarehouseEntities) {
            //转换类型
            EntryWarehouseAllVO entryWarehouseAllVO = toEntryWarehouseAllVO(entryWarehouseEntity);
            //取得所有sku
            List<EntryWarehouseProductVO> entryWarehouseProductVOS = entryWarehouseAllVO.getEntryWarehouseProductVO();
            for (EntryWarehouseProductVO entryWarehouseProductVO : entryWarehouseProductVOS) {
                for (SkuInfo skuInfo : skuInfos) {
                    if (skuInfo.getSkuId().equals(entryWarehouseProductVO.getSkuId())) {
                        skuInfo.decreamQuantity(entryWarehouseProductVO.getEntryNum());
                    }
                }

            }
        }
        //获得相应的退货单
        List<PurchaseReturnInfoEntity> purchaseReturnList = purchaseReturnDao.getPurchaseReturnListByOrderId(orderId);
        //从所有sku中加上相应的退货单中的sku数量
        for (PurchaseReturnInfoEntity purchaseReturnInfoEntity : purchaseReturnList) {
            Result result = purchaseReturnService.handleReturnBill(purchaseReturnInfoEntity);
            if (result.getCode() != 200) {
                continue;
            }
            Map<String, Object> data = (Map<String, Object>) result.getData();
            List<PurchaseReturnDetailsVO> purchaseRejectionDetailsList = (List<PurchaseReturnDetailsVO>) data.get("purchaseRejectionDetailsList");

            //取得所有sku

            for (PurchaseReturnDetailsVO purchaseReturnDetailsVO : purchaseRejectionDetailsList) {
                for (SkuInfo skuInfo : skuInfos) {
                    if (skuInfo.getSkuId().equals(purchaseReturnDetailsVO.getSkuId())) {
                        skuInfo.increamQuantity(purchaseReturnDetailsVO.getReturnQuantity());
                    }
                }
            }
        }
        return skuInfos;
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public void updateStatusById(String entryId, String user) {
        entryWarehouseDao.updateStatusById(entryId, new Timestamp(System.currentTimeMillis()), user);
        List<EntryWarehouseProductEntity> eleList = entryWarehouseProductDao.findInfoAll(entryId);
        for (EntryWarehouseProductEntity ele : eleList) {
            entryWarehouseProductDao.updateStatusById(ele.getUuid());
        }
    }

    @Override
    public EntryWarehouseAllVO findInfoById(String entryId) {
        EntryWarehouseEntity ele = entryWarehouseDao.findInfoById(entryId);
        return toEntryWarehouseAllVO(ele);
    }

    private EntryWarehouseAllVO toEntryWarehouseAllVO(EntryWarehouseEntity ele) {
        EntryWarehouseAllVO vo = new EntryWarehouseAllVO();
        if (ele != null) {
            EntryWarehouseVO infoVo = EntryWarehouseVO.toVO(ele);
            List<EntryWarehouseVO> infoList = new ArrayList<>();
            infoList.add(infoVo);
            toVoInfo(infoList);
            vo.setEntryWarehouseVO(infoList.get(0));
        }
        List skuIds = new ArrayList();
        Map<String, String> skuIdNameMap = new HashMap();
        List<EntryWarehouseProductEntity> prList = entryWarehouseProductDao.findInfoAll(ele.getEntryId());
        List<EntryWarehouseProductVO> prVoList = new ArrayList<>();
        for (EntryWarehouseProductEntity prEle : prList) {
            EntryWarehouseProductVO prVo = new EntryWarehouseProductVO();
            skuIds.add(prEle.getSkuId());
            prVo = EntryWarehouseProductVO.toVO(prEle);
            prVoList.add(prVo);
        }
        try {
            skuIdNameMap = productHandleService.getProductCNNameMap(skuIds);
            for (EntryWarehouseProductVO enPrVo : prVoList) {
                enPrVo.setSkuName(skuIdNameMap.get(enPrVo.getSkuId()));
            }
        } catch (Exception e) {

        }
        vo.setEntryWarehouseProductVO(prVoList);
        return vo;
    }


    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public void delete(ReturnEntryPara returnEntryPara, String userId) {
        List<String> returnEntryList = returnEntryPara.getReturnEntryList();
        if (returnEntryList != null) {
            for (String id : returnEntryList) {
                entryWarehouseDao.updateStatusById(id, new Timestamp(System.currentTimeMillis()), userId);
                List<EntryWarehouseProductEntity> eleList = entryWarehouseProductDao.findInfoAll(id);
                for (EntryWarehouseProductEntity ele : eleList) {
                    entryWarehouseProductDao.updateStatusById(ele.getUuid());
                }
            }
        }
    }

    @Override
    public List<String> findAllEntryIdByInspectionId(String inspectionId) {
        List<String> entryIds = entryWarehouseDao.findAllEntryIdByInspectionId(inspectionId);
        return entryIds;
    }

    @Override
    public EntryWarehouseProductEntity getEntryProductByEntryIdAndSkuId(String entryId, String skuId) {
        return entryWarehouseProductDao.findEntryProductByEntryIdAndSkuId(entryId, skuId);
    }

    @Override
    public LinkedHashMap findInfoIds(String[] ids) {
        LinkedHashMap result = new LinkedHashMap();
        List<EntryWarehouseEntity> enList = entryWarehouseDao.findInfoIds(ids);
        List<EntryWarehouseVO> voList = new ArrayList<>();
        for (EntryWarehouseEntity ele : enList) {
            EntryWarehouseVO vo = new EntryWarehouseVO();
            vo = EntryWarehouseVO.toVO(ele);
            voList.add(vo);
        }
        toVoInfo(voList);
        result.put("entryWarehouseList", voList);
        return result;
    }

    private List<EntryWarehouseVO> toVoInfo(List<EntryWarehouseVO> list) {
        List supplierIds = new ArrayList();
        List employeeIds = new ArrayList();
        List warehouseIds = new ArrayList();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();
        Map<String, String> warehouseNameMap = new HashMap();
        for (EntryWarehouseVO vo : list) {
            supplierIds.add(vo.getSupplierCd());
            employeeIds.add(vo.getEntryUser());
            warehouseIds.add(vo.getWarehouseId());
        }
        try {
            supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
            employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
            warehouseNameMap = warehouseService.getWarehouseNameMap(warehouseIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (EntryWarehouseVO vo : list) {
            vo.setSupplierName(supplierNameMap.get(vo.getSupplierCd()));
            vo.setEmployeeName(employeeNameMap.get(vo.getEntryUser()));
            vo.setWarehouseName(warehouseNameMap.get(vo.getWarehouseId()));
        }
        return list;
    }

    @Override
    public List<EntryWarehouseProductEntity> getEntryProductByEntryIds(List<String> ids) {
        return entryWarehouseProductDao.findEntryProductByEntryIds(ids);
    }

    @Override
    public LinkedHashMap infoListNew(int num, int size, String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierName, String orderId, String skuId, String productName) {
        List<String> skuIdList = new ArrayList<>();
        List<String> supplierList = new ArrayList<>();
        List<EntryWarehouseVO> voList = new ArrayList<>();
        if (StringUtils.isNotEmpty(productName)) {
            Result proResult = productSkuService.getProductSkuInfo(null, null, productName);
            if (proResult.getCode() != 200) {
                throw new RuntimeException("产品外部接口调用异常");
            }
            Map proResultMap = (Map) proResult.getData();
            List<Map> proMapList = (List<Map>) proResultMap.get("productData");
            if (proMapList == null || proMapList.size() <= 0) {
                return PageData.pack(0, 1, "entryWarehouseList", voList);
            }
            for (Map skuMap : proMapList) {
                String thisSkuId = (String) skuMap.get("sku");
                skuIdList.add(thisSkuId);
            }
        }
        if (supplierName != null) {
            try {
                Map map = supplierOutsideService.getSupplierList(supplierName, 1, Integer.MAX_VALUE);
                Integer count = (Integer) map.get("supplierCount");
                if (count.equals(0)) {
                    return PageData.pack(0, 1, "entryWarehouseList", voList);
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
        for (String thisSkuId : skuIdList) {
            skuIds += "'" + thisSkuId + "'" + ",";
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
        PageHelper.startPage(num, size);
        List<EntryWarehouseEntity> enList = entryWarehouseDao.findInfoNew(entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierName, suppliers, skuIds, orderId, skuId);
        for (EntryWarehouseEntity ele : enList) {
            EntryWarehouseVO vo = new EntryWarehouseVO();
            vo = EntryWarehouseVO.toVO(ele);
            voList.add(vo);
        }
        PageInfo info = new PageInfo(enList);
        toVoInfo(voList);
        return PageData.pack(info.getTotal(), info.getPages(), "entryWarehouseList", voList);
    }

    @Override
    public LinkedHashMap infoListNew(String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierName, String orderId, String skuId, String productName) {
        List<String> skuIdList = new ArrayList<>();
        List<String> supplierList = new ArrayList<>();
        List<EntryWarehouseVO> voList = new ArrayList<>();
        if (StringUtils.isNotEmpty(productName)) {
            Result proResult = productSkuService.getProductSkuInfo(null, null, productName);
            if (proResult.getCode() != 200) {
                throw new RuntimeException("产品外部接口调用异常");
            }
            Map proResultMap = (Map) proResult.getData();
            List<Map> proMapList = (List<Map>) proResultMap.get("productData");
            if (proMapList == null || proMapList.size() <= 0) {
                return PageData.pack(0, 1, "entryWarehouseList", voList);
            }
            for (Map skuMap : proMapList) {
                String thisSkuId = (String) skuMap.get("sku");
                skuIdList.add(thisSkuId);
            }
        }
        if (supplierName != null) {
            try {
                Map map = supplierOutsideService.getSupplierList(supplierName, 1, Integer.MAX_VALUE);
                Integer count = (Integer) map.get("supplierCount");
                if (count.equals(0)) {
                    return PageData.pack(0, 1, "entryWarehouseList", voList);
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
        for (String thisSkuId : skuIdList) {
            skuIds += "'" + thisSkuId + "'" + ",";
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
        List<EntryWarehouseEntity> enList = entryWarehouseDao.findInfoNew(entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierName, suppliers, skuIds, orderId, skuId);
        for (EntryWarehouseEntity ele : enList) {
            EntryWarehouseVO vo = new EntryWarehouseVO();
            vo = EntryWarehouseVO.toVO(ele);
            voList.add(vo);
        }
        PageInfo info = new PageInfo(enList);
        toVoInfo(voList);
        return PageData.pack(info.getTotal(), info.getPages(), "entryWarehouseList", voList);
    }

    @Override
    public List<EntryWarehouseExportVo> infoListNewExport(String entryId, Date entryTimeBegin, Date entryTimeEnd, String supplierDeliveryNum, String inspectionId, String entryUser, String warehouseId, String supplierName, String orderId, String skuId, String productName) {
        List<String> skuIdList = new ArrayList<>();
        List<String> supplierList = new ArrayList<>();
        List<EntryWarehouseVO> voList = new ArrayList<>();
        if (StringUtils.isNotEmpty(productName)) {
            Result proResult = productSkuService.getProductSkuInfo(null, null, productName);
            if (proResult.getCode() != 200) {
                throw new RuntimeException("产品外部接口调用异常");
            }
            Map proResultMap = (Map) proResult.getData();
            List<Map> proMapList = (List<Map>) proResultMap.get("productData");
            if (proMapList == null || proMapList.size() <= 0) {
                return null;
            }
            for (Map skuMap : proMapList) {
                String thisSkuId = (String) skuMap.get("sku");
                skuIdList.add(thisSkuId);
            }
        }
        if (supplierName != null) {
            try {
                Map map = supplierOutsideService.getSupplierList(supplierName, 1, Integer.MAX_VALUE);
                Integer count = (Integer) map.get("supplierCount");
                if (count.equals(0)) {
                    return null;
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
        for (String thisSkuId : skuIdList) {
            skuIds += "'" + thisSkuId + "'" + ",";
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
        List<EntryWarehouseExportVo> enList = entryWarehouseDao.findInfoNewExport(entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierName, suppliers, skuIds, orderId, skuId);
        Result result = productService.getAllSkuPackingMaterial();
        if (result.getCode() != 200) {
            return null;
        }
        Map<String, List> packMap = (Map<String, List>) result.getData();
        Set<String> skuSet = new HashSet<>();
        for (EntryWarehouseExportVo entryWarehouseExportVo : enList) {
            skuSet.add(entryWarehouseExportVo.getSkuId());
        }
        Map parameproMap = new HashMap();
        parameproMap.put("skuIdList", skuSet);
//      通过上诉skuId查询商品库存名称
        Gson gson = new Gson();
        Map<String, String> productCnMap = new HashMap<>();
        Result productResult = productSkuService.getProductInfo(gson.toJson(parameproMap));
        if (productResult.getCode() == 200) {
            List objects = (List) productResult.getData();
            for (Object product : objects) {
                Map proMap = (Map) product;
                Map define = (Map) proMap.get("define");
                if (define != null) {
                    String theskuId = (String) define.get("skuId");
                    String skuNameZh = (String) define.get("skuNameZh");
                    productCnMap.put(theskuId, skuNameZh);
                }
            }
        }
        for (EntryWarehouseExportVo entryWarehouseExportVo : enList) {
            String enskuId = entryWarehouseExportVo.getSkuId();
            List<ProductMachineInfoEntity> productMachineInfoEntityList = new ArrayList<>();
            entryWarehouseExportVo.setSkuName(productCnMap.get(enskuId));
            List<Map> packList = packMap.get(enskuId);
            if (packList != null) {
                entryWarehouseExportVo.setIsNeedPacking("Y");
                for (Map map : packList) {
                    ProductMachineInfoEntity productMachineInfoEntity = new ProductMachineInfoEntity();
                    productMachineInfoEntity.setSkuNameZh((String) map.get("skuNameZh"));
                    productMachineInfoEntity.setQuantity((Integer) map.get("quantity"));
                    productMachineInfoEntity.setDependencySkuId((String) map.get("dependencySkuId"));
                    productMachineInfoEntityList.add(productMachineInfoEntity);
                }
                entryWarehouseExportVo.setProductMachineInfoEntityList(productMachineInfoEntityList);
            } else {
                entryWarehouseExportVo.setIsNeedPacking("N");
            }
        }
        return enList;
    }


    /**
     * 动态验证产品状态
     *
     * @param skuId
     * @param orderId
     * @return
     */
    private int toStatus(String skuId, String orderId) {
        //动态验证产品状态
        try {
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
                return 1;
            }
            //欠货数量
            int lessQuantity = quantity - (sumEntry + sumReturn);
            if (lessQuantity == quantity) {
                return 1;
            }
            if (lessQuantity > 0) {
                return 2;
            } else if (lessQuantity <= 0 && lessQuantity < quantity) {
                return 3;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
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
                int lessQuantity = quantity - (sumEntry + sumReturn);
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

