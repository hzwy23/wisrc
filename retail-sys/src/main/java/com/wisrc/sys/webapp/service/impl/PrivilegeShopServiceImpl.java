package com.wisrc.sys.webapp.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wisrc.sys.webapp.service.proxy.MskuService;
import com.wisrc.sys.webapp.bo.MskuDTO;
import com.wisrc.sys.webapp.dao.EmployeeInfoDao;
import com.wisrc.sys.webapp.dao.PrivilegeShopDao;
import com.wisrc.sys.webapp.dao.SysUserInfoDao;
import com.wisrc.sys.webapp.dao.VUserCommodityPrivilegeDao;
import com.wisrc.sys.webapp.entity.GatherEntity;
import com.wisrc.sys.webapp.entity.SysPrivilegeShopEntity;
import com.wisrc.sys.webapp.service.PrivilegeShopService;
import com.wisrc.sys.webapp.utils.ArrayToInArguments;
import com.wisrc.sys.webapp.utils.PageData;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.vo.user.UserDetailsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Primary
public class PrivilegeShopServiceImpl implements PrivilegeShopService {
    private final Logger logger = LoggerFactory.getLogger(PrivilegeShopServiceImpl.class);

    @Autowired
    private PrivilegeShopDao storesDao;
    @Autowired
    private MskuService outOperationService;

    @Autowired
    private EmployeeInfoDao employeeInfoDao;

    @Autowired
    private MskuService mskuService;

    @Autowired
    private VUserCommodityPrivilegeDao vUserCommodityPrivilegeDao;

    @Autowired
    private SysUserInfoDao sysUserInfoDao;

    /**
     * Date转Sring
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    @Override
    public Result getPrivilegeShop(int pageNum, int pageSize,
                                   String authId, String platformName,
                                   String storeName, String mskuId) {
        try {
            // 获取授权的msku信息
            List<GatherEntity> storeList = storesDao.getPrivilegeShop(authId);

            // 获取msku详细信息
            Map<String, MskuDTO> ret = findMsku(storeList, platformName, storeName, mskuId);

            List<Object> list = new ArrayList<>();

            for (GatherEntity obj : storeList) {
                Map<String, Object> map = new HashMap<>();
                if (ret.containsKey(obj.getCommodityId())) {
                    MskuDTO mskuDTO = ret.get(obj.getCommodityId());
                    map.put("mskuId", mskuDTO.getMskuId());
                    map.put("mskuName", mskuDTO.getMskuName());
                    map.put("storeId", mskuDTO.getShopId());
                    map.put("storeName", mskuDTO.getShopName());
                    map.put("platform", mskuDTO.getPlatformName());
                    map.put("platformCd", mskuDTO.getPlatformCd());
                    map.put("authUser", obj.getUserName());
                    map.put("authTime", getStringDate(obj.getCreateTime()));
                    map.put("commodityId", obj.getCommodityId());
                    list.add(map);
                }
            }

            //根据前端打包VO分页
            int total = list.size();
            int pages = (total) % (pageSize) > 0 ? (total) / (pageSize) + 1 : (total) / (pageSize);
            int start = (pageNum - 1) * (pageSize);
            int end = (pageNum) * (pageSize) - 1;
            if (end > (total - 1)) {
                end = total - 1;
            }
            List<Object> newList = new ArrayList<>();
            if (start == end) {
                newList.add(list.get(start));
            } else {
                newList = list.subList(start, (end + 1));
            }
            return Result.success(PageData.pack(total, pages, "authStoreList", newList));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(9999, "查询MSKU授权信息失败，请联系管理员", e.getMessage());
        }
    }


    /**
     * 获取所有的商品信息
     */
    private Map<String, MskuDTO> findMsku(List<GatherEntity> storeList, String platformName, String storeName, String mskuId) {
        Map<String, MskuDTO> ret = new HashMap<>();
        if (storeList == null || storeList.size() == 0) {
            return null;
        }
        String[] ids = new String[storeList.size()];
        for (int i = 0; i < storeList.size(); i++) {
            ids[i] = storeList.get(i).getCommodityId();
        }

        Result result = mskuService.findMskuInfoByIds(ids);
        if (result != null && result.getCode() == 200) {
            // 请求成功
            Gson gson = new Gson();
            Object data = result.getData();
            if (data != null) {
                LinkedHashMap map = ((LinkedHashMap) data);
                JSONObject mp = JSONUtil.parseObj(map);
                List<MskuDTO> list = gson.fromJson(mp.getStr("mskuInfoBatch"), new TypeToken<List<MskuDTO>>() {
                }.getType());
                for (MskuDTO ele : list) {
                    if (platformName != null && !platformName.isEmpty() && (ele.getPlatformName() == null || (ele.getPlatformName() != null && ele.getPlatformName().indexOf(platformName) < 0))) {
                        continue;
                    }
                    if (storeName != null && !storeName.isEmpty() && (ele.getShopName() == null || (ele.getShopName() != null && ele.getShopName().indexOf(storeName) < 0))) {
                        continue;
                    }
                    if (mskuId != null && !mskuId.isEmpty() && (ele.getMskuId() == null || (ele.getMskuId() != null && ele.getMskuId().indexOf(mskuId) < 0))) {
                        continue;
                    }
                    ret.put(ele.getId(), ele);
                }
                return ret;
            }
        }
        return null;
    }

