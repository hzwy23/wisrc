package com.wisrc.warehouse.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.dao.WarehouseManageInfoDao;
import com.wisrc.warehouse.webapp.dao.WarehouseSeparateDetailsInfoDao;
import com.wisrc.warehouse.webapp.entity.WarehouseManageInfoEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseSeparateDetailsInfoEntity;
import com.wisrc.warehouse.webapp.service.StockService;
import com.wisrc.warehouse.webapp.service.WarehouseManageInfoService;
import com.wisrc.warehouse.webapp.utils.PageData;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.Time;
import com.wisrc.warehouse.webapp.vo.WarehouseManageInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class WarehouseManageInfoServiceImpl implements WarehouseManageInfoService {
    private final String url = "http://test.topwms.cn:8082/zgjk/wisen/api/service?access_token=123";
    private final String format = "json";
    @Autowired
    private WarehouseManageInfoDao warehouseManageInfoDao;

    @Autowired
    private StockService stockService;

    @Autowired
    private WarehouseSeparateDetailsInfoDao warehouseSeparateDetailsInfoDao;

    @Override
    public LinkedHashMap findAll(int pageNum, int pageSize, int statusCd, String typeCd, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        List<WarehouseManageInfoEntity> list = warehouseManageInfoDao.search(statusCd, typeCd, keyWord);
        handleSubWarehouseList(list);
        PageInfo pageInfo = new PageInfo(list);
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "warehouseManageInfoVOList", list);
    }

    @Override
    public LinkedHashMap findAll(int statusCd, String typeCd, String keyWord) {
        List<WarehouseManageInfoEntity> list = warehouseManageInfoDao.search(statusCd, typeCd, keyWord);
        handleSubWarehouseList(list);
        return PageData.pack(list.size(), 1, "warehouseManageInfoVOList", list);
    }

    @Override
    public LinkedHashMap findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WarehouseManageInfoEntity> list = warehouseManageInfoDao.findAll();
        handleSubWarehouseList(list);
        PageInfo pageInfo = new PageInfo(list);
        List<WarehouseManageInfoVO> result = new ArrayList<>();
        for (WarehouseManageInfoEntity m : list) {
            result.add(WarehouseManageInfoVO.toVO(m));
        }
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "warehouseManageInfoVOList", result);
    }

    private void handleSubWarehouseList(List<WarehouseManageInfoEntity> list) {
        if (list == null) {
            return;
        }
        for (WarehouseManageInfoEntity warehouseManageInfoEntity : list) {
            if (warehouseManageInfoEntity.getSubWarehouseSupport() == 1) {
                warehouseManageInfoEntity
                        .setSubWarehouseList(warehouseSeparateDetailsInfoDao.findByWareHouseId(
                                warehouseManageInfoEntity.getWarehouseId()));
            }
        }

    }

    @Override
    public LinkedHashMap findAll() {
        List<WarehouseManageInfoEntity> list = warehouseManageInfoDao.findAll();
        return PageData.pack(list.size(), 1, "warehouseManageInfoVOList", list);
    }

    /**
     * 仓库类型不能被修改，
     * 是否分仓也不能被修改
     */
    @Override
    @Transactional(transactionManager = "retailWarehouseTransactionManager")
    public Result changeName(WarehouseManageInfoEntity ele) {
        // 更新主库信息
        warehouseManageInfoDao.changeName(ele);

        // 更新分库信息
        String data = "";
        // 判断仓库是否改变分仓标示，如果变更为分仓，则需要添加分仓信息
        WarehouseManageInfoEntity details = warehouseManageInfoDao.findById(ele.getWarehouseId());
        if (details.getSubWarehouseSupport() == 1) {
            // 判断是否变更非分仓
            if (ele.getSubWarehouseSupport() == 1) {
                List<WarehouseSeparateDetailsInfoEntity> owner = warehouseSeparateDetailsInfoDao.findByWareHouseId(ele.getWarehouseId());
                Map<String, WarehouseSeparateDetailsInfoEntity> ownerKeys = toMap(owner);
                Map<String, WarehouseSeparateDetailsInfoEntity> subList = toMap(ele.getSubWarehouseList());

                WarehouseSeparateDetailsInfoEntity mod = new WarehouseSeparateDetailsInfoEntity();

                int idx = 1;
                if (owner.size() != 0) {
                    try {
                        String tmp = owner.get(0).getSubWarehouseId();
                        idx = Integer.parseInt(tmp.substring(ele.getWarehouseId().length() + 1)) + 1;
                    } catch (Exception e) {
                        idx = 188;
                        e.printStackTrace();
                    }
                }

                // 如果分仓编码为null，或者为空，则表示新增
                for (WarehouseSeparateDetailsInfoEntity sub : ele.getSubWarehouseList()) {
                    // 如果分库ID为空或null，表示新增
                    if (sub.getSubWarehouseId() == null || sub.getSubWarehouseId().isEmpty()) {
                        mod.setDeleteStatus(0);
                        mod.setSeparateWarehouseName(sub.getSeparateWarehouseName());
                        mod.setWarehouseId(ele.getWarehouseId());
                        mod.setCreateTime(Time.getCurrentTime());
                        mod.setCreateUser(ele.getModifyUser());
                        mod.setModifyUser(ele.getModifyUser());
                        mod.setModifyTime(Time.getCurrentTime());
                        if (idx < 10) {
                            mod.setSubWarehouseId(ele.getWarehouseId() + "-0" + idx++);
                        } else {
                            mod.setSubWarehouseId(ele.getWarehouseId() + "-" + idx++);
                        }
                        warehouseSeparateDetailsInfoDao.insert(mod);
                    } else if (ownerKeys.containsKey(sub.getSubWarehouseId())) {
                        // 编辑
                        mod.setModifyTime(Time.getCurrentTime());
                        mod.setModifyUser(ele.getModifyUser());
                        mod.setSubWarehouseId(sub.getSubWarehouseId());
                        mod.setSeparateWarehouseName(sub.getSeparateWarehouseName());
                        warehouseSeparateDetailsInfoDao.update(mod);
                    }
                }

                // 删除分库信息
                for (String e : ownerKeys.keySet()) {
                    if (!subList.containsKey(e)) {
                        // 删除分库名称
                        // 如果提交过来的分库ID不在现有的
                        if (!stockService.hasSkuBySubwarehouseId(e)) {
                            mod.setSubWarehouseId(e);
                            mod.setModifyTime(Time.getCurrentTime());
                            mod.setModifyUser(ele.getModifyUser());
                            mod.setDeleteStatus(1);
                            warehouseSeparateDetailsInfoDao.changeDeleteStauts(mod);
                        } else {
                            data += "【" + e + "】存在库存信息，不能被删除。";
                        }
                    }
                }
            }
        }
        return Result.success(200, "更新仓库信息成功", ele);
    }

    /**
     * 获取制定仓库下所有的分库集合
     */
    private Map<String, WarehouseSeparateDetailsInfoEntity> toMap(List<WarehouseSeparateDetailsInfoEntity> subList) {
        Map<String, WarehouseSeparateDetailsInfoEntity> subKeys = new HashMap<>();
        for (WarehouseSeparateDetailsInfoEntity e : subList) {
            subKeys.put(e.getSubWarehouseId(), e);
        }
        return subKeys;
    }

    @Override
    public void changeStatus(String warehouseId, int statusCd, String createUser, Timestamp createTime) {
        warehouseManageInfoDao.changeStatus(warehouseId, statusCd, createUser, createTime);
    }

    @Override
    @Transactional(transactionManager = "retailWarehouseTransactionManager")
    public Result add(WarehouseManageInfoEntity ele) {
        // 首先获取序列号
        String uuid = UUID.randomUUID().toString().replace("-", "");
        ele.setWarehouseId(uuid);
        //根据新增仓库名查询数据库是否存在正常状态同名仓库

        warehouseManageInfoDao.add(ele);
        // 拼接成warehouseId
        // 读取这个仓库的序列号
        String newWarehouseId = warehouseManageInfoDao.findIdByRandomValue(uuid);
        ele.setWarehouseId(newWarehouseId);

        // 判断仓库是否支持分仓
        if (ele.getSubWarehouseSupport() == 1) {
            // 支持分仓
            for (WarehouseSeparateDetailsInfoEntity e : ele.getSubWarehouseList()) {
                String randomValue = UUID.randomUUID().toString().replace("-", "");
                e.setCreateTime(Time.getCurrentTime());
                e.setModifyTime(Time.getCurrentTime());
                e.setModifyUser(ele.getCreateUser());
                e.setCreateUser(ele.getCreateUser());
                e.setWarehouseId(newWarehouseId);
                e.setSubWarehouseId(null);
                e.setRandomValue(randomValue);
                e.setDeleteStatus(0);
                warehouseSeparateDetailsInfoDao.insert(e);
                // 获取新增的分仓ID
                String subId = warehouseSeparateDetailsInfoDao.findIdByRandomValue(randomValue);
                e.setSubWarehouseId(subId);
            }
        } else {
            // 不分仓时，主仓库编码与分仓库编码相同
            WarehouseSeparateDetailsInfoEntity e = new WarehouseSeparateDetailsInfoEntity();
            String randomValue = UUID.randomUUID().toString().replace("-", "");
            e.setCreateTime(Time.getCurrentTime());
            e.setModifyTime(Time.getCurrentTime());
            e.setModifyUser(ele.getCreateUser());
            e.setCreateUser(ele.getCreateUser());
            e.setRandomValue(randomValue);
            e.setDeleteStatus(0);
            e.setWarehouseId(newWarehouseId);
            e.setSubWarehouseId(newWarehouseId);
            e.setSeparateWarehouseName(ele.getWarehouseName());
            warehouseSeparateDetailsInfoDao.insert(e);
        }
        return Result.success(ele);
    }

    @Override
    public WarehouseManageInfoEntity findById(String warehouseId) {
        WarehouseManageInfoEntity warehouseManageInfoEntity = warehouseManageInfoDao.findById(warehouseId);
        if (warehouseManageInfoEntity != null && warehouseManageInfoEntity.getSubWarehouseSupport() == 1) {
            warehouseManageInfoEntity.setSubWarehouseList(warehouseSeparateDetailsInfoDao.findByWareHouseId(warehouseManageInfoEntity.getWarehouseId()));
        }
        return warehouseManageInfoEntity;
    }

    @Override
    public List<WarehouseManageInfoEntity> findInfoList(String warehouseId) {
        String[] ids = warehouseId.split(",");
        String str = "";
        String endstr = "";
        for (int i = 0; i < ids.length; i++) {
            str = "'" + ids[i] + "'";
            if (i < ids.length - 1) {
                str += ",";
            }
            endstr += str;
        }
        List<WarehouseManageInfoEntity> list = warehouseManageInfoDao.findByIdList(endstr);
        return list;
    }

    @Override
    public List<WarehouseSeparateDetailsInfoEntity> findSubWarehouseInfo(String warehouseId) {
        return warehouseSeparateDetailsInfoDao.findByIdExceptDelete(warehouseId);
    }

    @Override
    public void addSubwarehouse(WarehouseSeparateDetailsInfoEntity ele) {
        // 获取这个仓库最大的分仓编码
        ele.setDeleteStatus(0);
        ele.setModifyTime(Time.getCurrentTime());
        ele.setCreateTime(Time.getCurrentTime());
        warehouseSeparateDetailsInfoDao.insert(ele);
    }

    @Override
    public Result updateSubwarehouse(WarehouseSeparateDetailsInfoEntity ele) {
        WarehouseManageInfoEntity info = warehouseManageInfoDao.findById(ele.getWarehouseId());
        if (info.getSubWarehouseSupport() == 0) {
            return Result.failure(423, "主仓库【" + ele.getWarehouseId() + "】不支持分仓", ele);
        }
        try {
            warehouseSeparateDetailsInfoDao.update(ele);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(423, "更新分仓信息失败，请联系管理员", ele);
        }
    }

    @Override
    @Transactional(transactionManager = "retailWarehouseTransactionManager")
    public Result deleteSubwarehouse(WarehouseSeparateDetailsInfoEntity ele) {
        //查询分仓信息 这里的ele 中的WarehouseId并没有赋值
        WarehouseSeparateDetailsInfoEntity warehouseSeparateDetailsInfoEntity = warehouseSeparateDetailsInfoDao.findBySeparateWareHouseId(ele.getSubWarehouseId());
        //deleteStatus 为1 时为删除状态
        if (warehouseSeparateDetailsInfoEntity == null || warehouseSeparateDetailsInfoEntity.getDeleteStatus() == 1) {
            return Result.failure(423, "不存在的分仓记录，可能已被删除，请重试", ele);
        }
        WarehouseManageInfoEntity warehouseManageInfoEntity = warehouseManageInfoDao.findById(warehouseSeparateDetailsInfoEntity.getWarehouseId());
        if (warehouseManageInfoEntity.getSubWarehouseSupport() == 1) {
            List<WarehouseSeparateDetailsInfoEntity> warehouseSeparateDetailsInfoEntities = warehouseSeparateDetailsInfoDao.findByWareHouseId(
                    warehouseManageInfoEntity.getWarehouseId());
            if (warehouseSeparateDetailsInfoEntities.stream().filter(w -> 0 == w.getDeleteStatus()).count() <= 1) {
                return Result.failure(423, "请至少保留一条分仓记录", ele);
            }
        }
        boolean hasSku = stockService.hasSkuBySubwarehouseId(ele.getSubWarehouseId());
        if (hasSku) {
            return Result.failure(423, "【" + ele.getSubWarehouseId() + "】目前仍然存在库存，不允许删除", ele);
        }
        ele.setDeleteStatus(1);
        try {
            warehouseSeparateDetailsInfoDao.changeDeleteStauts(ele);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(423, "删除分仓【" + ele.getSubWarehouseId() + "】失败，请联系管理员", ele);
        }
    }

    @Override
    public LinkedHashMap findAllNotFba() {
        List<WarehouseManageInfoEntity> list = warehouseManageInfoDao.findAllNotFba();
        return PageData.pack(list.size(), 1, "warehouseManageInfoVOList", list);
    }
}
