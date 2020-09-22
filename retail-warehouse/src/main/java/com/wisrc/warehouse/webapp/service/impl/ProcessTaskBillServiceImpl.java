package com.wisrc.warehouse.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.service.SkuInfoService;
import com.wisrc.warehouse.webapp.dao.ProcessDetailDao;
import com.wisrc.warehouse.webapp.dao.ProcessTaskBillDao;
import com.wisrc.warehouse.webapp.dao.ProcessTaskStatusAttrDao;
import com.wisrc.warehouse.webapp.entity.ProcessDetailEntity;
import com.wisrc.warehouse.webapp.entity.ProcessTaskBillEntity;
import com.wisrc.warehouse.webapp.entity.ProcessTaskStatusAttrEntity;
import com.wisrc.warehouse.webapp.service.ProcessTaskBillService;
import com.wisrc.warehouse.webapp.service.ProductService;
import com.wisrc.warehouse.webapp.utils.PageData;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.Time;
import com.wisrc.warehouse.webapp.utils.UUIDutil;
import com.wisrc.warehouse.webapp.vo.AddProcessTaskBillVO;
import com.wisrc.warehouse.webapp.vo.SelectProcessDetailVO;
import com.wisrc.warehouse.webapp.vo.SelectProcessTaskBillVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProcessTaskBillServiceImpl implements ProcessTaskBillService {

    @Autowired
    private ProcessTaskBillDao processTaskBillDao;
    @Autowired
    private ProcessDetailDao processDetailDao;
    @Autowired
    private ProcessTaskStatusAttrDao processTaskStatusAttrDao;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ProductService productService;

    @Override
    @Transactional(transactionManager = "retailWarehouseTransactionManager")
    public Result add(AddProcessTaskBillVO vo) {
        ProcessTaskBillEntity entity = vo.getEntity();
        String uuid = UUIDutil.randomUUID();
        entity.setProcessTaskId(uuid);
        entity.setRandomValue(uuid);
        entity.setCreateTime(Time.getCurrentTime());
        processTaskBillDao.add(entity);

        String processTaskId = processTaskBillDao.findProcessTaskIdByRandomValue(uuid);
        entity.setProcessTaskId(processTaskId);
        List<ProcessDetailEntity> entityList = vo.getEntityList();
        for (ProcessDetailEntity detail : entityList) {
            detail.setUuid(UUIDutil.randomUUID());
            detail.setProcessTaskId(processTaskId);
            processDetailDao.add(detail);
        }
        return Result.success(vo);
    }


    @Override
    public List<ProcessTaskBillEntity> getProcessTaskBillAll() {
        return processTaskBillDao.findAll();
    }

    @Override
    public LinkedHashMap getProcessTaskBillAll(int startPage, int pageSize) {
        PageHelper.startPage(startPage, pageSize);
        List<ProcessTaskBillEntity> processList = processTaskBillDao.findAll();
        List<SelectProcessTaskBillVO> processVoList = processListToVo(processList);
        PageInfo<ProcessTaskBillEntity> info = new PageInfo<>(processList);
        return PageData.pack(info.getTotal(), info.getPages(), "processTaskBillList", processVoList);
    }

    @Override
    public List<ProcessTaskBillEntity> search(String processStartDate, String processEndDate, String processLaterSku, int statusCd, String warehouseId, String createUser, String productName) {
        List<ProcessTaskBillEntity> processList = processTaskBillDao.search(processStartDate, processEndDate, processLaterSku, statusCd, warehouseId, createUser);
        if (productName != null) {
            List<ProcessTaskBillEntity> processListBySku = processTaskBillDao.getAllByProcessLaterSku(processLaterSku);

            List result = new ArrayList();
            result.addAll(processList);
            result.addAll(processListBySku);

            return result;
        }
        return processList;
    }

    @Override
    public LinkedHashMap getProcessTaskBillAll(int startPage, int pageSize, String processStartDate, String processEndDate, String processLaterSku, int statusCd, String warehouseId, String createUser, String productName) {
        if (startPage == 0 && pageSize == 0) {
            List<String> skuIds = new ArrayList<>();
            if (StringUtils.isNotEmpty(productName)) {
                Result skuInfoBySkuName = productService.getSkuInfoBySkuName(productName);
                if (skuInfoBySkuName.getCode() != 200) {
                    throw new RuntimeException("调用查询产品接口服务失败");
                }
                Map skuInfoListMap = (Map) skuInfoBySkuName.getData();
                List<Map> skuList = (List<Map>) skuInfoListMap.get("productData");
                for (Map map : skuList) {
                    skuIds.add((String) map.get("sku"));
                }
                List<ProcessTaskBillEntity> processList = processTaskBillDao.searchByCond(processStartDate, processEndDate, processLaterSku, statusCd, warehouseId, createUser, skuIds);
                List<SelectProcessTaskBillVO> processVoList = processListToVo(processList);
                return PageData.pack(processVoList.size(), 1, "processTaskBillList", processVoList);
            } else {
                List<ProcessTaskBillEntity> processList = processTaskBillDao.searchByCond(processStartDate, processEndDate, processLaterSku, statusCd, warehouseId, createUser, skuIds);
                List<SelectProcessTaskBillVO> processVoList = processListToVo(processList);
                return PageData.pack(processVoList.size(), 1, "processTaskBillList", processVoList);
            }
        } else {
            PageHelper.startPage(startPage, pageSize);
            List<String> skuIds = new ArrayList<>();
            if (StringUtils.isNotEmpty(productName)) {
                Result skuInfoBySkuName = productService.getSkuInfoBySkuName(productName);
                if (skuInfoBySkuName.getCode() != 200) {
                    throw new RuntimeException("调用查询产品接口服务失败");
                }
                Map skuInfoListMap = (Map) skuInfoBySkuName.getData();
                List<Map> skuList = (List<Map>) skuInfoListMap.get("productData");
                for (Map map : skuList) {
                    skuIds.add((String) map.get("sku"));
                }
                List<ProcessTaskBillEntity> processList = processTaskBillDao.searchByCond(processStartDate, processEndDate, processLaterSku, statusCd, warehouseId, createUser, skuIds);
                PageInfo<ProcessTaskBillEntity> info = new PageInfo<>(processList);
                List<SelectProcessTaskBillVO> processVoList = processListToVo(processList);
                return PageData.pack(info.getTotal(), info.getPages(), "processTaskBillList", processVoList);
            } else {
                List<ProcessTaskBillEntity> processList = processTaskBillDao.searchByCond(processStartDate, processEndDate, processLaterSku, statusCd, warehouseId, createUser, skuIds);
                List<SelectProcessTaskBillVO> processVoList = processListToVo(processList);
                PageInfo<ProcessTaskBillEntity> info = new PageInfo<>(processList);
                return PageData.pack(info.getTotal(), info.getPages(), "processTaskBillList", processVoList);
            }
        }
    }


    @Override
    public LinkedHashMap getProcessTaskBillByProcessTaskId(String processTaskId) {
        LinkedHashMap map = new LinkedHashMap();
        //加工主信息
        map.put("processTaskBill", processToVO(processTaskBillDao.getByProcessTaskId(processTaskId)));
        //加工明细
        map.put("processDetailList", detailToVO(processDetailDao.findByProcessTaskId(processTaskId)));
        return map;
    }

    @Override
    public List<ProcessTaskStatusAttrEntity> getStatusList() {
        List<ProcessTaskStatusAttrEntity> list = processTaskStatusAttrDao.getStatusList();
        return list;
    }

    @Override
    public ProcessTaskStatusAttrEntity getStatusDetail(String statusCd) {
        ProcessTaskStatusAttrEntity entity = processTaskStatusAttrDao.getStatusDetail(statusCd);
        return entity;
    }

    @Override
    public Result changeStatus(String processTaskId, String status) {
        try {
            if ("0".equals(status)) {
                processDetailDao.changeStatus(processTaskId, 3);
            }
            return Result.success("更新加工任务单状态成功");
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @Override
    public void update(String processTaskId) {
        processTaskBillDao.update(processTaskId);
    }

    //将 ProcessTaskBill 的list集合转化为其 VO 的list集合
    public List<SelectProcessTaskBillVO> processListToVo(List<ProcessTaskBillEntity> processList) {
        List<SelectProcessTaskBillVO> processVoList = new LinkedList<>();
        String[] ids = new String[processList.size()];

        for (int i = 0; i < processList.size(); i++) {
            ids[i] = processList.get(i).getProcessLaterSku();
        }

        Map map = new HashMap();
        try {
            //得到产品中文名
            Result skuName = skuInfoService.getSkuName(ids);
            map = (Map) skuName.getData();
        } catch (Exception e) {
        }

        for (ProcessTaskBillEntity one : processList) {
            SelectProcessTaskBillVO vo = new SelectProcessTaskBillVO();
            vo.setProcessTaskId(one.getProcessTaskId());
            vo.setStatusCd(one.getStatusCd());
            vo.setProcessDate(one.getProcessDate());
            vo.setProcessTaskSku(one.getProcessLaterSku());
            //已完成：获取中文名
            vo.setProductName((String) map.get(one.getProcessLaterSku()));
            vo.setProcessNum(one.getProcessNum());
            vo.setWarehouseId(one.getWarehouseId());
            vo.setBatch(one.getBatch());
            vo.setRemark(one.getRemark());
            vo.setCreateUser(one.getCreateUser());
            processVoList.add(vo);
        }
        return processVoList;
    }

    // 将 ProcessTaskBill 转化为对应的 VO
    public SelectProcessTaskBillVO processToVO(ProcessTaskBillEntity process) {

        String[] ids = new String[1];

        ids[0] = process.getProcessLaterSku();

        Map map = new HashMap();
        try {
            Result skuName = skuInfoService.getSkuName(ids);
            map = (Map) skuName.getData();
        } catch (Exception e) {
        }

        SelectProcessTaskBillVO vo = new SelectProcessTaskBillVO();
        vo.setProcessTaskId(process.getProcessTaskId());
        vo.setProcessDate(process.getProcessDate());
        vo.setStatusCd(process.getStatusCd());
        vo.setProcessTaskSku(process.getProcessLaterSku());
        vo.setProcessNum(process.getProcessNum());
        vo.setWarehouseId(process.getWarehouseId());
        //已完成：获取中文名
        vo.setProductName((String) map.get(process.getProcessLaterSku()));
        vo.setBatch(process.getBatch());
        vo.setRemark(process.getRemark());

        return vo;
    }

    // 将 ProcessDetail 的list集合转化为其对应的 VO 的list集合
    public List<SelectProcessDetailVO> detailToVO(List<ProcessDetailEntity> detailList) {
        List<SelectProcessDetailVO> detailVoList = new LinkedList<>();


        for (ProcessDetailEntity one : detailList) {
            SelectProcessDetailVO vo = new SelectProcessDetailVO();
            vo.setSkuId(one.getSkuId());
            //未完成：根据 库存skuId 搜索到库存产品中文名
            vo.setProductName("库存产品中文名");
            vo.setTotalAmount(one.getTotalAmount());
            vo.setUnitNum(one.getUnitNum());
            vo.setWarehouse_id(one.getWarehouseId());
            vo.setBatch(one.getBatch());
            detailVoList.add(vo);
        }
        return detailVoList;
    }

    public String getProcessTaskBillId(String rediskey) {
        SimpleDateFormat sdf = new SimpleDateFormat("YY");
        String currDate = sdf.format(System.currentTimeMillis());
        String key = currDate + rediskey;
        int count = 0;
        long maxId = redisTemplate.opsForValue().increment(key, 1);
        if (maxId > 999) {
            throw new RuntimeException("订单号已达最大");
        }
        long num = maxId;
        while (num > 0) {
            num = num / 10;
            count++;
        }
        if (count == 1) {
            return "ASS" + currDate + "0000" + maxId;
        }
        if (count == 2) {
            return "ASS" + currDate + "000" + maxId;
        }
        if (count == 3) {
            return "ASS" + currDate + "00" + maxId;
        }
        if (count == 4) {
            return "ASS" + currDate + "0" + maxId;
        }
        if (count == 5) {
            return "ASS" + currDate + maxId;
        }


        return "ASS" + currDate + maxId;
    }

    @Override
    public List<ProcessTaskBillEntity> getStatusCdByfbaId(String fbaReplenishmentId) {
        return processTaskBillDao.getStatusCdByfbaId(fbaReplenishmentId);
    }
}
