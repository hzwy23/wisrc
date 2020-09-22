package com.wisrc.warehouse.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.entity.HandmadeOutWarehouseBillEntity;
import com.wisrc.warehouse.webapp.entity.OutWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.OutWarehouseRemarkEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseStockManagerSyncEntity;
import com.wisrc.warehouse.webapp.service.HandmadeOutWarehouseBillService;
import com.wisrc.warehouse.webapp.dao.HandmadeOutWarehouseBillDao;
import com.wisrc.warehouse.webapp.dao.OutWarehouseListDao;
import com.wisrc.warehouse.webapp.dao.OutWarehouseRemarkDao;
import com.wisrc.warehouse.webapp.dao.StockDao;
import com.wisrc.warehouse.webapp.utils.*;
import com.wisrc.warehouse.webapp.query.GetTotalNumQuery;
import com.wisrc.warehouse.webapp.service.ProductService;
import com.wisrc.warehouse.webapp.vo.AddOutWarehouseBillVO;
import com.wisrc.warehouse.webapp.vo.OutWarehouseBillDetailVO;
import com.wisrc.warehouse.webapp.vo.SelectOutBillVO;
import com.wisrc.warehouse.webapp.vo.SelectOutWarehouseBillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class HandmadeOutWarehouseBillServiceImpl implements HandmadeOutWarehouseBillService {
    @Autowired
    private ProductService productService;
    @Autowired
    private HandmadeOutWarehouseBillDao handmadeOutWarehouseBillDao;
    @Autowired
    private OutWarehouseRemarkDao outWarehouseRemarkDao;
    @Autowired
    private OutWarehouseListDao outWarehouseListDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StockDao stockDao;

    @Override
    @Transactional(transactionManager = "retailWarehouseTransactionManager")
    public Result add(AddOutWarehouseBillVO vo, String userId) {
        // 查询各参数对应总库存
        GetTotalNumQuery getTotalNumQuery = new GetTotalNumQuery();
        getTotalNumQuery.setWarehouseId(vo.getBillEntity().getSubWarehouseId());
        List skuIds = new ArrayList();
        for (OutWarehouseListEntity outWarehouse : vo.getList()) {
            skuIds.add(outWarehouse.getSkuId());
        }
        if (skuIds.size() == 0) {
            return Result.failure(400, "手工单产品不能为空", "");
        }
        getTotalNumQuery.setSkuIds(skuIds);
        List<WarehouseStockManagerSyncEntity> stockList = stockDao.getTotalNum(getTotalNumQuery);
        Map<String, Integer> sumStock = new HashMap<>();
        Map<String, Integer> fnskuSumStock = new HashMap<>();
        for (WarehouseStockManagerSyncEntity stockEntity : stockList) {
            if (sumStock.get(Crypto.join(stockEntity.getSkuId(), stockEntity.getSubWarehouseId())) == null) {
                sumStock.put(Crypto.join(stockEntity.getSkuId(), stockEntity.getSubWarehouseId()), stockEntity.getSumStock());
            } else {
                sumStock.put(Crypto.join(stockEntity.getSkuId(), stockEntity.getSubWarehouseId()), sumStock.get(Crypto.join(stockEntity.getSkuId(), stockEntity.getSubWarehouseId())) + stockEntity.getSumStock());
            }
            if (fnskuSumStock.get(Crypto.join(stockEntity.getFnSkuId(), stockEntity.getSkuId(), stockEntity.getSubWarehouseId())) == null) {
                fnskuSumStock.put(Crypto.join(stockEntity.getFnSkuId(), stockEntity.getSkuId(), stockEntity.getSubWarehouseId()), stockEntity.getSumStock());
            } else {
                fnskuSumStock.put(Crypto.join(stockEntity.getFnSkuId(), stockEntity.getSkuId(), stockEntity.getSubWarehouseId()), fnskuSumStock.get(Crypto.join(stockEntity.getFnSkuId(), stockEntity.getSkuId(), stockEntity.getSubWarehouseId())) + stockEntity.getSumStock());
            }
        }
        // 验证对应分仓下库存是否充足
        for (OutWarehouseListEntity outWarehouse : vo.getList()) {
            Integer outWarehouseNum = null;
            if (outWarehouse.getFnSkuId() != null) {
                outWarehouseNum = fnskuSumStock.get(Crypto.join(outWarehouse.getFnSkuId(), outWarehouse.getSkuId(), vo.getBillEntity().getSubWarehouseId()));
            } else {
                outWarehouseNum = sumStock.get(Crypto.join(outWarehouse.getSkuId(), vo.getBillEntity().getSubWarehouseId()));
            }
            if (outWarehouseNum == null) {
                return Result.failure(400, "不存在库存", "");
            }
            if (outWarehouseNum < outWarehouse.getOutWarehouseNum()) {
                return Result.failure(400, "库存超出最大值", "");
            }
        }

        String billId = getBillId("addOutBillId");
        HandmadeOutWarehouseBillEntity entity = vo.getBillEntity();
        entity.setOutBillId(billId);
        entity.setCreateTime(Time.getCurrentTime());
        entity.setCreateUser(userId);
        handmadeOutWarehouseBillDao.add(entity);
        OutWarehouseRemarkEntity remarkEntity = vo.getRemarkEntity();
        remarkEntity.setUuid(UUIDutil.randomUUID());
        remarkEntity.setOutBillId(billId);
        remarkEntity.setCreateTime(Time.getCurrentTime());
        remarkEntity.setCreateUser(userId);
        outWarehouseRemarkDao.add(remarkEntity);
        List<OutWarehouseListEntity> list = vo.getList();
        for (OutWarehouseListEntity listEntity : list) {
            listEntity.setUuid(UUIDutil.randomUUID());
            listEntity.setOutBillId(billId);
            outWarehouseListDao.add(listEntity);
        }
        return Result.success(200, "新增成功", entity);
    }


    @Override
    public LinkedHashMap getList(int num, int size, String outBillId, String warehouseId, int outTypeCd, String skuId, String skuName, String startTime, String endTime) {
        List<SelectOutWarehouseBillVO> list = new ArrayList<>();
        PageHelper.startPage(num, size);
        List<SelectOutBillVO> voList = handmadeOutWarehouseBillDao.getList(outBillId, warehouseId, outTypeCd, startTime, endTime);
        PageInfo pageInfo = new PageInfo(voList);
        for (SelectOutBillVO vo : voList) {
            SelectOutWarehouseBillVO billVO = new SelectOutWarehouseBillVO();
            List<OutWarehouseRemarkEntity> remarkEntities = outWarehouseRemarkDao.getRemarkList(vo.getOutBillId());
            List<OutWarehouseListEntity> entityList = outWarehouseListDao.getCommodityList(vo.getOutBillId());
            if (entityList.size() > 0) {
                getMskuInfo(entityList);
            }
            billVO.setVo(vo);
            billVO.setList(remarkEntities);
            billVO.setEntityList(entityList);
            list.add(billVO);
            for (OutWarehouseListEntity entity : entityList) {
                if (skuId != null) {
                    if (entity.getSkuId().indexOf(skuId) < 0) {
                        list.remove(billVO);
                    }
                }
                if (skuName != null) {
                    if (entity.getSkuName().indexOf(skuName) < 0) {
                        list.remove(billVO);
                    }
                }
            }
        }
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "SelectOutWarehouseBillVOList", list);
    }

    @Override
    public LinkedHashMap getList(String outBillId, String warehouseId, int outTypeCd, String skuId, String skuName, String startTime, String endTime) {
        List<SelectOutWarehouseBillVO> list = new ArrayList<>();
        List<SelectOutBillVO> voList = handmadeOutWarehouseBillDao.getList(outBillId, warehouseId, outTypeCd, startTime, endTime);
        for (int i = 0; i < voList.size(); i++) {
            SelectOutWarehouseBillVO billVO = new SelectOutWarehouseBillVO();
            List<OutWarehouseRemarkEntity> remarkEntities = outWarehouseRemarkDao.getRemarkList(voList.get(i).getOutBillId());
            List<OutWarehouseListEntity> entityList = outWarehouseListDao.getCommodityList(voList.get(i).getOutBillId());
            if (entityList.size() > 0) {
                getMskuInfo(entityList);
            }
            billVO.setVo(voList.get(i));
            billVO.setList(remarkEntities);
            billVO.setEntityList(entityList);
            list.add(billVO);
            for (OutWarehouseListEntity entity : entityList) {
                if (skuId != null) {
                    if (entity.getSkuId().indexOf(skuId) < 0) {
                        list.remove(billVO);
                    }
                }
                if (skuName != null) {
                    if (entity.getSkuName().indexOf(skuName) < 0) {
                        list.remove(billVO);
                    }
                }
            }
        }
        return PageData.pack(list.size(), 1, "SelectOutWarehouseBillVOList", list);
    }

    @Override
    public OutWarehouseBillDetailVO getDetail(String outBillId) {
        OutWarehouseBillDetailVO detailVO = new OutWarehouseBillDetailVO();
        HandmadeOutWarehouseBillEntity vo = handmadeOutWarehouseBillDao.getDetail(outBillId);
        List<OutWarehouseRemarkEntity> remarkEntities = outWarehouseRemarkDao.getRemarkList(outBillId);
        List<OutWarehouseListEntity> commodityList = outWarehouseListDao.getCommodityList(outBillId);
        detailVO.setBillEntity(vo);
        detailVO.setRemarkList(remarkEntities);
        detailVO.setCommodityList(commodityList);
        return detailVO;
    }

    @Override
    public void addRemark(String outBillId, String remark) {
        OutWarehouseRemarkEntity entity = new OutWarehouseRemarkEntity();
        entity.setUuid(UUIDutil.randomUUID());
        entity.setOutBillId(outBillId);
        entity.setCreateTime(Time.getCurrentTime());
        entity.setRemark(remark);
        outWarehouseRemarkDao.add(entity);
    }

    public String getBillId(String rediskey) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
        String currDate = sdf.format(System.currentTimeMillis());
        String key = currDate + rediskey;
        int count = 0;
        long maxId = redisTemplate.opsForValue().increment(key, 1);
        if (maxId == 1) {
            redisTemplate.expire(key, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        }
        if (maxId > 999) {
            throw new RuntimeException("订单号已达最大");
        }
        long num = maxId;
        while (num > 0) {
            num = num / 10;
            count++;
        }
        if (count == 1) {
            return "MO" + currDate + "00" + maxId;
        }
        if (count == 2) {
            return "MO" + currDate + "0" + maxId;
        }
        if (count == 3) {
            return "MO" + currDate + maxId;
        }

        return "MO" + currDate + maxId;
    }

    private void getMskuInfo(List<OutWarehouseListEntity> list) {
        Map<String, Object> mskuMap = new HashMap<>();
        Set<String> set = new HashSet();
        for (OutWarehouseListEntity vo : list) {
            set.add(vo.getSkuId());
        }
        String[] ids = new String[set.size()];
        set.toArray(ids);
        Result mskuResult = productService.getProductInfo(ids);
        Map map = (Map) mskuResult.getData();
        for (OutWarehouseListEntity vo : list) {
            vo.setSkuName((String) map.get(vo.getSkuId()));
        }
    }
}
