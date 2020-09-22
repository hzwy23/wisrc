package com.wisrc.order.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.order.webapp.dao.*;
import com.wisrc.order.webapp.dto.code.CodeTemplateConfEntity;
import com.wisrc.order.webapp.dto.code.UploadFileDto;
import com.wisrc.order.webapp.dto.msku.MskuInfoDTO;
import com.wisrc.order.webapp.dto.saleNvoice.*;
import com.wisrc.order.webapp.entity.*;
import com.wisrc.order.webapp.query.GetCountByLogisticsIdQuery;
import com.wisrc.order.webapp.query.NvoiceOrderPageQuery;
import com.wisrc.order.webapp.service.MskuService;
import com.wisrc.order.webapp.service.NvoiceOrderService;
import com.wisrc.order.webapp.service.OrderCodeService;
import com.wisrc.order.webapp.service.ShipmentService;
import com.wisrc.order.webapp.service.externalService.WarehouseService;
import com.wisrc.order.webapp.utils.*;
import com.wisrc.order.webapp.vo.msku.GetByMskuIdAndNameVo;
import com.wisrc.order.webapp.vo.msku.GetMskuListByIdVo;
import com.wisrc.order.webapp.vo.saleNvoice.FreightExcelVo;
import com.wisrc.order.webapp.vo.saleNvoice.SaleNvoicePageVo;
import com.wisrc.order.webapp.vo.saleNvoice.SaleNvoiceSaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NvoiceOrderServiceImpl implements NvoiceOrderService {
    SimpleDateFormat sdfPage = new SimpleDateFormat("yyyy/M/dd HH:mm");
    @Autowired
    private OrderNvoiceInfoDao saleNvoiceInfoDao;
    @Autowired
    private OrderInvoiceStatusAttrDao saleInvoiceStatusAttrDao;
    @Autowired
    private OrderCommodityInfoDao saleOrderCommodityInfoDao;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private OrderLogisticsInfoDao saleLogisticsInfoDao;
    @Autowired
    private OrderNvoiceRelationDao saleNvoiceOrderInfoDao;
    @Autowired
    private OrderCodeService orderCodeService;
    @Autowired
    private WarehouseService warehouseService;

    @Override
    public Result saleNvoicePage(SaleNvoicePageVo saleNvoicePageVo) {
        NvoiceOrderPageDto pageResult = new NvoiceOrderPageDto();
        List<NvoiceOrderDto> nvoiceOrders = new ArrayList<>();
        List shipmentIds = new ArrayList();
        Map<String, String> channelNameMap = new HashMap<>();

        NvoiceOrderPageQuery svoiceOrderPageQuery = new NvoiceOrderPageQuery();
        BeanUtils.copyProperties(saleNvoicePageVo, svoiceOrderPageQuery);

        if (saleNvoicePageVo.getMskuId() != null || saleNvoicePageVo.getMskuName() != null) {
            try {
                GetByMskuIdAndNameVo getByMskuIdAndNameVo = new GetByMskuIdAndNameVo();
                BeanUtils.copyProperties(saleNvoicePageVo, getByMskuIdAndNameVo);
                List<String> mskuIds = mskuService.getIdByMskuIdAndName(getByMskuIdAndNameVo);
                if (mskuIds == null || mskuIds.size() == 0) {
                    pageResult.setNvoiceOrders(nvoiceOrders);
                    return Result.success(pageResult);
                }
                svoiceOrderPageQuery.setMskuIds(mskuIds);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        List<NvoiceOrderEntity> saleNvoiceResult;
        try {
            if (saleNvoicePageVo.getPageNum() != null && saleNvoicePageVo.getPageSize() != null)
                PageHelper.startPage(saleNvoicePageVo.getPageNum(), saleNvoicePageVo.getPageSize());
            saleNvoiceResult = saleNvoiceInfoDao.saleNvoicePage(svoiceOrderPageQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (NvoiceOrderEntity getNvoiceOrder : saleNvoiceResult) {
            shipmentIds.add(getNvoiceOrder.getOfferId());
        }

        if (shipmentIds.size() > 0) {
            try {
                channelNameMap = shipmentService.getChannelName(shipmentIds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (NvoiceOrderEntity getNvoiceOrder : saleNvoiceResult) {
            NvoiceOrderDto nvoiceOrderDto = new NvoiceOrderDto();
            BeanUtils.copyProperties(getNvoiceOrder, nvoiceOrderDto);
            nvoiceOrderDto.setCreateTime(sdfPage.format(getNvoiceOrder.getCreateTime()));
            if (getNvoiceOrder.getLogisticsCost() != null)
                nvoiceOrderDto.setFreight(getNvoiceOrder.getLogisticsCost().toString());
            nvoiceOrderDto.setChannelName(channelNameMap.get(getNvoiceOrder.getOfferId()));

            nvoiceOrders.add(nvoiceOrderDto);
        }

        pageResult.setNvoiceOrders(nvoiceOrders);
        PageInfo pageInfo = new PageInfo(nvoiceOrders);
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPages(pageInfo.getPages());
        return Result.success(pageResult);
    }

    @Override
    public Result invoiceStatus() {
        List selector = new ArrayList();
        List<OrderInvoiceStatusAttrEntity> invoiceStatusResult;

        try {
            invoiceStatusResult = saleInvoiceStatusAttrDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (OrderInvoiceStatusAttrEntity getInvoiceStatus : invoiceStatusResult) {
            selector.add(ServiceUtils.idAndName(getInvoiceStatus.getStatusCd(), getInvoiceStatus.getStatusDesc()));
        }

        return Result.success(selector);
    }

    @Override
    public Result getSaleNvoice(String invoiceNumber) {
        NvoiceOrderInfoDto orderInfo = new NvoiceOrderInfoDto();
        List<OrderCommodityInfo> saleOrderCommodityResult;
        List<String> commonidyIds = new ArrayList();
        Map<String, MskuInfoDTO> msku = new HashMap<>();
        List offerIds = new ArrayList();
        Map<String, String> channelNameMap = new HashMap();

        try {
            GetNvoiceOrderEntity nvoiceOrder = saleNvoiceInfoDao.getSaleNvoice(invoiceNumber);
            BeanUtils.copyProperties(nvoiceOrder, orderInfo);
            orderInfo.setFreight(ServiceUtils.toString(nvoiceOrder.getLogisticsCost()));

            offerIds.add(nvoiceOrder.getOfferId());
            if (offerIds.size() > 0) {
                try {
                    channelNameMap = shipmentService.getChannelName(offerIds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            orderInfo.setChannelName(channelNameMap.get(nvoiceOrder.getOfferId()));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        try {
            int commodityCount = 0;

            saleOrderCommodityResult = saleOrderCommodityInfoDao.getOrderCommodityByOrderId(orderInfo.getOrderId());
            for (OrderCommodityInfo saleOrderCommodityInfoEntity : saleOrderCommodityResult) {
                commodityCount += saleOrderCommodityInfoEntity.getQuantity();
                commonidyIds.add(saleOrderCommodityInfoEntity.getCommodityId());
            }

            orderInfo.setCommodityKind(saleOrderCommodityResult.size());
            orderInfo.setCommodityNum(commodityCount);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        if (commonidyIds.size() > 0) {
            try {
                GetMskuListByIdVo getMskuListByIdVo = new GetMskuListByIdVo();
                getMskuListByIdVo.setIds(commonidyIds);
                msku = mskuService.getMskuInfo(getMskuListByIdVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<OrderCommodityDto> saleOrderCommodities = new ArrayList();
        for (OrderCommodityInfo saleOrderCommodityInfoEntity : saleOrderCommodityResult) {
            OrderCommodityDto orderCommodity = new OrderCommodityDto();
            BeanUtils.copyProperties(saleOrderCommodityInfoEntity, orderCommodity);
            orderCommodity.setWeight(ServiceUtils.toString(saleOrderCommodityInfoEntity.getWeight()));
            orderCommodity.setQuantity(saleOrderCommodityInfoEntity.getQuantity());
            if (msku.get(saleOrderCommodityInfoEntity.getCommodityId()) != null) {
                MskuInfoDTO mskuInfo = msku.get(saleOrderCommodityInfoEntity.getCommodityId());
                BeanUtils.copyProperties(mskuInfo, orderCommodity);
            }

            saleOrderCommodities.add(orderCommodity);
        }
        orderInfo.setOrderCommodities(saleOrderCommodities);

        return Result.success(orderInfo);
    }

    @Override
    @Transactional(transactionManager = "retailOrderTransactionManager", rollbackFor = Exception.class)
    public Result saveSaleNvoice(SaleNvoiceSaveVo saleNvoiceSaveVo, String userId) {
        String newId = getNewId();
        if (newId == null) {
            return new Result(400, "数据量已超出设计上限，无法继续添加", null);
        }

        OrderNvoiceInfo saleNvoiceInfoEntity = new OrderNvoiceInfo();
        BeanUtils.copyProperties(saleNvoiceSaveVo, saleNvoiceInfoEntity);
        saleNvoiceInfoEntity.setInvoiceNumber(newId);
        saleNvoiceInfoEntity.setStatusCd(saleNvoiceSaveVo.getStatusCd());
        saleNvoiceInfoEntity.setCreateTime(Time.getCurrentDateTime());
        saleNvoiceInfoEntity.setCreateUser(userId);
        saleNvoiceInfoEntity.setTotalWeight(Double.parseDouble(saleNvoiceSaveVo.getTotalWeight()));
        try {
            saleNvoiceInfoDao.saveSaleNvoice(saleNvoiceInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        OrderNvoiceRelation saleNvoiceOrderInfoEntity = new OrderNvoiceRelation();
        BeanUtils.copyProperties(saleNvoiceSaveVo, saleNvoiceOrderInfoEntity);
        saleNvoiceOrderInfoEntity.setCreateTime(Time.getCurrentDateTime());
        saleNvoiceOrderInfoEntity.setCreateUser(userId);
        saleNvoiceOrderInfoEntity.setInvoiceNumber(newId);
        saleNvoiceOrderInfoEntity.setUuid(Toolbox.randomUUID());
        try {
            saleNvoiceOrderInfoDao.saveSaleNvoiceOrderInfo(saleNvoiceOrderInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    public Result excelModel() {
        try {
            CodeTemplateConfEntity codeTemplate = orderCodeService.getCodeTemplateConfById();
            UploadFileDto uploadFileDto = new UploadFileDto();
            uploadFileDto.setUuid(codeTemplate.getAddr());
            uploadFileDto.setObsName(codeTemplate.getObsName());
            return Result.success(uploadFileDto);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    @Transactional(transactionManager = "retailOrderTransactionManager", rollbackFor = Exception.class)
    public Result excelHandle(MultipartFile excel) {
        Map<String, List<String>> logisticIdMap = new HashMap();
        List logisticsIds = new ArrayList();
        List<CountSaleLogisticsEntity> saleLogisticsResult;
        List editLogistics = new ArrayList();
        List findOffer = new ArrayList();
        List<OrderLogisticsInfo> editEntity = new ArrayList<>();
        Map<String, Map> offerRelationship = new HashMap<>();
        List<Map<Integer, Object>> excelData;
        List<FreightExcelVo> freightExcel = new ArrayList<>();

        try {
            excelData = ExcelTools.excelImport(excel);
            for (Map<Integer, Object> freightData : excelData) {
                FreightExcelVo freightExcelVo = new FreightExcelVo();
                freightExcelVo.setChannelName(ServiceUtils.toString(freightData.get(1)));
                freightExcelVo.setLogisticsId(ServiceUtils.toString(freightData.get(2)));
                freightExcelVo.setFreight(ServiceUtils.toString(freightData.get(3)));

                freightExcel.add(freightExcelVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "上传文件格式错误", null);
        }

        // 获取物流单号对应物流商
        for (FreightExcelVo freightExcelVo : freightExcel) {
            logisticsIds.add(freightExcelVo.getLogisticsId());
        }

        try {
            GetCountByLogisticsIdQuery getCountByLogisticsIdQuery = new GetCountByLogisticsIdQuery();
            getCountByLogisticsIdQuery.setLogisticsIds(logisticsIds);
            List<CountSaleLogisticsEntity> countList = saleLogisticsInfoDao.getCountByLogisticsId(getCountByLogisticsIdQuery);
            Map<String, Integer> countMap = new HashMap();
            for (CountSaleLogisticsEntity count : countList) {
                countMap.put(count.getLogisticsId(), count.getCount());
            }
            saleLogisticsResult = saleLogisticsInfoDao.getDistinctOffer(getCountByLogisticsIdQuery);
            for (CountSaleLogisticsEntity saleLogistics : saleLogisticsResult) {
                saleLogistics.setCount(countMap.get(saleLogistics.getLogisticsId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // 如果物流单号对应数据只有一条则直接新增，否则匹配物流商信息
        for (CountSaleLogisticsEntity countSaleLogistics : saleLogisticsResult) {
            if (countSaleLogistics.getCount() == 1) {
                editLogistics.add(countSaleLogistics.getLogisticsId());
            } else {
                findOffer.add(countSaleLogistics.getOfferId());
                if (logisticIdMap.get(countSaleLogistics.getLogisticsId()) == null) {
                    logisticIdMap.put(countSaleLogistics.getLogisticsId(), new ArrayList());
                }
                logisticIdMap.get(countSaleLogistics.getLogisticsId()).add(countSaleLogistics.getOfferId());
            }
        }

        // 查询所有非单物流单号涉及的物流商信息
        if (findOffer.size() > 0) {
            try {
                offerRelationship = shipmentService.getShipment(findOffer);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        for (FreightExcelVo freightExcelVo : freightExcel) {
            OrderLogisticsInfo saleLogisticsInfoEntity = new OrderLogisticsInfo();
            if (editLogistics.indexOf(freightExcelVo.getLogisticsId()) != -1) {
                BeanUtils.copyProperties(freightExcelVo, saleLogisticsInfoEntity);
                saleLogisticsInfoEntity.setLogisticsCost(freightExcelVo.getFreight());

                editEntity.add(saleLogisticsInfoEntity);
            } else {
                // 匹配物流商信息，截取发货物流渠道的物流商名称进行匹配，若渠道和名称匹配成功，则修改此项费用，物流商名称包含在【】格式里
                if (logisticIdMap.get(freightExcelVo.getLogisticsId()) == null) {
                    return Result.failure(400, "订单不存在物流单号：" + freightExcelVo.getLogisticsId(), "");
                }
                List<String> offerIdList = logisticIdMap.get(freightExcelVo.getLogisticsId());
                for (int m = 0; m < offerIdList.size(); m++) {
                    String offerId = offerIdList.get(m);
                    String offerName = freightExcelVo.getChannelName();

                    String leftBrackets;
                    String rightBrackets;
                    if (offerName.lastIndexOf("]") > offerName.lastIndexOf("】")) {
                        leftBrackets = "[";
                        rightBrackets = "]";
                    } else {
                        leftBrackets = "【";
                        rightBrackets = "】";
                    }

                    if (offerName.indexOf(rightBrackets) == -1 || offerName.indexOf(leftBrackets) == -1) {
                        return Result.failure(400, "物流商内容格式错误：" + freightExcelVo.getLogisticsId(), "");
                    }
                    int startIndex = 0;
                    int endIndex = 0;
                    if (offerName.lastIndexOf(rightBrackets) != -1) {
                        endIndex = offerName.lastIndexOf(rightBrackets);
                    }
                    while (offerName.indexOf(leftBrackets, startIndex + 1) != -1 && offerName.indexOf(leftBrackets, startIndex + 1) < endIndex) {
                        startIndex = offerName.indexOf(leftBrackets, startIndex + 1);
                    }
                    String channelName = offerName.substring(0, startIndex);
                    String shipmentName = offerName.substring(startIndex + 1, endIndex);

                    Map<String, String> relation = offerRelationship.get(offerId);
                    if (relation.get("channelName").equals(channelName) && relation.get("shipmentName").equals(shipmentName)) {
                        BeanUtils.copyProperties(freightExcelVo, saleLogisticsInfoEntity);
                        saleLogisticsInfoEntity.setOfferId(offerId);
                        saleLogisticsInfoEntity.setLogisticsCost(freightExcelVo.getFreight());

                        editEntity.add(saleLogisticsInfoEntity);
                        break;
                    }
                    if (m == logisticIdMap.get(freightExcelVo.getLogisticsId()).size() - 1) {
                        return Result.failure(400, "不存在对应物流：" + freightExcelVo.getLogisticsId() + "\t" + freightExcelVo.getChannelName(), "");
                    }
                }
            }
        }

        // 编辑物流费用
        if (editEntity.size() > 0) {
            for (OrderLogisticsInfo saleLogistics : editEntity) {
                try {
                    saleLogisticsInfoDao.editSaleLogistics(saleLogistics);
                } catch (Exception e) {
                    e.printStackTrace();
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.failure();
                }
            }
        }

        return Result.success();
    }

    @Override
    public Result nvoiceOrderByIds(List ids) {
        List<IdsToNvoiceOutsideDto> result = new ArrayList<>();
        List<NvoiceOrderEntity> nvoiceOrderList;
        List invoiceIds = new ArrayList();
        Map<String, List> saleNvoiceMap = new HashMap<>();

        try {
            nvoiceOrderList = saleNvoiceInfoDao.saleNvoiceByOrder(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (NvoiceOrderEntity nvoiceOrder : nvoiceOrderList) {
            invoiceIds.add(nvoiceOrder.getInvoiceNumber());
        }

        if (invoiceIds.size() > 0) {
            try {
                List<OrderNvoiceRelation> saleNvoiceList = saleNvoiceOrderInfoDao.getOrderIdByInvoice(invoiceIds);
                for (OrderNvoiceRelation saleNvoiceOrder : saleNvoiceList) {
                    if (saleNvoiceMap.get(saleNvoiceOrder.getInvoiceNumber()) == null) {
                        saleNvoiceMap.put(saleNvoiceOrder.getInvoiceNumber(), new ArrayList());
                    }
                    saleNvoiceMap.get(saleNvoiceOrder.getInvoiceNumber()).add(saleNvoiceOrder.getOrderId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (NvoiceOrderEntity nvoiceOrder : nvoiceOrderList) {
                IdsToNvoiceOutsideDto idsToNvoiceOutsideDto = new IdsToNvoiceOutsideDto();
                BeanUtils.copyProperties(nvoiceOrder, idsToNvoiceOutsideDto);
                idsToNvoiceOutsideDto.setInvoiceId(nvoiceOrder.getInvoiceNumber());
                idsToNvoiceOutsideDto.setOrderIds(saleNvoiceMap.get(nvoiceOrder.getInvoiceNumber()));

                result.add(idsToNvoiceOutsideDto);
            }
        }

        return Result.success(result);
    }

    @Override
    @Transactional(transactionManager = "retailOrderTransactionManager", rollbackFor = Exception.class)
    public Result saveNvoiceOrderByIds(List<String> ids, String userId) {
        List failOrderId = new ArrayList();

        for (String orderId : ids) {
            String newId = getNewId();
            if (newId == null) {
                return new Result(400, "数据量已超出设计上限，无法继续添加", null);
            }
            Double totalWeight;
            try {
                totalWeight = saleOrderCommodityInfoDao.getTotalWeight(orderId);
            } catch (Exception e) {
                e.printStackTrace();
                failOrderId.add(orderId);
                continue;
            }

            OrderNvoiceInfo saleNvoiceInfo = new OrderNvoiceInfo();
            saleNvoiceInfo.setInvoiceNumber(newId);
            saleNvoiceInfo.setCreateUser(userId);
            saleNvoiceInfo.setCreateTime(Time.getCurrentDateTime());
            saleNvoiceInfo.setStatusCd(1);
            saleNvoiceInfo.setTotalWeight(totalWeight);
            try {
                saleNvoiceInfoDao.saveSaleNvoice(saleNvoiceInfo);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }

            OrderNvoiceRelation saleNvoiceOrderInfo = new OrderNvoiceRelation();
            saleNvoiceOrderInfo.setOrderId(orderId);
            saleNvoiceOrderInfo.setInvoiceNumber(newId);
            saleNvoiceOrderInfo.setCreateUser(userId);
            saleNvoiceOrderInfo.setCreateTime(Time.getCurrentDateTime());
            saleNvoiceOrderInfo.setUuid(Toolbox.randomUUID());
            try {
                saleNvoiceOrderInfoDao.saveSaleNvoiceOrderInfo(saleNvoiceOrderInfo);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                failOrderId.add(orderId);
            }
        }

        return Result.success("创建发货单失败订单：" + failOrderId);
    }

    private String getNewId() {
        String newId;
        try {
            Integer idNumber = Integer.parseInt(saleNvoiceInfoDao.getMaxId(new SimpleDateFormat("yyMMdd").format(new Date()))) + 1;
            if (idNumber > 999999) return null;
            newId = "SP" + new SimpleDateFormat("yyMMdd").format(new Date()) + new DecimalFormat("000000").format(idNumber);
        } catch (Exception e) {
            e.printStackTrace();
            newId = "SP" + new SimpleDateFormat("yyMMdd").format(new Date()) + "000001";
        }

        return newId;
    }
}
