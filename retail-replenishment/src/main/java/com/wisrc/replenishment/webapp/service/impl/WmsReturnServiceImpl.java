package com.wisrc.replenishment.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.TransferDao;
import com.wisrc.replenishment.webapp.dao.WaybillInfoDao;
import com.wisrc.replenishment.webapp.entity.TransferOrderPackInfoEntity;
import com.wisrc.replenishment.webapp.service.ProductService;
import com.wisrc.replenishment.webapp.service.WmsReturnService;
import com.wisrc.replenishment.webapp.utils.Time;
import com.wisrc.replenishment.webapp.utils.Toolbox;
import com.wisrc.replenishment.webapp.vo.wms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WmsReturnServiceImpl implements WmsReturnService {

    @Autowired
    private TransferDao transferDao;

    @Autowired
    Gson gson;

    @Autowired
    private ProductService productService;

    @Autowired
    private WaybillInfoDao waybillInfoDao;

    @Override
    public void transferPackInfoReturn(TransferOrderPackBasicVO transferOrderPackBasicVO) {

        List<TransferOrderPackProductVO> productListVO = transferOrderPackBasicVO.getSkuEntityList();

        for (TransferOrderPackProductVO productVO : productListVO) {
            List<TransferOrderPackDetailsVO> packInfoListVO = productVO.getPackTypeList();
            String commodityInfoCd = transferDao.findCommodityInfoCdByCdAndSku(transferOrderPackBasicVO.getTransferOrderId(), productVO.getSkuId());
            //逻辑删除产品下的装箱规格
            transferDao.deletePackInfoByCommodityId(commodityInfoCd);

            int packingNumber = 0;

            double length = 0;
            double width = 0;
            double height = 0;
            double Weight = 0;
            double Quantity = 0;

            for (TransferOrderPackDetailsVO packInfoVO : packInfoListVO) {
                TransferOrderPackInfoEntity packInfoEntity = new TransferOrderPackInfoEntity();
                packInfoEntity.setUuid(Toolbox.randomUUID());
                packInfoEntity.setCommodityInfoCd(commodityInfoCd);
                packInfoEntity.setOuterBoxSpecificationLen(packInfoVO.getOuterBoxSpecificationLen());
                packInfoEntity.setOuterBoxSpecificationWidth(packInfoVO.getOuterBoxSpecificationWidth());
                packInfoEntity.setOuterBoxSpecificationHeight(packInfoVO.getOuterBoxSpecificationHeight());
                packInfoEntity.setWeight(packInfoVO.getPackingWeight());
                packInfoEntity.setPackingQuantity(packInfoVO.getPackingQuantity());
                packInfoEntity.setNumberOfBoxes(packInfoVO.getNumberOfBox());
                packInfoEntity.setDeleteStatus(0);
                packInfoEntity.setPackQuantity(packInfoVO.getPackingQuantity() * packInfoVO.getNumberOfBox());
                packingNumber += packInfoVO.getPackingQuantity() * packInfoVO.getNumberOfBox();
                //没有是否标准箱字段！！！
                transferDao.insertTransferPacking(packInfoEntity);
                if (packInfoVO.getNumberOfBox() > 0) {
                    width = packInfoVO.getOuterBoxSpecificationWidth();
                    length = packInfoVO.getOuterBoxSpecificationLen();
                    height = packInfoVO.getOuterBoxSpecificationHeight();
                    Weight = packInfoVO.getPackingWeight();
                    Quantity = packInfoVO.getPackingQuantity();
                }
            }
            Map<String, Object> productInfo = new LinkedHashMap<>();
            productInfo.put("skuId", productVO.getSkuId());
            productInfo.put("fbaLength", length);
            productInfo.put("fbaWidth", width);
            productInfo.put("fbaHeight", height);
            productInfo.put("fbaWeight", Weight);
            productInfo.put("fbaQuantity", Quantity);
            //回写信息给产品档案
            productService.syncProductSpecifications(gson.toJson(productInfo), "matrixwms");
            //回写调拨单产品装箱量信息
            transferDao.updatePackingQuantityByCd(0, packingNumber, commodityInfoCd);
        }
        //改变调拨单状态为待选择渠道
        transferDao.updateStatusCdByCd(3, "matrixwms", transferOrderPackBasicVO.getTransferOrderId(), Time.getCurrentDateTime());
    }

    @Override
    public void transferOutReturn(TransferOutBasicVO transferOutBasicVO) {
        List<TransferOutProductVO> productListVO = transferOutBasicVO.getSkuList();

        for (TransferOutProductVO productVO : productListVO) {
            List<TransferOutPackVO> packInfoListVO = productVO.getPackTypeList();
            String commodityInfoCd = transferDao.findCommodityInfoCdByCdAndSku(transferOutBasicVO.getTransferOrderId(), productVO.getSkuId());
            //逻辑删除产品下的装箱规格
            transferDao.deletePackInfoByCommodityId(commodityInfoCd);

            int packingNumber = 0;
            int deliveryNumber = 0;

            for (TransferOutPackVO packInfoVO : packInfoListVO) {
                TransferOrderPackInfoEntity packInfoEntity = new TransferOrderPackInfoEntity();
                packInfoEntity.setUuid(Toolbox.randomUUID());
                packInfoEntity.setCommodityInfoCd(commodityInfoCd);
                packInfoEntity.setOuterBoxSpecificationLen(packInfoVO.getOuterBoxSpecificationLen());
                packInfoEntity.setOuterBoxSpecificationWidth(packInfoVO.getOuterBoxSpecificationWidth());
                packInfoEntity.setOuterBoxSpecificationHeight(packInfoVO.getOuterBoxSpecificationHeight());
                packInfoEntity.setWeight(packInfoVO.getPackingWeight());
                packInfoEntity.setPackingQuantity(packInfoVO.getPackingQuantity());
                packInfoEntity.setNumberOfBoxes(packInfoVO.getNumberOfBox());
                packInfoEntity.setDeleteStatus(0);

                deliveryNumber += packInfoVO.getDeliveryNumber();
                packingNumber += packInfoVO.getPackingQuantity() * packInfoVO.getNumberOfBox();
                packInfoEntity.setPackQuantity(packInfoVO.getPackingQuantity() * packInfoVO.getNumberOfBox());
                packInfoEntity.setDeliveryQuantity(packInfoVO.getDeliveryNumber());
                transferDao.insertTransferPacking(packInfoEntity);
            }
            //回写调拨单产品装箱量信息
            transferDao.updatePackingQuantityByCd(deliveryNumber, packingNumber, commodityInfoCd);
        }
        //改变调拨单状态为待签收
        transferDao.delivery(transferOutBasicVO.getTransferOrderId(), "matrixwms", Time.getCurrentDateTime());
        //改变调拨单关联的交运单的状态为待签收
        String waybillId = transferDao.getWayBillIdByCd(transferOutBasicVO.getTransferOrderId());
        waybillInfoDao.upWaybillStatus(waybillId, Time.getCurrentTimestamp(), "matrixwms", 2);
    }
}
