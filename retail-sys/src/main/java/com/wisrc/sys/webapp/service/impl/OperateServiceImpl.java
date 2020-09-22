package com.wisrc.sys.webapp.service.impl;

import com.wisrc.sys.webapp.dao.OperateDao;
import com.wisrc.sys.webapp.dao.VSysUserMenuDao;
import com.wisrc.sys.webapp.entity.SysResourceInfoEntity;
import com.wisrc.sys.webapp.entity.SysRoleMenusEntity;
import com.wisrc.sys.webapp.entity.VSysMenuUserEntity;
import com.wisrc.sys.webapp.service.OperateService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OperateServiceImpl implements OperateService {
    private final Logger logger = LoggerFactory.getLogger(OperateServiceImpl.class);

    // 防止遍历资源树时，垂岸了循环循环情况，最大深度为5，即最多支持五级菜单
    // 后续可以根据具体需求，调整这个值的大小
    private final int MAX_DEPT = 6;

    @Autowired
    private OperateDao operateDao;

    @Autowired
    private VSysUserMenuDao vSysUserMenuDao;


    @Override
    public Result getOperateList(String userId, String roleId, String menuId) {
        try {
            //取出菜单表中menuId菜单下操作menuType为4的操作菜单
            List<SysResourceInfoEntity> resource = operateDao.getPageHandle(menuId);

            //二 查出该角色拥有的菜单
            SysRoleMenusEntity sysRoleMenusEntity = new SysRoleMenusEntity();
            sysRoleMenusEntity.setRoleId(roleId);
            sysRoleMenusEntity.setMenuId(menuId);
            List<SysRoleMenusEntity> roleMenusList = operateDao.getRoleMenusList(sysRoleMenusEntity);

            List<Map> operateList = new ArrayList<>();
            Set<String> subSet = new HashSet();
            Set<String> choseSet = new HashSet();
            Set<String> tempSet = new HashSet();
            for (SysResourceInfoEntity o : resource) {
                Map map = new HashMap();
                map.put("value", o.getMenuId());
                map.put("label", o.getMetaTitle());
                operateList.add(map);
                subSet.add(o.getMenuId());
            }
            for (SysRoleMenusEntity o : roleMenusList) {
                choseSet.add(o.getMenuId());
            }
            //取出交集
            tempSet.clear();
            tempSet.addAll(subSet);
            tempSet.retainAll(choseSet);

            List<String> chosenList = new ArrayList<>();
            for (String o : tempSet) {
                for (SysRoleMenusEntity ol : roleMenusList) {
                    if (ol.getMenuId().equals(o)) {
                        chosenList.add(ol.getMenuId());
                    }
                }
            }
            Map<String, Object> resultMap = new HashMap();
            resultMap.put("operateList", operateList);
            resultMap.put("chosenList", chosenList);
            return Result.success(resultMap);

        } catch (Exception e) {
            logger.error("角色编码和菜单编码获取操作权限出错！", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @Override
    public List<VSysMenuUserEntity> getOperationAuth(String userId, String menuId, Integer methodCd, String path, Integer menuType) {
        return vSysUserMenuDao.getAuth(userId, menuId, methodCd, path, menuType);
    }
}
