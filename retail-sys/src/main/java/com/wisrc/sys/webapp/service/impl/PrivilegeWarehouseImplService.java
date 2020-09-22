package com.wisrc.sys.webapp.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wisrc.sys.webapp.bo.WarehouseInfoBO;
import com.wisrc.sys.webapp.bo.WarehouseTypeBO;
import com.wisrc.sys.webapp.dao.PrivilegeWarehouseDao;
import com.wisrc.sys.webapp.entity.SysPrivilegeWarehouseEntity;
import com.wisrc.sys.webapp.service.proxy.OutWarehouseService;
import com.wisrc.sys.webapp.service.PrivilegeWarehouseService;
import com.wisrc.sys.webapp.utils.PageData;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.vo.WarehouseDataPrivilegesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PrivilegeWarehouseImplService implements PrivilegeWarehouseService {

    private final Logger logger = LoggerFactory.getLogger(PrivilegeWarehouseImplService.class);

    @Autowired
    private PrivilegeWarehouseDao privilegeWarehouseDao;
    @Autowired
    private OutWarehouseService outWarehouseService;

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
    public Result insert(List<SysPrivilegeWarehouseEntity> list) {
        List<SysPrivilegeWarehouseEntity> ret = new ArrayList<>();
        for (SysPrivilegeWarehouseEntity ele : list) {
            try {
                privilegeWarehouseDao.insert(ele);
            } catch (DuplicateKeyException e) {
                //重复授权就是更新创建人与创建时间
                privilegeWarehouseDao.updateOnly(ele);
                ret.add(ele);
            }
        }
        return Result.success(200, "添加仓库授权信息成功", ret);
    }

    @Override
    public Result deletePrivilegeWarehouse(SysPrivilegeWarehouseEntity entity) {
        privilegeWarehouseDao.deletePrivilegeWarehouse(entity);
        return Result.success();
    }

    @Override
    public LinkedHashMap getPrivilegeWarehouse(int pageNum, int pageSize, String authId, String type, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysPrivilegeWarehouseEntity> list = privilegeWarehouseDao.findAll(authId);
        PageInfo pageInfo = new PageInfo(list);

        String[] args = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            args[i] = list.get(i).getWarehouseCd();
        }

        // 获取仓库列表信息
        LinkedHashMap map = toMap(args);

        // 获取仓库类型信息
        LinkedHashMap typeMap = typeAttrMap();

        List<WarehouseDataPrivilegesVO> ret = new ArrayList<>();
        for (SysPrivilegeWarehouseEntity ele : list) {
            WarehouseDataPrivilegesVO one = new WarehouseDataPrivilegesVO();
            if (map != null && map.containsKey(ele.getWarehouseCd())) {
                WarehouseInfoBO wb = (WarehouseInfoBO) map.get(ele.getWarehouseCd());
                one.setWarehouseTypeCd(wb.getTypeCd());
                if (typeMap != null && typeMap.containsKey(wb.getTypeCd())) {
                    one.setWarehouseTypeName(((WarehouseTypeBO) typeMap.get(wb.getTypeCd())).getTypeDesc());
                    one.setWarehouseName(wb.getWarehouseName());
                }
            }
            BeanUtils.copyProperties(ele, one);
            ret.add(one);
        }
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "authRepositoryList", ret);
    }

    @Override
    public LinkedHashMap getPrivilegeWarehouseUnauth(int pageNum, int pageSize, String authId, String type, String keyWord) {
        try {
            Result result = outWarehouseService.getWarehouseFuzzy(1, type, keyWord);

            if (result.getCode() == 200) {
                LinkedHashMap mp = (LinkedHashMap) result.getData();

                List<WarehouseInfoBO> mlist = boToList((List<LinkedHashMap>) mp.get("warehouseManageInfoVOList"));

                if (mlist.size() == 0) {
                    return PageData.pack(-1, -1, "没有可以访问的仓库信息", null);
                }
                // 已经获取了的仓库信息
                List<SysPrivilegeWarehouseEntity> getList = privilegeWarehouseDao.findAll(authId);
                Set<String> set = toSet(getList);
                List<WarehouseInfoBO> list = new ArrayList<>();
                for (int i = 0; i < mlist.size(); i++) {
                    if (!set.contains(mlist.get(i).getWarehouseId())) {
                        list.add(mlist.get(i));
                    }
                }

                if (list.size() == 0) {
                    return PageData.pack(-1, -1, "没有可以访问的仓库信息", null);
                }

                if (pageNum > 0) {
                    pageNum -= 1;
                }
                int pages = (list.size()) % (pageSize) > 0 ? (list.size()) / (pageSize) + 1 : (list.size()) / (pageSize);
                int startIndex = pageNum * pageSize;
                if (startIndex >= list.size()) {
                    startIndex = 0;
                }
                int endIndex = startIndex + pageSize;
                if (endIndex > list.size()) {
                    endIndex = list.size();
                }
                // 没有获取到的仓库信息
                return PageData.pack(list.size(), pages, "unAuthRepositoryList", list.subList(startIndex, endIndex));
            } else {
                return PageData.pack(-1, -1, "unAuthRepositoryList", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return PageData.pack(-1, -1, "unAuthRepositoryList", null);
        }
    }

    private LinkedHashMap toMap(String[] args) {
        Result result = outWarehouseService.getWarehouseDetails(args);
        if (result.getCode() == 200) {
            return boToMap((List) result.getData());
        }
        return null;
    }

    private LinkedHashMap boToMap(List<LinkedHashMap> warehouseInfoBOList) {
        LinkedHashMap map = new LinkedHashMap();
        for (LinkedHashMap ele : warehouseInfoBOList) {
            WarehouseInfoBO bo = new WarehouseInfoBO();
            bo.setWarehouseId(ele.get("warehouseId").toString());
            try {
                bo.setTypeCd(ele.get("typeCd").toString());
            } catch (NumberFormatException e) {
                bo.setTypeCd("");
            }
            bo.setWarehouseName(ele.get("warehouseName").toString());
            map.put(bo.getWarehouseId(), bo);
        }
        return map;
    }

    private List boToList(List<LinkedHashMap> warehouseInfoBOList) {
        List<WarehouseInfoBO> list = new ArrayList<>();
        for (LinkedHashMap ele : warehouseInfoBOList) {
            WarehouseInfoBO bo = new WarehouseInfoBO();
            bo.setWarehouseId(ele.get("warehouseId").toString());

            if (ele.containsKey("typeCd") && ele.get("typeCd") != null) {
                bo.setTypeCd(ele.get("typeCd").toString());
            } else {
                bo.setTypeCd("");
            }

            if (ele.containsKey("statusCd") && ele.get("statusCd") != null) {
                bo.setStatusCd(Integer.parseInt(ele.get("statusCd").toString()));
            } else {
                bo.setStatusCd(0);
            }

            if (ele.containsKey("warehouseName") && ele.get("warehouseName") != null) {
                bo.setWarehouseName(ele.get("warehouseName").toString());
            }
            list.add(bo);
        }
        return list;
    }

    private Set toSet(List<SysPrivilegeWarehouseEntity> getList) {
        HashSet<String> set = new HashSet<>();
        for (SysPrivilegeWarehouseEntity ele : getList) {
            set.add(ele.getWarehouseCd());
        }
        return set;
    }

    private LinkedHashMap<String, WarehouseTypeBO> typeAttrMap() {
        Result result = outWarehouseService.getWarehouseType();
        if (result.getCode() == 200) {
            Gson gson = new Gson();
            List<WarehouseTypeBO> list = gson.fromJson(result.getData().toString(), new TypeToken<List<WarehouseTypeBO>>() {
            }.getType());
            LinkedHashMap map = new LinkedHashMap();
            for (WarehouseTypeBO ele : list) {
                map.put(ele.getTypeCd(), ele);
            }
            return map;
        }
        return null;
    }
}


