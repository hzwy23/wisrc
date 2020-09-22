package com.wisrc.merchandise.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.merchandise.dao.BasicPlatformInfoDao;
import com.wisrc.merchandise.dto.WarehouseManageInfoDTO;
import com.wisrc.merchandise.entity.BasicPlatformInfoEntity;
import com.wisrc.merchandise.outside.WarehouseService;
import com.wisrc.merchandise.service.BasicPlatformInfoService;
import com.wisrc.merchandise.utils.PageData;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.vo.PlatformInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class BasicPlatformInfoServiceImpl implements BasicPlatformInfoService {

    @Autowired
    private BasicPlatformInfoDao basicPlatformInfoDao;

    @Autowired
    private WarehouseService warehouseService;

    @Override
    public LinkedHashMap findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BasicPlatformInfoEntity> list = basicPlatformInfoDao.findAll();
        PageInfo pageInfo = new PageInfo(list);
        List<PlatformInfoVO> result = new ArrayList<>();
        for (BasicPlatformInfoEntity m : list) {
            result.add(PlatformInfoVO.toVO(m));
        }
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "platformInfoList", result);
    }

    @Override
    public LinkedHashMap search(int pageNum, int pageSize, String platformName, String statusCd) {
        PageHelper.startPage(pageNum, pageSize);
        List<BasicPlatformInfoEntity> list = basicPlatformInfoDao.search(platformName, statusCd);
        PageInfo pageInfo = new PageInfo(list);
        List<PlatformInfoVO> result = new ArrayList<>();
        for (BasicPlatformInfoEntity m : list) {
            result.add(PlatformInfoVO.toVO(m));
        }
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "platformInfoList", result);
    }

    @Override
    public List<BasicPlatformInfoEntity> findAll() {
        return basicPlatformInfoDao.findAll();
    }

    @Override
    public BasicPlatformInfoEntity findById(String platId) {
        return basicPlatformInfoDao.findById(platId);
    }

    @Override
    public void updateById(BasicPlatformInfoEntity ele) throws DuplicateKeyException {
        BasicPlatformInfoEntity entity = basicPlatformInfoDao.getSite(ele.getPlatId());
        List<String> siteName = basicPlatformInfoDao.check(ele.getPlatName());
        if (entity.getPlatName().equals(ele.getPlatName()) && entity.getPlatSite().equals(ele.getPlatSite())) {
            basicPlatformInfoDao.update(ele);
        } else if (siteName.contains(ele.getPlatSite())) {
            throw new DuplicateKeyException("该平台下站点名称重复");
        } else {
            basicPlatformInfoDao.update(ele);
        }
    }

    @Override
    @Transactional(transactionManager = "retailMerchandiseTransactionManager")
    public Result delete(String platId) {
        basicPlatformInfoDao.delete(platId);
        // 获取这个平台对应的FBA仓库ID
        String fbaWarehouseId = basicPlatformInfoDao.getFbaWarehouse(platId);
        Result result = warehouseService.deleteWarehouse(fbaWarehouseId, 2);
        if (result.getCode() != 200) {
            return result;
        } else {
            return Result.success("删除平台站点，以及FBA补货仓成功");
        }
    }

    @Override
    @Transactional(transactionManager = "retailMerchandiseTransactionManager")
    @Deprecated
    public Result deleteList(List<String> list) {
        for (String id : list) {
            basicPlatformInfoDao.delete(id);
            // 获取这个平台对应的FBA仓库ID
            String fbaWarehouseId = basicPlatformInfoDao.getFbaWarehouse(id);
            Result result = warehouseService.deleteWarehouse(fbaWarehouseId, 2);
            if (result.getCode() != 200) {
                return result;
            } else {
                return Result.success("删除平台站点，以及FBA补货仓成功");
            }
        }
        return Result.success();
    }

    @Override
    public Result changeStatus(String platId, int statusCd) {
        // 首先删除失效的平台
        String fbaWarehouseId = basicPlatformInfoDao.getFbaWarehouse(platId);
        if (fbaWarehouseId != null) {
            Result result = warehouseService.deleteWarehouse(fbaWarehouseId, statusCd);
            if (result.getCode() != 200) {
                return result;
            }
        }
        basicPlatformInfoDao.changeStatus(platId, statusCd);
        return Result.success("删除平台站点，以及FBA补货仓成功");
    }

    @Override
    @Transactional(transactionManager = "retailMerchandiseTransactionManager")
    public Result add(BasicPlatformInfoEntity ele) {
        // 调用仓库接口，创建FBA仓
        try {
            Result result = addFbaWarehouse(ele);
            if (result.getCode() == 200) {
                String id = ((LinkedHashMap) result.getData()).get("warehouseId").toString();
                ele.setFbaWarehouseId(id);
                basicPlatformInfoDao.add(ele);
                return Result.success("新增平台站点成功");
            } else {
                return Result.failure(423, "创建默认FBA仓失败，请联系管理员", ele);
            }
        } catch (DuplicateKeyException e) {
            // 查询站点信息
            BasicPlatformInfoEntity row = basicPlatformInfoDao.find(ele.getPlatName(), ele.getPlatSite());
            if (row != null && row.getStatusCd() == 2) {
                return Result.success(423, "平台名和站点组合已经存在，且处于停用状态，无法重复创建", ele);
            }
            return Result.success(423, "平台名和站点组合已经存在，无法重复创建", ele);
        } catch (Exception e) {
            e.printStackTrace();
            basicPlatformInfoDao.delete(ele.getPlatId());
            return Result.failure(423, "创建默认FBA仓失败，请联系管理员", ele);
        }
    }

    @Override
    public List<BasicPlatformInfoEntity> findSiteById(String platName) {
        return basicPlatformInfoDao.findSiteById(platName);
    }

    private Result addFbaWarehouse(BasicPlatformInfoEntity ele) {
        WarehouseManageInfoDTO args = new WarehouseManageInfoDTO();
        args.setWarehouseName(ele.getPlatName() + "-" + ele.getPlatSite());
        args.setTypeCd("F");
        return warehouseService.addFbaWarehouse(args, ele.getModifyUser());
    }

}
