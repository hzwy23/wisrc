package com.wisrc.warehouse.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.warehouse.webapp.service.externalService.WmsService;
import com.wisrc.warehouse.webapp.vo.AddOutWarehouseBillVO;
import com.wisrc.warehouse.webapp.vo.wmsvo.handmadeOutWarehouseBill.OutWarehouseBillDetailsVO;
import com.wisrc.warehouse.webapp.vo.wmsvo.handmadeOutWarehouseBill.OutWarehouseBillVO;
import com.wisrc.warehouse.webapp.entity.HandmadeOutWarehouseBillEntity;
import com.wisrc.warehouse.webapp.entity.OutWarehouseListEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Aspect
@Component
public class OutWarehouseBillSyncAOP {

    @Autowired
    private WmsService wmsService;

    @Autowired
    private Gson gson;

    @AfterReturning(value = "execution(public * com.wisrc.warehouse.webapp.service.HandmadeOutWarehouseBillService.add(..))", returning = "result")
    public void addOutWarehouseBillSyncAOP(JoinPoint jp, Result result) {
        try {
            if (result.getCode() == 200) {
                Object[] args = jp.getArgs();
                AddOutWarehouseBillVO argsVO = (AddOutWarehouseBillVO) args[0];

                OutWarehouseBillVO resultVO = new OutWarehouseBillVO();
                HandmadeOutWarehouseBillEntity entity = (HandmadeOutWarehouseBillEntity) result.getData();

                resultVO.setVoucherCode(entity.getOutBillId());
                resultVO.setVoucherType("SGC");
                resultVO.setSectionCode(entity.getSubWarehouseId());
                resultVO.setCreateTime(entity.getCreateTime());
                resultVO.setRemark(argsVO.getRemarkEntity().getRemark());

                List<OutWarehouseBillDetailsVO> goodsList = new LinkedList<>();
                List<OutWarehouseListEntity> detailsList = argsVO.getList();

                int i = 1;
                for (OutWarehouseListEntity one : detailsList) {
                    OutWarehouseBillDetailsVO vo = new OutWarehouseBillDetailsVO();
                    vo.setLineNum(i + "");
                    i++;
                    vo.setGoodsCode(one.getSkuId());
                    vo.setFnCode(one.getFnSkuId());
                    vo.setGoodsName(one.getSkuName());
                    vo.setPackageQuantity(0 + "");
                    vo.setUnitQuantity(one.getOutWarehouseNum() + "");
                    vo.setTotalQuantity(one.getOutWarehouseNum() + "");
                    goodsList.add(vo);
                }
                resultVO.setGoodsList(goodsList);
                Result result1 = wmsService.addOutWarehouseBillSync(gson.toJson(resultVO));
                if (result1.getCode() != 0) {
                    throw new RuntimeException(result1.getMsg());
                }
            }
        } catch (Exception e) {
            result.setMsg("同步错误");
            result.setData(e.getMessage());
        }
    }
}
