package com.wisrc.purchase.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.purchase.webapp.dao.OrderBasisDao;
import com.wisrc.purchase.webapp.dao.OrderDetailsProductInfoDao;
import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.service.*;
import com.wisrc.purchase.webapp.service.externalService.InspectionService;
import com.wisrc.purchase.webapp.utils.PageData;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.*;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderProvisionVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Service
public class PurchcaseOrderBasisInfoServiceImpl implements PurchcaseOrderBasisInfoService {

    private final static Logger logger = LoggerFactory.getLogger(PurchcaseOrderBasisInfoServiceImpl.class);
    @Autowired
    private OrderBasisDao orderBasisDao;
    @Autowired
    private OrderDetailsProductInfoDao orderDetailsProductInfoDao;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private PurchaseEmployeeService purchaseEmployeeService;
    @Autowired
    private ProductHandleService productHandleService;
    @Autowired
    private OrderProvisionService orderProvisionService;
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private PurchcaseOrderBasisInfoService purchcaseOrderBasisInfoService;
    @Autowired
    private PurchasePlanService purchasePlanService;
    @Autowired
    private SupplierOutsideService supplierOutsideService;

    public static String toInString(List ids) {
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.size(); i++) {
            str = "'" + ids.get(i) + "'";
            if (i < ids.size() - 1) {
                str += ",";
            }
            endstr += str;
        }
        return endstr;
    }

    @Override
    public LinkedHashMap findBasisInfo(int startPage, int pageSize, String orderId, String employeeId, int deliveryTypeCd, String keyword, int tiicketOpenCd, int customsTypeCd, Date billDateBegin, Date billDateEnd, Date deliveryTimeBegin, Date deliveryTimeEnd, String supplierId) {
        String suppliers = "";
        if (StringUtils.isEmpty(keyword)) {
            keyword = null;
        }
        if (keyword != null) {
            try {
                Map map = supplierOutsideService.getSupplierList(keyword, 1, Integer.MAX_VALUE);
                List<Map> supplierMapList = (List<Map>) map.get("suppliers");
                List<String> supplierList = new ArrayList<>();
                Integer count = (Integer) map.get("supplierCount");
                if (!count.equals(0)) {
                    for (Map supplierMap : supplierMapList) {
                        String supplier = (String) supplierMap.get("supplierId");
                        supplierList.add(supplier);
                    }
                }
                for (String supplier : supplierList) {
                    suppliers += "'" + supplier + "'" + ",";
                }
                if (suppliers.endsWith(",")) {
                    int index = suppliers.lastIndexOf(",");
                    suppliers = suppliers.substring(0, index);
                }
                if (StringUtils.isEmpty(suppliers)) {
                    suppliers = null;
                }
            } catch (Exception e) {
                throw new RuntimeException("供应商接口调用异常");
            }
        }
        PageHelper.startPage(startPage, pageSize);
        List<OrderBasisInfoEntity> EnList = orderBasisDao.findBasisInfo(orderId, employeeId, deliveryTypeCd, keyword, tiicketOpenCd, customsTypeCd, billDateBegin, billDateEnd, deliveryTimeBegin, deliveryTimeEnd, supplierId, suppliers);
        PageInfo info = new PageInfo(EnList);
        toOrderBasis(EnList);
        return PageData.pack(info.getTotal(), info.getPages(), "orderBasisInfoList", EnList);
    }

    @Override
    public LinkedHashMap findBasisInfo(String orderId, String employeeId, int deliveryTypeCd, String keyword, int tiicketOpenCd, int customsTypeCd, Date billDateBegin, Date billDateEnd, Date deliveryTimeBegin, Date deliveryTimeEnd, String supplierId) {
        List<OrderBasisInfoEntity> EnList = orderBasisDao.findBasisInfo(orderId, employeeId, deliveryTypeCd, keyword, tiicketOpenCd, customsTypeCd, billDateBegin, billDateEnd, deliveryTimeBegin, deliveryTimeEnd, supplierId, toSqlKeyword(keyword));
        return PageData.pack(EnList.size(), 1, "orderBasisInfoList", toOrderBasis(EnList));
    }

    @Override
    public List<OrderBasisInfoEntity> findBasisInfoAll() {
        return toOrderBasis(orderBasisDao.findBasisInfoAll());
    }

    @Override
    public LinkedHashMap findBasisNeet(int startPage, int pageSize, String orderId, String supplierName, String skuId, String productNameCN) {
        List supplierIds = new ArrayList();
        List skuIds = new ArrayList();
        try {
            if (supplierName != null && !("".equals(supplierName))) {
                supplierIds = supplierService.getFindKeyDealted(supplierName);
            }
        } catch (Exception e) {

        }
        try {
            if (productNameCN != null && !("".equals(productNameCN))) {
                skuIds = productHandleService.getByNameToIds(productNameCN);
            }
        } catch (Exception e) {
        }
        PageHelper.startPage(startPage, pageSize);
        List<OrderNeetVO> basisNeet = orderBasisDao.findBasisNeet(orderId, supplierIds, skuId, skuIds);
        PageInfo info = new PageInfo(basisNeet);
        if (basisNeet != null) {
            return PageData.pack(info.getTotal(), info.getPages(), "orderNeetList", toName(basisNeet));
        }
        return PageData.pack(info.getTotal(), info.getPages(), "orderNeetList", "");
    }

    @Override
    public LinkedHashMap findBasisNeet(int startPage, int pageSize, String keyWords, String skuId, String orderId) {
        List supplierIds = new ArrayList();
        List skuIds = new ArrayList();
        if (keyWords != null && !("".equals(keyWords))) {
            try {
                supplierIds = supplierService.getFindKeyDealted(keyWords);
            } catch (Exception e) {

            }
            try {
                skuIds = productHandleService.getByNameToIds(keyWords);
            } catch (Exception e) {
            }
        }
        PageHelper.startPage(startPage, pageSize);
        List<OrderNeetVO> basisNeet = orderBasisDao.findBasisNeet2(orderId, supplierIds, skuId, skuIds);
        PageInfo info = new PageInfo(basisNeet);
        if (basisNeet != null) {
            return PageData.pack(info.getTotal(), info.getPages(), "orderNeetList", toName(basisNeet));
        }
        return PageData.pack(info.getTotal(), info.getPages(), "orderNeetList", "");
    }

    @Override
    public LinkedHashMap findBasisNeet(String orderId, String supplierName, String skuId, String productNameCN) {
        List supplierIds = new ArrayList();
        List skuIds = new ArrayList();
        try {
            if (supplierName != null && !("".equals(supplierName))) {
                supplierIds = supplierService.getFindKeyDealted(supplierName);
            }
        } catch (Exception e) {
        }
        try {
            if (productNameCN != null && !("".equals(productNameCN))) {
                skuIds = productHandleService.getByNameToIds(productNameCN);
            }
        } catch (Exception e) {
        }

        List<OrderNeetVO> basisNeet = orderBasisDao.findBasisNeet(orderId, supplierIds, skuId, skuIds);

        if (basisNeet != null) {
            return PageData.pack(basisNeet.size(), 1, "orderNeetList", toName(basisNeet));
        }
        return PageData.pack(0, 0, "orderNeetList", "");
    }

    @Override
    public LinkedHashMap findBasisNeet(String keyWords, String skuId, String orderId) {
        List supplierIds = new ArrayList();
        List skuIds = new ArrayList();
        if (keyWords != null && !("".equals(keyWords))) {
            try {
                supplierIds = supplierService.getFindKeyDealted(keyWords);
            } catch (Exception e) {
            }
            try {
                skuIds = productHandleService.getByNameToIds(keyWords);
            } catch (Exception e) {
            }
        }
        List<OrderNeetVO> basisNeet = orderBasisDao.findBasisNeet2(orderId, supplierIds, skuId, skuIds);
        if (basisNeet != null) {
            return PageData.pack(basisNeet.size(), 1, "orderNeetList", toName(basisNeet));
        }
        return PageData.pack(0, 0, "orderNeetList", "");
    }

    @Override
    public LinkedHashMap findBasisInfoAll(int startPage, int pageSize) {
        PageHelper.startPage(startPage, pageSize);
        List<OrderBasisInfoEntity> EnList = orderBasisDao.findBasisInfoAll();
        PageInfo info = new PageInfo(EnList);
        toOrderBasis(EnList);
        return PageData.pack(info.getTotal(), info.getPages(), "orderBasisInfoList", EnList);
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result addOrderInfo(List<AddDetailsProdictAllVO> eleAllVOList, OrderBasisInfoVO orderVO) {
        //截取出订单基本板块信息
        String orderId = orderVO.getOrderId();
        OrderBasisInfoEntity basisEn = new OrderBasisInfoEntity();
        BeanUtils.copyProperties(orderVO, basisEn);
        basisEn.setOrderStatus(0);
        List<String> purchasePlanIdList = new ArrayList<>();
        //新增订单基本信息
        orderBasisDao.addBasisInfo(basisEn);
        //截取出附件信息集合
        List<OrderAttachmentInfoEntity> attachmentList = orderVO.getOderAttachmentInfoEntityList();
        for (OrderAttachmentInfoEntity entity : attachmentList) {
            entity.setUuid(UUID.randomUUID().toString());
            entity.setOrderId(orderId);
            //新增附件信息
            orderBasisDao.addOrderAttachment(entity);
        }
        for (AddDetailsProdictAllVO vo : eleAllVOList) {
            // 新增采购订单
            addDetailsProdictAll(vo, orderId);
            // 获取采购计划ID
            if (vo.getPurchasePlanId() != null && !vo.getPurchasePlanId().trim().isEmpty()) {
                purchasePlanIdList.add(vo.getPurchasePlanId().trim());
            }
        }
        //新增订单条款信息条款
        OrderProvisionVO provisionVo = new OrderProvisionVO();
        BeanUtils.copyProperties(orderVO, provisionVo);
        if (provisionVo.getProvisionContent() != null || provisionVo.getAccount() != null || provisionVo.getBank() != null || provisionVo.getPayee() != null) {
            orderProvisionService.addOrderProvision(provisionVo);
        }

        // 如果传入了采购计划ID，则更新采购计划ID对应的采购订单号
        purchasePlanService.updateOrderId(orderId, purchasePlanIdList);
        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result updateOrder(List<AddDetailsProdictAllVO> eleAllVOList, OrderBasisInfoVO orderVO) {
        //截取出订单基本板块信息
        String orderId = orderVO.getOrderId();
        OrderBasisInfoEntity basisEn = new OrderBasisInfoEntity();
        BeanUtils.copyProperties(orderVO, basisEn);
//        //先删除订单条款之前的信息
//        OrderProvisionEntity prEle = new OrderProvisionEntity();
//        prEle.setOrderId(orderId);
//        prEle.setDeleteStatus(1);
//        orderProvisionService.delOrderProvision(prEle);
        //修改订单条款信息条款
        OrderProvisionVO provisionVo = new OrderProvisionVO();
        BeanUtils.copyProperties(orderVO, provisionVo);
        //if (provisionVo.getProvisionContent() != null && provisionVo.getAccount() != null && provisionVo.getBank() != null && provisionVo.getPayee() != null) {
        //有的订单没有条款内容，却又其他三个参数就尴尬了
        if (provisionVo.getProvisionContent() != null) {
            orderProvisionService.updateOrderProvision(provisionVo);
        }
        //修改订单基础信息表
        orderBasisDao.updateBasisInfo(basisEn);
        List<OrderAttachmentInfoEntity> attachAll = orderVO.getOderAttachmentInfoEntityList();
        for (OrderAttachmentInfoEntity ele : attachAll) {
            orderBasisDao.delOrderAttachment(orderId);
            ele.setUuid(UUID.randomUUID().toString());
            ele.setOrderId(orderId);
            orderBasisDao.addOrderAttachment(ele);
        }
        List<String> idList = orderDetailsProductInfoDao.idListById(orderId);
        Map map = new HashMap();
        for (AddDetailsProdictAllVO vo : eleAllVOList) {
            String s = vo.getId();
            //判断如果产品订单ID不为空，说明是修改或者删除
            if (s != null) {
                orderDetailsProductInfoDao.delDeliveryById(s);
                orderDetailsProductInfoDao.delPackInfoById(s);

                //修改产品基本信息
                OrderDetailsProductInfoEntity ele = new OrderDetailsProductInfoEntity();
                BeanUtils.copyProperties(vo, ele);
                orderDetailsProductInfoDao.updateInfoById(ele);
                //截取集装箱信息
                ProducPackingInfoEntity packEle = vo.getProducPackingInfoEn();
                packEle.setId(s);
                //新增集装箱信息
                orderDetailsProductInfoDao.addPackInfo(packEle);
                //截取交易日期与数量集合数据
                List<ProductDeliveryInfoEntity> DelList = vo.getProductDeliveryInfoEnList();
                for (ProductDeliveryInfoEntity delEle : DelList) {
                    delEle.setId(s);
                    delEle.setUuid(UUID.randomUUID().toString());
                    //新增交易日期与数量集合数据
                    orderDetailsProductInfoDao.addDelivery(delEle);
                }
                map.put(s, "1");
            } else {  //新增
                addDetailsProdictAll(vo, orderId);
            }

        }
        for (String id : idList) {
            if (map.get(id) == null) {
                //删除产品基础信息
                orderDetailsProductInfoDao.delDeliveryById(id);
                orderDetailsProductInfoDao.delPackInfoById(id);
                orderDetailsProductInfoDao.delDeliveryById(id);
                orderDetailsProductInfoDao.delInfoById(id);
            }
        }
        return Result.success();
    }

    @Override
    public OrderBasisInfoVO findBasisInfoById(String orderId) {
        OrderBasisInfoEntity ele = orderBasisDao.findBasisInfoById(orderId);
        OrderBasisInfoVO vo = new OrderBasisInfoVO();
        if (ele == null) {
            return null;
        }
        BeanUtils.copyProperties(ele, vo);
        List supplierIds = new ArrayList();
        Map<String, String> supplierNameMap = new HashMap();
        supplierIds.add(ele.getSupplierId());
        try {
            supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        vo.setSupplierName(supplierNameMap.get(ele.getSupplierId()));
        List<OrderAttachmentInfoEntity> attList = orderBasisDao.findAttachmentInfo(orderId);
        vo.setOderAttachmentInfoEntityList(attList);
        //查询条款信息
        OrderProvisionVO orderProvision = orderProvisionService.findOrderProvision(orderId);
        if (orderProvision.getUuid() != null) {
            vo.setProvisionContent(orderProvision.getProvisionContent());
            vo.setBank(orderProvision.getBank());
            vo.setPayee(orderProvision.getPayee());
            vo.setAccount(orderProvision.getAccount());
        }
        return vo;
    }

    @Override
    public OrderBasisInfoVO findBasisInfoByIdNeet(String orderId) {
        OrderBasisInfoEntity ele = orderBasisDao.findBasisInfoById(orderId);
        OrderBasisInfoVO vo = new OrderBasisInfoVO();
        BeanUtils.copyProperties(ele, vo);
        List<String> supplierIds = new ArrayList<>();
        List<String> employeeIds = new ArrayList<>();


        supplierIds.add(ele.getSupplierId());
        employeeIds.add(ele.getEmployeeId());
        try {
            Map<String, String> employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
            vo.setEmployeeName(employeeNameMap.get(ele.getEmployeeId()));
        } catch (Exception e) {
            logger.error("加载采购员名称错误，置为空值", e);
            vo.setEmployeeName("");
        }
        try {
            Map<String, String> supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
            vo.setSupplierName(supplierNameMap.get(ele.getSupplierId()));
        } catch (Exception e) {
            logger.error("加载供应商名称错误，置为空值", e);
            vo.setSupplierName("");
        }

        List<OrderAttachmentInfoEntity> attList = orderBasisDao.findAttachmentInfo(orderId);
        vo.setOderAttachmentInfoEntityList(attList);
        return vo;
    }

    @Override
    public List<AddDetailsProdictAllVO> findDetailsList(String orderId) {
        List<AddDetailsProdictAllVO> detAllList = new ArrayList<>();
        //查询此订单里面的产品信息集合
        List<OrderDetailsProductInfoEntity> detEnList = orderDetailsProductInfoDao.findInfoById(orderId);
        List skuIds = new ArrayList();
        Map<String, String> productNameMap = new HashMap();
        for (OrderDetailsProductInfoEntity ele : detEnList) {
            AddDetailsProdictAllVO vo = new AddDetailsProdictAllVO();
            //为VO赋值产品信息
            BeanUtils.copyProperties(ele, vo);
            skuIds.add(ele.getSkuId());
            //为VO赋值此产品交货日期与数据信息
            List<ProductDeliveryInfoEntity> delEnList = orderDetailsProductInfoDao.findDeliveryInfo(ele.getId());
            vo.setProductDeliveryInfoEnList(delEnList);
            //为VO赋值此产品装箱信息
            ProducPackingInfoEntity producPackEntity = orderDetailsProductInfoDao.findPackInfo(ele.getId());
            vo.setProducPackingInfoEn(producPackEntity);
            if (vo.getDeleteStatus() == 1) {
                vo.setStatus(4);
            } else {
                vo.setStatus(toStatus(ele.getSkuId(), ele.getOrderId()));
            }
            detAllList.add(vo);
        }
        try {
            productNameMap = productHandleService.getProductCNNameMap(skuIds);
            for (AddDetailsProdictAllVO ele : detAllList) {
                ele.setSkuName(productNameMap.get(ele.getSkuId()));
            }
        } catch (Exception e) {

        }
        return detAllList;
    }

    @Override
    public List<AddDetailsProdictAllVO> findDetailsListNeet(String orderId) {
        List<AddDetailsProdictAllVO> detAllList = new ArrayList<>();
        List<OrderDetailsProductInfoEntity> detEnList = orderDetailsProductInfoDao.findInfoById(orderId);
        for (OrderDetailsProductInfoEntity ele : detEnList) {
            AddDetailsProdictAllVO vo = new AddDetailsProdictAllVO();
            BeanUtils.copyProperties(ele, vo);
            List<ProductDeliveryInfoEntity> delEnList = orderDetailsProductInfoDao.findDeliveryInfo(ele.getId());
            vo.setProductDeliveryInfoEnList(delEnList);
            ProducPackingInfoEntity producPackEntity = orderDetailsProductInfoDao.findPackInfo(ele.getId());
            vo.setProducPackingInfoEn(producPackEntity);
            detAllList.add(vo);
        }
        return detAllList;
    }

    @Override
    public Map findSupplier(String[] ids) {
        Map map = new HashMap();
        for (String skuId : ids) {
            OrderSkuSupplierEntity supplier = orderBasisDao.findSupplier(skuId);
            if (supplier != null) {
                map.put(supplier.getSkuId(), supplier.getSupplierId());
            }
        }
        return map;
    }

    @Override
    public void updateProductStatus(String id, int deleteStatus) {
        orderDetailsProductInfoDao.updateProductStatus(id, deleteStatus);
        String orderId = orderDetailsProductInfoDao.getOrderId(id);
        if (orderId != null) {
            Integer status = toOrderStatus(orderId);
            orderBasisDao.changeStatus(status, orderId);
        }
    }

    /**
     * 通过采购订单id查询详细信息
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderBasisInfoAllVO findInfoAllVoById(String orderId) {
        OrderBasisInfoAllVO orderBasisInfoAllVO = new OrderBasisInfoAllVO();
        orderBasisInfoAllVO.setOrderVO(purchcaseOrderBasisInfoService.findBasisInfoById(orderId));
        orderBasisInfoAllVO.setEleVOList(purchcaseOrderBasisInfoService.findDetailsList(orderId));
        return orderBasisInfoAllVO;
    }

    @Override
    public void updateRemark(RemarkVo remarkVo) {
        orderBasisDao.updateRemark(remarkVo);
    }

    @Override
    public List<ProductDeliveryInfoEntity> findDeliveryAll(String orderId) {
        return orderDetailsProductInfoDao.findDeliveryInfo(orderId);
    }

    @Override
    public ProducPackingInfoEntity findPackInfo(String orderId) {
        return orderDetailsProductInfoDao.findPackInfo(orderId);
    }

    @Override
    public Result updateStatus(String orderId) {
        //查询该采购订单是否被验货申请单引用
        Result applyResult = inspectionService.getInspectionApplyInfo(orderId);
        int applyInfoNum = (int) applyResult.getData();
        //查询该采购订单是否被产品检验单引用
        Result productResult = inspectionService.getInspectionProductInfo(orderId);
        int productInfoNum = (int) productResult.getData();
        //查询该采购订单是否被到货通知单引用
        int arrivalBasisInfoNum = orderBasisDao.getArrivalBasisInfo(orderId);
        //查询该采购订单是否被采购入库单引用
        int entryWarehouseInfoNum = orderBasisDao.getEntryWarehouseInfo(orderId);
        //查询该采购订单是否被采购拒收单引用
        int purchaseRejectionInfoNum = orderBasisDao.getPurchaseRejectionInfo(orderId);
        //查询该采购订单是否被采购退货单引用
        int purchaseReturnInfoNum = orderBasisDao.getPurchaseReturnInfo(orderId);

        if (applyInfoNum + productInfoNum + arrivalBasisInfoNum + entryWarehouseInfoNum + purchaseRejectionInfoNum + purchaseReturnInfoNum == 0) {
            orderBasisDao.deleteBasisInfo(orderId);
            return Result.success("逻辑删除成功");
        } else {
            return Result.failure(300, "该采购订单正被其他订单引用，无法删除", "");
        }
    }

    @Override
    public int findNum(String orderId) {
        return orderBasisDao.findNumById(orderId);
    }

    /**
     * 查询此订单下产品的SKU
     *
     * @param orderId
     * @return
     */
    public List<String> skuById(String orderId) {
        return orderDetailsProductInfoDao.skuById(orderId);
    }

    @Override
    public String findOrderId() {
        return orderBasisDao.findOrderId();
    }

    @Override
    public void updateStatus(String[] idList) {
        for (String id : idList) {
            orderBasisDao.deleteBasisInfo(id);
        }
    }

    @Override
    public List<OrderAttachmentInfoEntity> findAttachmentInfo(String orderId) {
        return orderBasisDao.findAttachmentInfo(orderId);
    }

    @Override
    public LinkedHashMap findBasisByIds(String[] ids) {
        LinkedHashMap map = new LinkedHashMap();
        List<OrderBasisInfoEntity> basisByIds = orderBasisDao.findBasisByIds(ids);
        toOrderBasis(basisByIds);
        map.put("orderBasisInfoList", basisByIds);
        return map;
    }

    @Override
    public void addOrderAttachment(OrderAttachmentInfoEntity ele) {
        orderBasisDao.addOrderAttachment(ele);
    }

    @Override
    public List<String> findAll() {
        return orderBasisDao.findAll();
    }

    /**
     * 新增一条订单产品相关所有信息
     *
     * @param vo
     * @param orderId
     */
    private void addDetailsProdictAll(AddDetailsProdictAllVO vo, String orderId) {
        String id = UUID.randomUUID().toString();
        //截取订单里面产品板块基本信息
        OrderDetailsProductInfoEntity detailsEle = new OrderDetailsProductInfoEntity();
        BeanUtils.copyProperties(vo, detailsEle);
        detailsEle.setId(id);
        detailsEle.setOrderId(orderId);
        detailsEle.setDeleteStatus(0);
        //新增订单里面产品板块基本信息
        orderDetailsProductInfoDao.addInfo(detailsEle);
        //截取集装箱信息
        ProducPackingInfoEntity packEle = vo.getProducPackingInfoEn();
        packEle.setId(id);
        //新增集装箱信息
        orderDetailsProductInfoDao.addPackInfo(packEle);
        //截取交易日期与数量集合数据
        List<ProductDeliveryInfoEntity> DelList = vo.getProductDeliveryInfoEnList();
        for (ProductDeliveryInfoEntity delEle : DelList) {
            delEle.setId(id);
            delEle.setUuid(UUID.randomUUID().toString());
            //新增交易日期与数量集合数据
            orderDetailsProductInfoDao.addDelivery(delEle);
        }
    }

    private List<OrderBasisInfoEntity> toOrderBasis(List<OrderBasisInfoEntity> list) {
        List supplierIds = new ArrayList();
        List employeeIds = new ArrayList();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();
        for (OrderBasisInfoEntity ele : list) {
            supplierIds.add(ele.getSupplierId());
            employeeIds.add(ele.getEmployeeId());
        }
        try {
            supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
            employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (OrderBasisInfoEntity ele : list) {
            ele.setSupplierName(supplierNameMap.get(ele.getSupplierId()));
            ele.setEmployeeName(employeeNameMap.get(ele.getEmployeeId()));
        }
        return list;
    }

    private List<OrderNeetVO> toName(List<OrderNeetVO> list) {
        List supplierIds = new ArrayList();
        List skuIds = new ArrayList();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> productHandleMap = new HashMap();
        for (OrderNeetVO e : list) {
            supplierIds.add(e.getSupplierId());
            skuIds.add(e.getSkuId());
        }
        try {
            supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
            productHandleMap = productHandleService.getProductCNNameMap(skuIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (OrderNeetVO vo : list) {
            vo.setSupplierName(supplierNameMap.get(vo.getSupplierId()));
            vo.setProductNameCN(productHandleMap.get(vo.getSkuId()));
        }
        return list;
    }

    /**
     * 如果关键字不是空，转换成模糊查询的ID列表
     *
     * @param keyword
     * @return
     */
    private String toSqlKeyword(String keyword) {
        if (keyword != null && !("".equals(keyword))) {
            try {
                List list = supplierService.getFindKeyDealted(keyword);
                if (list != null && list.size() > 0) {
                    keyword = toInString(list);
                    return keyword;
                }
            } catch (Exception e) {
            }
        }
        return null;
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
