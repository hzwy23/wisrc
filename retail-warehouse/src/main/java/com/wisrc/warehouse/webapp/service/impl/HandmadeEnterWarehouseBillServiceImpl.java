package com.wisrc.warehouse.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.dao.EnterWarehouseListDao;
import com.wisrc.warehouse.webapp.dao.EnterWarehouseRemarkDao;
import com.wisrc.warehouse.webapp.dao.HandmadeEnterWarehouseBillDao;
import com.wisrc.warehouse.webapp.entity.EnterWarehouseListEntity;
import com.wisrc.warehouse.webapp.entity.EnterWarehouseRemarkEntity;
import com.wisrc.warehouse.webapp.entity.HandmadeEnterWarehouseBillEntity;
import com.wisrc.warehouse.webapp.entity.HandmadeStatusEntity;
import com.wisrc.warehouse.webapp.service.HandmadeEnterWarehouseBillService;
import com.wisrc.warehouse.webapp.service.ProductService;
import com.wisrc.warehouse.webapp.utils.PageData;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.Time;
import com.wisrc.warehouse.webapp.utils.UUIDutil;
import com.wisrc.warehouse.webapp.vo.AddEnterWarehouseBillVO;
import com.wisrc.warehouse.webapp.vo.SelectEnterBillVO;
import com.wisrc.warehouse.webapp.vo.SelectEnterWarehouseBillVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class HandmadeEnterWarehouseBillServiceImpl implements HandmadeEnterWarehouseBillService {
    @Autowired
    private ProductService productService;
    @Autowired
    private HandmadeEnterWarehouseBillDao handmadeEnterWarehouseBillDao;
    @Autowired
    private EnterWarehouseRemarkDao enterWarehouseRemarkDao;
    @Autowired
    private EnterWarehouseListDao enterWarehouseListDao;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    @Transactional(transactionManager = "retailWarehouseTransactionManager")
    public Result add(AddEnterWarehouseBillVO vo, String userId) {
        String billId = getBillId("addEnterBillId");
        HandmadeEnterWarehouseBillEntity billEntity = vo.getBillEntity();
        billEntity.setEnterBillId(billId);
        billEntity.setCreateTime(Time.getCurrentTime());
        billEntity.setCreateUser(userId);
        handmadeEnterWarehouseBillDao.add(billEntity);
        EnterWarehouseRemarkEntity remarkEntity = vo.getRemarkEntity();
        remarkEntity.setUuid(UUIDutil.randomUUID());
        remarkEntity.setEnterBillId(billId);
        remarkEntity.setCreateTime(Time.getCurrentTime());
        remarkEntity.setCreateUser(userId);
        enterWarehouseRemarkDao.add(remarkEntity);
        List<EnterWarehouseListEntity> list = vo.getList();
        for (EnterWarehouseListEntity listEntity : list) {
            listEntity.setUuid(UUIDutil.randomUUID());
            listEntity.setEnterBillId(billId);
            enterWarehouseListDao.add(listEntity);
        }
        return Result.success(200, "新增成功", billEntity);
    }

    @Override
    public LinkedHashMap getList(int num, int size, String warehouseId, Integer enterTypeCd, Integer status, String startTime, String endTime, String keyword) {
        List<SelectEnterWarehouseBillVO> selectEnterWarehouseBillVOS = new ArrayList<>();
        Result imgResult = productService.getAllImgs();
        Map imgMap = (Map) imgResult.getData();
        if (num == 0 && size == 0) {
            List<SelectEnterBillVO> selectEnterBillVOS = handmadeEnterWarehouseBillDao.findHandmadeListByCond(warehouseId, enterTypeCd, status, startTime, endTime, keyword);
            for (SelectEnterBillVO selectEnterBillVO : selectEnterBillVOS) {
                SelectEnterWarehouseBillVO selectEnterWarehouseBillVO = new SelectEnterWarehouseBillVO();
                selectEnterWarehouseBillVO.setVo(selectEnterBillVO);
                List<EnterWarehouseRemarkEntity> remarkEntities = enterWarehouseRemarkDao.getRemarkList(selectEnterBillVO.getEnterBillId());
                selectEnterWarehouseBillVO.setList(remarkEntities);
                List<EnterWarehouseListEntity> enterWarehouseListEntities = handmadeEnterWarehouseBillDao.findSkuListByHandmadeEnterBillId(selectEnterBillVO.getEnterBillId());
                for (EnterWarehouseListEntity enterWarehouseListEntity : enterWarehouseListEntities) {
                    enterWarehouseListEntity.setImgUrls((List<String>) imgMap.get(enterWarehouseListEntity.getSkuId()));
                }
                selectEnterWarehouseBillVO.setEntityList(enterWarehouseListEntities);
                selectEnterWarehouseBillVOS.add(selectEnterWarehouseBillVO);
            }
            return PageData.pack(selectEnterWarehouseBillVOS.size(), 1, "list", selectEnterWarehouseBillVOS);
        } else {
            PageHelper.startPage(num, size);
            List<SelectEnterBillVO> selectEnterBillVOS = handmadeEnterWarehouseBillDao.findHandmadeListByCond(warehouseId, enterTypeCd, status, startTime, endTime, keyword);
            PageInfo pageInfo = new PageInfo(selectEnterBillVOS);
            for (SelectEnterBillVO selectEnterBillVO : selectEnterBillVOS) {
                SelectEnterWarehouseBillVO selectEnterWarehouseBillVO = new SelectEnterWarehouseBillVO();
                selectEnterWarehouseBillVO.setVo(selectEnterBillVO);
                List<EnterWarehouseRemarkEntity> remarkEntities = enterWarehouseRemarkDao.getRemarkList(selectEnterBillVO.getEnterBillId());
                selectEnterWarehouseBillVO.setList(remarkEntities);
                List<EnterWarehouseListEntity> enterWarehouseListEntities = handmadeEnterWarehouseBillDao.findSkuListByHandmadeEnterBillId(selectEnterBillVO.getEnterBillId());
                for (EnterWarehouseListEntity enterWarehouseListEntity : enterWarehouseListEntities) {
                    enterWarehouseListEntity.setImgUrls((List<String>) imgMap.get(enterWarehouseListEntity.getSkuId()));
                }
                selectEnterWarehouseBillVO.setEntityList(enterWarehouseListEntities);
                selectEnterWarehouseBillVOS.add(selectEnterWarehouseBillVO);
            }
            return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "list", selectEnterWarehouseBillVOS);
        }
    }

    @Override
    public LinkedHashMap getList(String enterBillId, String warehouseId, int enterTypeCd, String skuId, String skuName, String startTime, String endTime) {
        List<SelectEnterWarehouseBillVO> list = new ArrayList<>();
        List<SelectEnterBillVO> voList = handmadeEnterWarehouseBillDao.getList(enterBillId, warehouseId, enterTypeCd, startTime, endTime);
        for (int i = 0; i < voList.size(); i++) {
            SelectEnterWarehouseBillVO billVO = new SelectEnterWarehouseBillVO();
            List<EnterWarehouseRemarkEntity> remarkEntities = enterWarehouseRemarkDao.getRemarkList(voList.get(i).getEnterBillId());
            List<EnterWarehouseListEntity> entityList = enterWarehouseListDao.getCommodityList(voList.get(i).getEnterBillId());
            if (entityList.size() > 0) {
                getMskuInfo(entityList);
            }
            billVO.setVo(voList.get(i));
            billVO.setList(remarkEntities);
            billVO.setEntityList(entityList);
            list.add(billVO);
            for (EnterWarehouseListEntity entity : entityList) {
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
        return PageData.pack(list.size(), 1, "SelectEnterWarehouseBillVOList", list);
    }

    @Override
    public Result getDetail(String enterBillId) {
        Result imgResult = productService.getAllImgs();
        Map imgMap = (Map) imgResult.getData();
        SelectEnterWarehouseBillVO selectEnterWarehouseBillVO = new SelectEnterWarehouseBillVO();
        SelectEnterBillVO selectEnterBillVO = handmadeEnterWarehouseBillDao.findHandmadeById(enterBillId);
        List<EnterWarehouseRemarkEntity> remarkEntities = enterWarehouseRemarkDao.getRemarkList(enterBillId);
        List<EnterWarehouseListEntity> enterWarehouseListEntities = handmadeEnterWarehouseBillDao.findSkuListByHandmadeEnterBillId(enterBillId);
        for (EnterWarehouseListEntity enterWarehouseListEntity : enterWarehouseListEntities) {
            enterWarehouseListEntity.setImgUrls((List<String>) imgMap.get(enterWarehouseListEntity.getSkuId()));
        }
        selectEnterWarehouseBillVO.setVo(selectEnterBillVO);
        selectEnterWarehouseBillVO.setEntityList(enterWarehouseListEntities);
        selectEnterWarehouseBillVO.setList(remarkEntities);
        return Result.success(selectEnterWarehouseBillVO);
    }

    @Override
    public void addRemark(String enterBillId, String remark, String userId) {
        EnterWarehouseRemarkEntity entity = new EnterWarehouseRemarkEntity();
        entity.setUuid(UUIDutil.randomUUID());
        entity.setEnterBillId(enterBillId);
        entity.setCreateTime(Time.getCurrentTime());
        entity.setCreateUser(userId);
        entity.setRemark(remark);
        enterWarehouseRemarkDao.add(entity);
    }

    @Override
    public void cancel(String enterBillId, String cancelReason) {
        handmadeEnterWarehouseBillDao.updateStatus(enterBillId, 3);
        handmadeEnterWarehouseBillDao.updateCancelReason(enterBillId, cancelReason);
    }

    @Override
    public Result getAllStatus() {
        List<HandmadeStatusEntity> handmadeStatusEntities = handmadeEnterWarehouseBillDao.findAllStatus();
        return Result.success(handmadeStatusEntities);
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
            return "MI" + currDate + "00" + maxId;
        }
        if (count == 2) {
            return "MI" + currDate + "0" + maxId;
        }
        if (count == 3) {
            return "MI" + currDate + maxId;
        }

        return "MI" + currDate + maxId;
    }


    private void getMskuInfo(List<EnterWarehouseListEntity> list) {
        Map<String, Object> mskuMap = new HashMap<>();
        Set<String> set = new HashSet();
        for (EnterWarehouseListEntity vo : list) {
            set.add(vo.getSkuId());
        }
        String[] ids = new String[set.size()];
        set.toArray(ids);
        Result mskuResult = productService.getProductInfo(ids);
        Map map = (Map) mskuResult.getData();
        for (EnterWarehouseListEntity vo : list) {
            vo.setSkuName((String) map.get(vo.getSkuId()));
        }
    }
}
