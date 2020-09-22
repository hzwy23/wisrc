package com.wisrc.shipment.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wisrc.shipment.webapp.service.LabelService;
import com.wisrc.shipment.webapp.service.WarehouseService;
import com.wisrc.shipment.webapp.dao.ChangeLabelStatusDao;
import com.wisrc.shipment.webapp.dao.LabelDao;
import com.wisrc.shipment.webapp.entity.ChangeLabelStatusEnity;
import com.wisrc.shipment.webapp.entity.ChangeLableDetail;
import com.wisrc.shipment.webapp.entity.ChangeLableEnity;
import com.wisrc.shipment.webapp.entity.RequestVO;
import com.wisrc.shipment.webapp.service.externalService.MskuService;
import com.wisrc.shipment.webapp.service.externalService.WmsService;
import com.wisrc.shipment.webapp.utils.DateUtil;
import com.wisrc.shipment.webapp.utils.PageData;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.utils.UUIDutil;
import com.wisrc.shipment.webapp.vo.ChangeLabelDetailVo;
import com.wisrc.shipment.webapp.vo.ChangeLableViewVo;
import com.wisrc.shipment.webapp.vo.ChangeLableVo;
import com.wisrc.shipment.webapp.vo.ChangeRemarkDetail;
import com.wisrc.shipment.webapp.vo.ChangeRemarkVo;
import com.wisrc.shipment.webapp.vo.LabelDetailDealVo;
import com.wisrc.shipment.webapp.vo.WareHouseVo;
import com.wisrc.shipment.webapp.vo.WarehouseProductVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(transactionManager = "retailShipmentTransactionManager")
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private ChangeLabelStatusDao changeLabelStatusDao;
    @Autowired
    private WmsService wmsService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${wms.url}")
    private String url;

    @Override
    public Result insert(ChangeLableEnity changeLableEnity) {
        String time = Time.getCurrentDateTime();
        if (changeLableEnity.getChangeLabelId() != null) {
            ChangeLableVo lableEnity = labelDao.getLabelById(changeLableEnity.getChangeLabelId());
            if (lableEnity != null) {
                changeLableEnity.setModifyTime(time);
                changeLableEnity.setModifyUser(changeLableEnity.getCreateUser());
                labelDao.update(changeLableEnity);
                labelDao.deleteDetailByLabelId(changeLableEnity.getChangeLabelId());
                List<ChangeLableDetail> changeLableDetailList = changeLableEnity.getChangeLableDetailList();
                for (ChangeLableDetail changeLableDetail : changeLableDetailList) {
                    changeLableDetail.setUuid(UUIDutil.randomUUID());
                    changeLableDetail.setChangeLabelId(changeLableEnity.getChangeLabelId());
                    changeLableDetail.setModifyTime(time);
                    changeLableDetail.setModifyUser(changeLableEnity.getCreateUser());
                    changeLableDetail.setOperationStatusCd(1);
                    labelDao.addLabelDatail(changeLableDetail);
                }
            }
        } else {
            changeLableEnity.setCreateTime(time);
            changeLableEnity.setModifyTime(time);
            changeLableEnity.setStatusCd(1);
            changeLableEnity.setModifyUser(changeLableEnity.getCreateUser());
            String changeLabelId = getChangeLableId("_shipment_service_lable");
            changeLableEnity.setChangeLabelId(changeLabelId);
            labelDao.insert(changeLableEnity);
            List<ChangeLableDetail> detailList = changeLableEnity.getChangeLableDetailList();
            for (ChangeLableDetail changeLableDetail : detailList) {
                changeLableDetail.setUuid(UUIDutil.randomUUID());
                changeLableDetail.setChangeLabelId(changeLabelId);
                changeLableDetail.setModifyTime(time);
                changeLableDetail.setModifyUser(changeLableEnity.getCreateUser());
                changeLableDetail.setOperationStatusCd(1);
                labelDao.addLabelDatail(changeLableDetail);
            }
        }
//      未接入fnSku的预分配库存
        return Result.success();
    }

    @Override
    public ChangeLableVo getLabelDetail(String changeLabelId) {
        Map<Integer, String> statusMap = new HashMap();
        List<ChangeLabelStatusEnity> changeLabelStatusEnityList = changeLabelStatusDao.getAll();
        for (ChangeLabelStatusEnity changeLabelStatusEnity : changeLabelStatusEnityList) {
            statusMap.put(changeLabelStatusEnity.getOperationStatusCd(), changeLabelStatusEnity.getOperationStatusName());
        }
        Map wareMap = new HashMap();
        ChangeLableVo changeLableVo = labelDao.getLabelById(changeLabelId);
        WarehouseProductVo warehouseProductVo = new WarehouseProductVo();
        Result mskuResult = mskuService.getMskuInfoByFnsku(changeLableVo.getFnsku());
        if (mskuResult.getCode() == 200) {
            Map map = (Map) mskuResult.getData();
            if (map != null) {
                warehouseProductVo.setPicture((String) map.get("picture"));
                warehouseProductVo.setSkuId((String) map.get("skuId"));
                warehouseProductVo.setSkuNameZh((String) map.get("productName"));
            }
        } else {
            throw new IllegalStateException("不存在的目标fnsku");
        }
        if (changeLableVo == null) {
            return null;
        }
        changeLableVo.setSkuId(warehouseProductVo.getSkuId());
        changeLableVo.setOperationStatusName(statusMap.get(changeLableVo.getStatusCd()));
        List<ChangeLabelDetailVo> labelDetailVos = labelDao.getLabelDetail(changeLabelId);
        Result wareResult = warehouseService.getWareHouser(changeLableVo.getSkuId(), changeLableVo.getWareHouseId());
        if (wareResult.getCode() == 200) {
            List<Object> objectList = (List<Object>) wareResult.getData();
            for (Object object : objectList) {
                Map map = (Map) object;
                String subWarehouseId = (String) map.get("subWarehouseId");
                String fnsku = (String) map.get("fnSkuId");
                wareMap.put(subWarehouseId + fnsku, map);
            }
        }
        for (ChangeLabelDetailVo changeLabelDetailVo : labelDetailVos) {
            Map map = (Map) wareMap.get(changeLabelDetailVo.getSubWarehouseId() + changeLabelDetailVo.getFnsku());
            changeLabelDetailVo.setOperationStatusName(statusMap.get(changeLabelDetailVo.getOperationStatusCd()));
            if (map != null) {
                String subWarehouseName = (String) map.get("subWarehouseName");
                String enterBatch = (String) map.get("enterBatch");
                if (map.get("enterBatch") != null) {
                    Date date = DateUtil.convertStrToDate(enterBatch, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                    enterBatch = DateUtil.convertDateToStr(date, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                }
                String productionBatch = (String) map.get("productionBatch");
                Integer onWarehouseStockNum = (Integer) map.get("onWarehouseStockNum");
                Integer enableStockNum = (Integer) map.get("enableStockNum");
                changeLabelDetailVo.setEnterBatch(enterBatch);
                changeLabelDetailVo.setProductionBatch(productionBatch);
                changeLabelDetailVo.setOnWarehouseStockNum(onWarehouseStockNum);
                changeLabelDetailVo.setEableStockNum(enableStockNum);
                changeLabelDetailVo.setSubWarehouseName(subWarehouseName);
            }
        }
        Collections.sort(labelDetailVos, new Comparator<ChangeLabelDetailVo>() {

            @Override
            public int compare(ChangeLabelDetailVo o1, ChangeLabelDetailVo o2) {
                if (o1.getEnterBatch() == null || o2.getEnterBatch() == null) {
                    return 0;
                }
                if (o1.getEnterBatch().compareTo(o2.getEnterBatch()) > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        changeLableVo.setChangeLableDetailList(labelDetailVos);
        return changeLableVo;
    }

    @Override
    public LinkedHashMap findByCond(String pageNum, String pageSize, String startTime, String endTime, String wareHouseId, int statusCd, String keyword) {
        Map<Integer, String> statusMap = new HashMap();
        List<ChangeLabelStatusEnity> changeLabelStatusEnityList = changeLabelStatusDao.getAll();
        for (ChangeLabelStatusEnity changeLabelStatusEnity : changeLabelStatusEnityList) {
            statusMap.put(changeLabelStatusEnity.getOperationStatusCd(), changeLabelStatusEnity.getOperationStatusName());
        }
        List<ChangeLableViewVo> basisList = null;
        Map<String, String> wareIdNameMap = new HashMap<>();
        if (pageNum != null && pageSize != null) {
            int num = Integer.valueOf(pageNum);
            int size = Integer.valueOf(pageSize);
            PageHelper.startPage(num, size);
            basisList = labelDao.findByCond(startTime, endTime, wareHouseId, statusCd, keyword);
        } else {
            basisList = labelDao.findByCond(startTime, endTime, wareHouseId, statusCd, keyword);
        }
        StringBuilder stringBuilder = new StringBuilder();
        PageInfo pageInfo = new PageInfo(basisList);
        for (ChangeLableViewVo changeLableViewVo : basisList) {
            List<ChangeLabelDetailVo> changeLabelDetailVos = labelDao.getLabelDetail(changeLableViewVo.getChangeLabelId());
            int changeQuantity = 0;
            int changedQuantity = 0;
            changeLableViewVo.setOperationStatusName(statusMap.get(changeLableViewVo.getStatusCd()));
            stringBuilder.append(changeLableViewVo.getWareHouseId() + ",");
            for (ChangeLabelDetailVo changeLabelDetailVo : changeLabelDetailVos) {
                if (changeLabelDetailVo.getChangeQuantity() != null) {
                    changeQuantity += changeLabelDetailVo.getChangeQuantity();
                }
                if (changeLabelDetailVo.getChangedQuantity() != null) {
                    changedQuantity += changeLabelDetailVo.getChangedQuantity();
                }
                changeLableViewVo.setChangeQuantity(changeQuantity);
                changeLableViewVo.setChangedQuantity(changedQuantity);
            }
        }
        Result wareResult = warehouseService.getWareHouseBatch(stringBuilder.toString());
        if (wareResult.getCode() == 200) {
            List<Object> objectList = (List<Object>) wareResult.getData();
            for (Object object : objectList) {
                Map map = (Map) object;
                String warehouseId = (String) map.get("warehouseId");
                String warehouseName = (String) map.get("warehouseName");
                wareIdNameMap.put(warehouseId, warehouseName);
            }
        }
        for (ChangeLableViewVo changeLableViewVo : basisList) {
            changeLableViewVo.setWareHouseName(wareIdNameMap.get(changeLableViewVo.getWareHouseId()));
        }
        if (pageNum != null && pageSize != null) {
            return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "changeLableList", basisList);
        }
        return PageData.pack(-1, -1, "changeLableList", basisList);
    }

    @Override
    public WarehouseProductVo getWarehouseAndProduct(String fnsku, String warehouseId) {
        WarehouseProductVo warehouseProductVo = new WarehouseProductVo();
        Result mskuResult = mskuService.getMskuInfoByFnsku(fnsku);
        if (mskuResult.getCode() == 200) {
            Map map = (Map) mskuResult.getData();
            if (map != null) {
                warehouseProductVo.setPicture((String) map.get("picture"));
                warehouseProductVo.setSkuId((String) map.get("skuId"));
                warehouseProductVo.setSkuNameZh((String) map.get("productName"));
            } else {
                return null;
            }
        }
        List<WareHouseVo> changeLabelDetailVos = new ArrayList<>();
        Result wareResult = warehouseService.getWareHouser(warehouseProductVo.getSkuId(), warehouseId);
        if (wareResult.getCode() == 200) {
            List<Object> objectList = (List<Object>) wareResult.getData();
            for (Object object : objectList) {
                Map map = (Map) object;
                String fnSkuId = (String) map.get("fnSkuId");
                if (fnSkuId == null || fnSkuId.equals(fnsku)) {
                    continue;
                }
                String subWarehouseName = (String) map.get("subWarehouseName");
                String subWarehouseId = (String) map.get("subWarehouseId");
                String warehousePosition = (String) map.get("warehousePosition");
                String enterBatch = (String) map.get("enterBatch");
                if (map.get("enterBatch") != null) {
                    Date date = DateUtil.convertStrToDate(enterBatch, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                    enterBatch = DateUtil.convertDateToStr(date, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                }
                String productionBatch = (String) map.get("productionBatch");
                Integer onWarehouseStockNum = (Integer) map.get("onWarehouseStockNum");
                Integer enableStockNum = (Integer) map.get("enableStockNum");
                WareHouseVo wareHouseVo = new WareHouseVo();
                wareHouseVo.setFnsku(fnSkuId);
                wareHouseVo.setWarehousePosition(warehousePosition);
                wareHouseVo.setEnterBatch(enterBatch);
                wareHouseVo.setProductionBatch(productionBatch);
                wareHouseVo.setOnWarehouseStockNum(onWarehouseStockNum);
                wareHouseVo.setEableStockNum(enableStockNum);
                wareHouseVo.setSubWarehouseName(subWarehouseName);
                wareHouseVo.setSubWarehouseId(subWarehouseId);
                changeLabelDetailVos.add(wareHouseVo);
            }
        }
        warehouseProductVo.setChangeLableDetailList(changeLabelDetailVos);
        return warehouseProductVo;
    }

    @Override
    public void updateDetail(LabelDetailDealVo labelDetailDealVo) {
        String time = Time.getCurrentDateTime();
        labelDetailDealVo.setCompleteTime(time);
        labelDao.updateDetail(labelDetailDealVo);
        List<ChangeLabelDetailVo> changeLableDetailList = labelDao.getLabelDetail(labelDetailDealVo.getChangeLabelId());
        if (changeLableDetailList == null || changeLableDetailList.size() <= 0) {
            return;
        }
        ChangeLabelDetailVo changeLabelDetail = labelDao.getById(labelDetailDealVo.getUuid());
        if (changeLabelDetail.getChangeQuantity() < labelDetailDealVo.getChangedQuantity()) {
            throw new RuntimeException("换标数不能大于安排换标数");
        }
        ChangeLableVo changeLableVo = labelDao.getLabelById(labelDetailDealVo.getChangeLabelId());
        int allStatus = 1;
        for (ChangeLabelDetailVo changeLabelDetailVo : changeLableDetailList) {
            if (changeLabelDetailVo.getOperationStatusCd() == 2 || changeLabelDetailVo.getOperationStatusCd() == 3) {
                allStatus *= 1;
            } else {
                allStatus *= 2;
            }
        }
        if (allStatus == 1) {
            labelDao.changeStatus(labelDetailDealVo.getChangeLabelId(), 2);
        }
        RequestVO vo = new RequestVO();
        vo.setMethod("wisen.wms.voucher.changemark.sync");
        vo.setFormat("json");
        vo.setTimestamp(Time.getCurrentDateTime());
        if (labelDetailDealVo.getChangedQuantity() != null && labelDetailDealVo.getChangedQuantity() > 0) {
            ChangeRemarkVo changeRemarkVo = new ChangeRemarkVo();
            String changeLabelId = labelDetailDealVo.getChangeLabelId();
            long serial = redisTemplate.opsForValue().increment(changeLabelId + "changeLabel", 1);
            if (serial == 1) {
                redisTemplate.expire(changeLabelId + "changeLabel", 1000 * 60 * 60 * 24 * 90, TimeUnit.MILLISECONDS);
                redisTemplate.opsForValue().set(changeLabelId + "changeLabel", 1);
            }
            long num = serial;
            int count = 0;
            while (num > 0) {
                num = num / 10;
                count++;
            }
            if (count == 1) {
                changeRemarkVo.setVoucherCode(changeLabelId + "-00" + serial);
            } else if (count == 2) {
                changeRemarkVo.setVoucherCode(changeLabelId + "-0" + serial);
            } else {
                changeRemarkVo.setVoucherCode(changeLabelId + "-" + serial);
            }
            changeRemarkVo.setVoucherType("HB");
            List<ChangeRemarkDetail> changeMarkList = new ArrayList<>();
            ChangeRemarkDetail changeRemarkDetail = new ChangeRemarkDetail();
            changeRemarkDetail.setNewFnCode(changeLableVo.getFnsku());
            changeRemarkDetail.setTargetSectionCode(changeLableVo.getSubWarehouseId());
            changeRemarkDetail.setOldFnCode(changeLabelDetail.getFnsku());
            changeRemarkDetail.setSectionCode(changeLabelDetail.getSubWarehouseId());
            changeRemarkDetail.setTotalQuantity(Double.parseDouble(labelDetailDealVo.getChangedQuantity() + ""));
            changeRemarkDetail.setLineNum(1);
            Result mskuResult = mskuService.getMskuInfoByFnsku(changeLableVo.getFnsku());
            if (mskuResult.getCode() == 200) {
                Map map = (Map) mskuResult.getData();
                if (map != null) {
                    String skuId = (String) map.get("skuId");
                    changeRemarkDetail.setGoodsCode(skuId);
                }
            }
            changeMarkList.add(changeRemarkDetail);
            changeRemarkVo.setChangeMarkList(changeMarkList);
            vo.setData(changeRemarkVo);
            Gson gson = new Gson();
            Result result = wmsService.changeLabelSync(gson.toJson(vo));
            if (result.getCode() != 0) {
                throw new RuntimeException(result.getMsg());
            }
        }
    }

    @Override
    public void cancelChangeLabel(String changeLabelId, String reason, String userId) {
        labelDao.cancelChangeLabel(changeLabelId, reason, userId);
        labelDao.cancelChangeLabelDetail(3, changeLabelId);
    }

    /**
     * 更新fnsku条码文件id
     *
     * @param changeLabelId
     * @param fnskuCodeFileId
     */
    @Override
    public void updateFnskuCodeFileId(String changeLabelId, String fnskuCodeFileId) {
        labelDao.changeFnskuCodeFileId(changeLabelId, fnskuCodeFileId);
    }


    public String getChangeLableId(String rediskey) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
        String currDate = sdf.format(System.currentTimeMillis());
        String key = currDate + rediskey;
        int count = 0;
        long maxId = redisTemplate.opsForValue().increment(key, 1);
        if (maxId == 1) {
            redisTemplate.expire(key, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        }
        if (maxId > 999) {
            throw new RuntimeException("今日换标已达最大");
        }
        long num = maxId;
        while (num > 0) {
            num = num / 10;
            count++;
        }
        if (count == 1) {
            return "C" + currDate + "00" + maxId;
        }
        if (count == 2) {
            return "C" + currDate + "0" + maxId;
        }
        if (count == 3) {
            return "C" + currDate + maxId;
        }
        return null;
    }
}
