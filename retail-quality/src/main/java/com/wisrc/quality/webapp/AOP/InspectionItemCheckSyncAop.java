package com.wisrc.quality.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.quality.webapp.service.OutOrderService;
import com.wisrc.quality.webapp.service.WmsService;
import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.AOP.wmsvo.InspectionItemCheckDetailsVO;
import com.wisrc.quality.webapp.AOP.wmsvo.InspectionItemCheckVO;
import com.wisrc.quality.webapp.vo.productInspectionInfo.add.AddProductInspectionInfoVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Order(1)
public class InspectionItemCheckSyncAop {

    @Autowired
    private WmsService wmsService;
    @Autowired
    private OutOrderService outOrderService;
    @Autowired
    private Gson gson;

    /**
     * 文档编号： 005
     * 编号名称： 【采购管理】-【到货通知单】-【检验合格数量】
     *
     * @param pj
     * @return
     */
    @AfterReturning(value = "execution(public * com.wisrc.quality.webapp.service.impl.ProductInspectionInfoImplService.insert(..))", returning = "result")
    public void aopAdd(JoinPoint pj, Result result) {
        try {

            if (result.getCode() == 200) {
                InspectionItemCheckVO resultVo = new InspectionItemCheckVO();
                List<InspectionItemCheckDetailsVO> goodsList = new LinkedList<>();

                //得到切入方法的第一个参数
                Object[] args = pj.getArgs();
                AddProductInspectionInfoVO addProductInspectionInfoVO = (AddProductInspectionInfoVO) args[0];

                //写入到货通知单号和订单号
                String arrivalId = addProductInspectionInfoVO.getSourceDocumentCd();
                resultVo.setVoucherCode(arrivalId);
                resultVo.setPreDeliveryVocuherCode(addProductInspectionInfoVO.getPurchaseOrderId());

                //调外部接口得到到货通知单信息
                Result arrivalResult = outOrderService.getOrderById(arrivalId);
                if (arrivalResult.getCode() != 200) {
                    throw new RuntimeException("同步wms的时候调用外部订单接口服务错误：" + arrivalResult.getMsg());
                }
                //新建产品检验单的时候只有是仓库的时候才有到货通知单号，只有验货方式是仓库验货的时候才推送给wms

                if (arrivalResult.getCode() == 200) {

                    int flag = 0;
                    //如果是抽检且最终判定不为空
                    if (addProductInspectionInfoVO.getInspectionTypeCd() != null && addProductInspectionInfoVO.getInspectionTypeCd() == 1) {
                        //仓库检验的不会推过去
                        if (addProductInspectionInfoVO.getFinalDetermineCd() != null && addProductInspectionInfoVO.getFinalDetermineCd() != 2) {
                            flag = 1;
                        }
                        //如果是全检
                    } else {
                        flag = 1;
                    }

                    if (flag == 1) {
                        Map infoMap = (Map) arrivalResult.getData();
                        List detailList = (List) infoMap.get("inspectionProduct");
                        InspectionItemCheckDetailsVO detailsVO = new InspectionItemCheckDetailsVO();

                        //写入产品信息
                        for (int i = 0; i < detailList.size(); i++) {
                            Object one = detailList.get(i);
                            detailsVO.setLineNum(i + 1 + "");
                            if ((((Map) one).get("skuId")).equals(addProductInspectionInfoVO.getSkuId())) {
                                detailsVO.setGoodsName((String) ((Map) one).get("productName"));
                                detailsVO.setUnQcQuantity((Integer) ((Map) one).get("deliveryQuantity"));
                                break;
                            }
                        }
                        detailsVO.setGoodsCode(addProductInspectionInfoVO.getSkuId());
                        detailsVO.setQualifiedQuantity(addProductInspectionInfoVO.getQualifiedQuantity());
                        detailsVO.setUnQualifiedQuantity(addProductInspectionInfoVO.getUnqualifiedQuantity());
                        detailsVO.setTotalQuantity(addProductInspectionInfoVO.getActualInspectionQuantity());
                        goodsList.add(detailsVO);
                        resultVo.setGoodsList(goodsList);
                        Result wmsResult = wmsService.inspectionItemsCheckSync(gson.toJson(resultVo));

                        if (wmsResult.getCode() != 0) {
                            throw new Exception();
                        }
                    }
                }
            }
        } catch (Exception e) {
            result.setData(result.getMsg() + "," + e.getMessage());
        }
    }

    @AfterReturning(value = "execution(public * com.wisrc.quality.webapp.service.impl.ProductInspectionInfoImplService.update(..))", returning = "result")
    public void aopUpdate(JoinPoint pj, Result result) {
        try {
            if (result.getCode() == 200) {
                InspectionItemCheckVO resultVo = new InspectionItemCheckVO();
                List<InspectionItemCheckDetailsVO> goodsList = new LinkedList<>();

                //得到切入方法的第一个参数
                Object[] args = pj.getArgs();
                AddProductInspectionInfoVO addProductInspectionInfoVO = (AddProductInspectionInfoVO) args[1];

                //写入到货通知单号和订单号
                String arrivalId = addProductInspectionInfoVO.getSourceDocumentCd();
                resultVo.setVoucherCode(arrivalId);
                resultVo.setPreDeliveryVocuherCode(addProductInspectionInfoVO.getPurchaseOrderId());

                //调外部接口得到到货通知单信息
                Result arrivalResult = outOrderService.getOrderById(arrivalId);
                if (arrivalResult.getCode() != 200) {
                    throw new RuntimeException("同步wms的时候调用外部订单接口服务错误：" + arrivalResult.getMsg());
                }
                //新建产品检验单的时候只有是仓库的时候才有到货通知单号，只有验货方式是仓库验货的时候才推送给wms

                if (arrivalResult.getCode() == 200) {

                    if (addProductInspectionInfoVO.getFinalDetermineCd() != null && addProductInspectionInfoVO.getFinalDetermineCd() != 2) {
                        Map infoMap = (Map) arrivalResult.getData();
                        List detailList = (List) infoMap.get("inspectionProduct");
                        InspectionItemCheckDetailsVO detailsVO = new InspectionItemCheckDetailsVO();

                        //写入产品信息
                        for (int i = 0; i < detailList.size(); i++) {
                            Object one = detailList.get(i);
                            detailsVO.setLineNum(i + 1 + "");
                            if ((((Map) one).get("skuId")).equals(addProductInspectionInfoVO.getSkuId())) {
                                detailsVO.setGoodsName((String) ((Map) one).get("productName"));
                                detailsVO.setUnQcQuantity((Integer) ((Map) one).get("deliveryQuantity"));
                                break;
                            }
                        }
                        detailsVO.setGoodsCode(addProductInspectionInfoVO.getSkuId());
                        detailsVO.setQualifiedQuantity(addProductInspectionInfoVO.getQualifiedQuantity());
                        detailsVO.setUnQualifiedQuantity(addProductInspectionInfoVO.getUnqualifiedQuantity());
                        detailsVO.setTotalQuantity(addProductInspectionInfoVO.getActualInspectionQuantity());
                        goodsList.add(detailsVO);
                        resultVo.setGoodsList(goodsList);
                        Result wmsResult = wmsService.inspectionItemsCheckSync(gson.toJson(resultVo));
                        if (wmsResult.getCode() != 0) {
                            throw new Exception(wmsResult.getMsg());
                        }
                    }
                }
            }
        } catch (Exception e) {
            result.setMsg(result.getMsg() + "," + e.getMessage());
        }
    }
}
