package com.wisrc.warehouse.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.warehouse.webapp.service.externalService.ExternalProductService;
import com.wisrc.warehouse.webapp.service.externalService.WmsService;
import com.wisrc.warehouse.webapp.vo.AddEnterWarehouseBillVO;
import com.wisrc.warehouse.webapp.vo.syncVO.ArrivalNoticeBillSyncVO;
import com.wisrc.warehouse.webapp.vo.syncVO.GoodsInfoVO;
import com.wisrc.warehouse.webapp.vo.wmsvo.handmadeEnterWarehouseBill.EnterWarehouseBillDetailsVO;
import com.wisrc.warehouse.webapp.vo.wmsvo.handmadeEnterWarehouseBill.EnterWarehouseBillVO;
import com.wisrc.warehouse.webapp.dao.HandmadeEnterWarehouseBillDao;
import com.wisrc.warehouse.webapp.entity.EnterWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.HandmadeEnterWarehouseBillEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class EnterWarehouseBillSyncAOP {

    @Autowired
    private WmsService wmsService;
    @Autowired
    private HandmadeEnterWarehouseBillDao handmadeEnterWarehouseBillDao;
    @Autowired
    private ExternalProductService productService;

    @AfterReturning(value = "execution(public * com.wisrc.warehouse.webapp.service.HandmadeEnterWarehouseBillService.add(..))", returning = "result")
    public void enterWarehouseBillAop(JoinPoint jp, Result result) {
        try {
            if (result.getCode() == 200) {
                Gson gson = new Gson();
                Object[] args = jp.getArgs();
                AddEnterWarehouseBillVO argsVO = (AddEnterWarehouseBillVO) args[0];
                Map<String, String> skuIdToName = new HashMap<>();
                HandmadeEnterWarehouseBillEntity billEntity = (HandmadeEnterWarehouseBillEntity) result.getData();
                if (billEntity.getSubWarehouseId().startsWith("A")) {
                    //入本地库,调拨wms到货通知单接口
                    ArrivalNoticeBillSyncVO arrivalNoticeBillSyncVO = new ArrivalNoticeBillSyncVO();
                    arrivalNoticeBillSyncVO.setVoucherCode(billEntity.getEnterBillId());
                    arrivalNoticeBillSyncVO.setVoucherType("SGR");
                    arrivalNoticeBillSyncVO.setCreateTime(billEntity.getCreateTime());
                    arrivalNoticeBillSyncVO.setSectionCode(billEntity.getCreateUser());
                    arrivalNoticeBillSyncVO.setPreDeliveryVocuherCode(billEntity.getEnterBillId());
                    arrivalNoticeBillSyncVO.setSectionCode(billEntity.getSubWarehouseId());
                    arrivalNoticeBillSyncVO.setRemark(argsVO.getRemarkEntity().getRemark());
                    List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
                    int lineNum = 1;
                    for (EnterWarehouseListEntity enterWarehouseListEntity : argsVO.getList()) {
                        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                        goodsInfoVO.setLineNum(lineNum);
                        goodsInfoVO.setGoodsCode(enterWarehouseListEntity.getSkuId());
                        Result skuResult = productService.getSkuById(enterWarehouseListEntity.getSkuId());
                        Map skuMap = (Map) skuResult.getData();
                        goodsInfoVO.setGoodsName((String) skuMap.get("skuNameZh"));
                        goodsInfoVO.setUnitQuantity(enterWarehouseListEntity.getEnterWarehouseNum());
                        goodsInfoVO.setPackageQuantity(0);
                        goodsInfoVO.setTotalQuantity(enterWarehouseListEntity.getEnterWarehouseNum());
                        goodsInfoVOList.add(goodsInfoVO);
                        lineNum++;
                    }
                    arrivalNoticeBillSyncVO.setGoodsList(goodsInfoVOList);
                    Result result1 = wmsService.handmadeBillSync(gson.toJson(arrivalNoticeBillSyncVO));
                    if (result1.getCode() != 0) {
                        throw new RuntimeException(result1.getMsg());
                    }
                } else {
                    //入虚拟、海外库
                    EnterWarehouseBillVO resultVO = new EnterWarehouseBillVO();
                    resultVO.setVoucherCode(billEntity.getEnterBillId());
                    resultVO.setVoucherType("SGR");
                    resultVO.setSectionCode(billEntity.getSubWarehouseId());
                    resultVO.setCreateTime(billEntity.getCreateTime());
                    resultVO.setRemark(argsVO.getRemarkEntity().getRemark());

                    List<EnterWarehouseBillDetailsVO> goodsList = new LinkedList<>();
                    List<EnterWarehouseListEntity> entityList = argsVO.getList();
                    int i = 1;
                    for (EnterWarehouseListEntity one : entityList) {
                        EnterWarehouseBillDetailsVO detailsVO = new EnterWarehouseBillDetailsVO();
                        one.setActualEnterWarehouseNum(one.getEnterWarehouseNum());
                        detailsVO.setLineNum(i + "");
                        i++;
                        detailsVO.setGoodsCode(one.getSkuId());
                        detailsVO.setFnCode(one.getFnSkuId());
                        detailsVO.setGoodsName(one.getSkuName());
                        detailsVO.setUnitQuantity(one.getEnterWarehouseNum() + "");
                        detailsVO.setPackageQuantity(0 + "");
                        detailsVO.setTotalQuantity(one.getEnterWarehouseNum() + "");
                        goodsList.add(detailsVO);
                    }
                    resultVO.setGoodsList(goodsList);
                    System.out.println(resultVO);
                    Result result1 = wmsService.addEnterWarehouseBillSync(gson.toJson(resultVO));
                    System.out.println(result1);
                    if (result1.getCode() != 0) {
                        throw new RuntimeException(result1.getMsg());
                    } else {
                        for (EnterWarehouseListEntity enterWarehouseListEntity : entityList) {
                            handmadeEnterWarehouseBillDao.updateDeliveryNum(billEntity.getEnterBillId(), enterWarehouseListEntity.getSkuId(), enterWarehouseListEntity.getActualEnterWarehouseNum());
                        }
                        handmadeEnterWarehouseBillDao.updateStatus(billEntity.getEnterBillId(), 2);
                    }
                }
            }

        } catch (Exception e) {
            result.setMsg("同步错误:");
            result.setData(e.getMessage());
        }
    }
}
