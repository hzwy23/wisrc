package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.entity.OrderAttachmentInfoEntity;
import com.wisrc.purchase.webapp.entity.OrderBasisInfoEntity;
import com.wisrc.purchase.webapp.entity.ProducPackingInfoEntity;
import com.wisrc.purchase.webapp.entity.ProductDeliveryInfoEntity;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.AddDetailsProdictAllVO;
import com.wisrc.purchase.webapp.vo.OrderBasisInfoAllVO;
import com.wisrc.purchase.webapp.vo.OrderBasisInfoVO;
import com.wisrc.purchase.webapp.vo.RemarkVo;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface PurchcaseOrderBasisInfoService {
    /**
     * 通过条件查询采购订单基本信息
     *
     * @param orderId           采购订单号
     * @param employeeId        采购员
     * @param deliveryTypeCd    交货状态
     * @param keyword           关键词
     * @param tiicketOpenCd     开票
     * @param customsTypeCd     报关
     * @param billDateBegin     订单日期开始时间
     * @param billDateEnd       订单日期结束时间
     * @param deliveryTimeBegin 交货日期结束时间
     * @param deliveryTimeEnd   交货日期结束时间
     * @return
     */
    LinkedHashMap findBasisInfo(int startPage, int pageSize, String orderId, String employeeId, int deliveryTypeCd, String keyword, int tiicketOpenCd, int customsTypeCd, Date billDateBegin, Date billDateEnd, Date deliveryTimeBegin, Date deliveryTimeEnd, String supplierId);

    /**
     * 通过条件查询采购订单基本信息
     *
     * @param orderId           采购订单号
     * @param employeeId        采购员
     * @param deliveryTypeCd    交货状态
     * @param keyword           关键词
     * @param tiicketOpenCd     开票
     * @param customsTypeCd     报关
     * @param billDateBegin     订单日期开始时间
     * @param billDateEnd       订单日期结束时间
     * @param deliveryTimeBegin 交货日期结束时间
     * @param deliveryTimeEnd   交货日期结束时间
     * @return
     */
    LinkedHashMap findBasisInfo(String orderId, String employeeId, int deliveryTypeCd, String keyword, int tiicketOpenCd, int customsTypeCd, Date billDateBegin, Date billDateEnd, Date deliveryTimeBegin, Date deliveryTimeEnd, String supplierId);

    /**
     * 查询全部采购订单基本信息
     * order_status 订单状态 0表示正常运行  1表示虚拟删除
     */
    List<OrderBasisInfoEntity> findBasisInfoAll();

    /**
     * 查询分页采购订单基本信息
     * order_status 订单状态 0表示正常运行  1表示虚拟删除
     */
    LinkedHashMap findBasisInfoAll(int startPage, int pageSize);

    /**
     * 新增采购订单信息
     *
     * @param eleAllVOList 采购订单产品信息集合
     * @param orderVO      采购订单基本信息
     */
    Result addOrderInfo(List<AddDetailsProdictAllVO> eleAllVOList, OrderBasisInfoVO orderVO);

    /**
     * 修改采购订单信息
     *
     * @param eleAllVOList
     * @param orderVO
     */
    Result updateOrder(List<AddDetailsProdictAllVO> eleAllVOList, OrderBasisInfoVO orderVO);

    /**
     * 查询采购订单基本信息
     *
     * @param orderId
     * @return
     */
    OrderBasisInfoVO findBasisInfoById(String orderId);

    OrderBasisInfoVO findBasisInfoByIdNeet(String orderId);

    /**
     * 查询采购订单产品基本信息集合
     *
     * @param orderId
     * @return
     */
    List<AddDetailsProdictAllVO> findDetailsList(String orderId);

    /**
     * 查询采购订单产品交货日期与数量集合
     *
     * @param orderId
     * @return
     */
    List<ProductDeliveryInfoEntity> findDeliveryAll(String orderId);

    /**
     * 查询集装箱信息
     *
     * @param orderId
     * @return
     */
    ProducPackingInfoEntity findPackInfo(String orderId);

    /**
     * 验证采集订单ID是否已经存在
     *
     * @param orderId
     * @return
     */
    int findNum(String orderId);

    /**
     * 虚拟删除订单
     *
     * @param orderId
     * @return
     */
    Result updateStatus(String orderId);

    /**
     * 查询此订单下产品的SKU
     *
     * @param orderId
     * @return
     */
    List<String> skuById(String orderId);

    /**
     * 查询已有最大订单Id
     */
    String findOrderId();

    /**
     * 虚拟删除订单(ID集合)
     *
     * @param idList
     * @return
     */
    void updateStatus(String[] idList);

    /**
     * 通过订单ID查询此订单相关附件
     *
     * @param orderId
     * @return
     */
    List<OrderAttachmentInfoEntity> findAttachmentInfo(String orderId);

    /**
     * 通过id数组查询采购订单基本信息
     */
    LinkedHashMap findBasisByIds(final String[] ids);

    /**
     * 新增订单附件信息
     */
    void addOrderAttachment(OrderAttachmentInfoEntity ele);

    /**
     * 查询所有采购订单id列表
     */
    List<String> findAll();

    /**
     * 通过条件查询采购订单产品信息列表（外部需求）1
     *
     * @param orderId
     * @param supplierName
     * @param skuId
     * @param productNameCN
     * @return
     */

    LinkedHashMap findBasisNeet(int startPage, int pageSize, String orderId, String supplierName, String skuId, String productNameCN);

    LinkedHashMap findBasisNeet(int startPage, int pageSize, String keyWords, String skuId, String orderId);

    LinkedHashMap findBasisNeet(String orderId, String supplierName, String skuId, String productNameCN);

    LinkedHashMap findBasisNeet(String keyWords, String skuId, String orderId);

    List<AddDetailsProdictAllVO> findDetailsListNeet(String orderId);

    /**
     * 根据库存sku批量查询最近一次采购订单的供应商
     */
    Map findSupplier(String[] ids);

    /**
     * 中止产品信息
     */
    void updateProductStatus(String id, int deleteStatus);

    /**
     * 通过采购订单id查询详细信息
     *
     * @param orderId
     * @return
     */
    OrderBasisInfoAllVO findInfoAllVoById(String orderId);


    void updateRemark(RemarkVo remarkVo);
}
