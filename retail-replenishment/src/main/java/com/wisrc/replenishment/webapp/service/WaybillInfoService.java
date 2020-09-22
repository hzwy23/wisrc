package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.ReplenishShippingDataListVO;
import com.wisrc.replenishment.webapp.vo.waybill.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface WaybillInfoService {


    /**
     * 根据条件查询物流跟踪单信息集合(优化方案)分页
     *
     * @param pageNum
     * @param pageSize
     * @param waybillOrderDateBegin
     * @param waybillOrderDateEnd
     * @param shipmentId
     * @param customsCd
     * @param warehouseId
     * @param employeeId
     * @param logisticsTypeCd
     * @param Keyword
     * @return
     */
    Result findInfoFine(String pageNum,
                        String pageSize,
                        Date waybillOrderDateBegin,
                        Date waybillOrderDateEnd,
                        String shipmentId,
                        Integer customsCd,
                        String warehouseId,
                        String employeeId,
                        Integer logisticsTypeCd,
                        String Keyword,
                        String isLackLogistics,
                        String isLackShipment,
                        String isLackCustomsDeclare,
                        String isLackCustomsClearance,
                        String waybillId,
                        String batchNumber,
                        String fbaReplenishmentId,
                        String mskuId,
                        String productName);

    /**
     * 通过运单号查询基本信息
     *
     * @param waybillId
     * @return
     */
    WaybillInfoVO findInfoById(String waybillId);

    /**
     * 根据跟踪单查询详细信息
     *
     * @param waybillId
     * @return
     */
    WaybillDetailsVO findDetailsById(String waybillId);

    /**
     * 查询数据库跟踪号最大的Id
     */
    String findWaybillId();

    /**
     * 新增物流跟踪单基本信息
     */
    Result addWaybillInfo(AddWaybillVO vo, String user);

    /**
     * 逻辑删除物流跟踪单
     *
     * @param waybillId
     */
    void deleteWaybill(String waybillId, String user);

    /**
     * 完善更新物流信息
     *
     * @param vo
     */
    void updateWaybill(PerfectWaybillInfoVO vo);

    /**
     * 新增标记异常信息
     *
     * @param vo
     */
    void addException(WaybillExceptionVO vo, String userId);

    /**
     * 查询运单下所有商品详情信息
     *
     * @param waybillId
     * @return
     */
    Map findMskuVO(String waybillId);

    /**
     * 确定验收
     */
    Result confirmAcceptance(WaybillAcceptanceVO vo, String userId);

    /**
     * 确定发货
     */
    Result confirmShipments(List<ReplenishShippingDataListVO> list, String waybillId, String userId);

    /**
     * 获取标记异常信息
     */
    Result getException(String waybillId);

    void updateRemark(String waybillId, String remark);

    /**
     * 根据条件查询物流跟踪单信息集合(优化方案)分页
     */
    void waybillExcel(HttpServletResponse response,
                      HttpServletRequest request,
                      Integer pageNum,
                      Integer pageSize,
                      Date waybillOrderDateBegin,
                      Date waybillOrderDateEnd,
                      String shipmentId,
                      Integer customsCd,
                      String warehouseId,
                      String employeeId,
                      Integer logisticsTypeCd,
                      String Keyword,
                      String isLackLogistics,
                      String isLackShipment,
                      String isLackCustomsDeclare,
                      String isLackCustomsClearance,
                      String waybillId,
                      String batchNumber,
                      String fbaReplenishmentId,
                      String mskuId,
                      String productName);
}