    @Override
    public Result insert(List<SysPrivilegeShopEntity> list) {
        List<SysPrivilegeShopEntity> ret = new ArrayList<>();
        for (SysPrivilegeShopEntity entity : list) {
            try {
                storesDao.insert(entity);
            } catch (DuplicateKeyException e) {
                //重复授权就是更新创建人与创建时间
                storesDao.update(entity);
                ret.add(entity);
            }
        }
        return Result.success(200, "添加店铺授权信息成功", ret);
    }

    @Override
    public Result deletePrivilegeShop(String commodityId, String privilegeCd) {
        Map<String, String> map = new HashMap<>();
        map.put("commodityId", commodityId);
        map.put("privilegeCd", privilegeCd);
        storesDao.deletePrivilegeShop(map);
        return Result.success();
    }

    //模糊分页查找获取未授权店铺
    @Override
    public Result getPrivilegeShopUnauth(int pageNum, int pageSize, String authId, String platformName, String storeName, String mskuId) {
        //拥有该授权的店铺集合
        List<GatherEntity> storeList = storesDao.getPrivilegeShop(authId);
        String[] idsList = null;
        if (storeList != null && storeList.size() > 0) {
            idsList = new String[storeList.size()];
            for (int i = 0; i < storeList.size(); i++) {
                idsList[i] = storeList.get(i).getCommodityId();
            }
        }
        try {
            return mskuService.searchMskuInfo(pageNum, pageSize, platformName, storeName, mskuId, idsList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(423, "查询未授权的商品信息失败，请联系管理员", null);
        }
    }

    @Override
    public Result searchUserCommodity(String userId,
                                      String[] commodityIdList,
                                      String[] roleIdList,
                                      String[] privilegeCdList,
                                      String deptCd,
                                      String[] positionCdList,
                                      String[] employeeIdList) {
        // 1. 判断使用为运营专员，如果是运营专员，就直接返回他负责的msku并加上给他另外授权的msku
        // 2. 如果是类目主管，则返回类目主管下运营专员负责的msku和他自己负责的msku，并且加上另外给他授权的msku
        // 3. 如果不是以上两类角色，则直接返回不用过滤权限
        UserDetailsVO userDetailsVO = sysUserInfoDao.findById(userId);
        // 获取账号的员工ID
        if (userDetailsVO == null || userDetailsVO.getEmployeeId() == null) {
            return null;
        }
        Set<String> owner = new HashSet<>();
        // 获取员工是否为主管
        if (userDetailsVO.getExecutiveDirectorAttr() == 1) {
            // 类目主管
            owner = storesDao.getUserChargeMsku(userDetailsVO.getEmployeeId());
            // 获取这个类目主管下边的运营专员员工号
            String[] employeeList = employeeInfoDao.getEployee(userDetailsVO.getEmployeeId());
            if (employeeList != null) {
                // 获取这个用户能够访问到的所有MSKu信息
                for (String eid : employeeList) {
                    Set<String> o = storesDao.getUserChargeMsku(eid);
                    if (o != null && o.size() > 0) {
                        owner.addAll(o);
                    }
                    Set<String> oo = vUserCommodityPrivilegeDao.getEmployeeAuth(eid);
                    if (oo != null && oo.size() > 0) {
                        owner.addAll(oo);
                    }
                }
            }
            // 获取下级用户授权的msku
        } else if (userDetailsVO.getExecutiveDirectorAttr() == 2) {
            // 运营专员，获取自己负责的MSKu消息
            owner = storesDao.getUserChargeMsku(userDetailsVO.getEmployeeId());
        } else {
            // 其他人员，获取全部的MSKU信息，不用合并
            owner = storesDao.getAllMsku();
            return Result.success(owner);
        }

        // 获取另行授权的MSKU信息
        try {
            Set<String> ret = vUserCommodityPrivilegeDao.findUserCommodityPrivilege(userId,
                    ArrayToInArguments.toInArgs(commodityIdList),
                    ArrayToInArguments.toInArgs(roleIdList),
                    ArrayToInArguments.toInArgs(privilegeCdList),
                    deptCd,
                    ArrayToInArguments.toInArgs(positionCdList),
                    ArrayToInArguments.toInArgs(employeeIdList));
            ret.addAll(owner);
            return Result.success(ret);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
