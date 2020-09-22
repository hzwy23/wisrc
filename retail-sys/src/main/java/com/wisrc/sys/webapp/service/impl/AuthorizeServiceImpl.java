package com.wisrc.sys.webapp.service.impl;

import com.wisrc.sys.webapp.dao.SysRolePrivilegeDao;
import com.wisrc.sys.webapp.dao.SysRoleUserDao;
import com.wisrc.sys.webapp.entity.SysPrivilegesInfoEntity;
import com.wisrc.sys.webapp.entity.SysRolePrivilegeEntity;
import com.wisrc.sys.webapp.entity.SysRoleUserEntity;
import com.wisrc.sys.webapp.service.AuthorizeService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.Toolbox;
import com.wisrc.sys.webapp.vo.authorize.AuthorizeUserPrivilegeVo;
import com.wisrc.sys.webapp.vo.authorize.AuthorizeUserRoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    private final Logger logger = LoggerFactory.getLogger(AuthorizeServiceImpl.class);

    @Autowired
    private SysRoleUserDao sysRoleUserDao;
    @Autowired
    private SysRolePrivilegeDao sysRolePrivilegeDao;

    @Override
    public Result editRoleUser(AuthorizeUserRoleVo authorizeUserRoleVo, String account, String userId) {
        List errRoleSave = new ArrayList();
        List errRoleDelete = new ArrayList();
        List<String> roleIdsAuthority;

        try {
            roleIdsAuthority = sysRoleUserDao.getRoleIdByUserId(account);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        List<String> roleIds = authorizeUserRoleVo.getRoleIds();
        for (String roleId : roleIds) {
            int index = roleIdsAuthority.indexOf(roleId);
            if (index == -1) {
                SysRoleUserEntity sysRoleUserEntity = new SysRoleUserEntity();
                sysRoleUserEntity.setUuid(UUID.randomUUID().toString().replace("-", "").toLowerCase());
                sysRoleUserEntity.setRoleId(roleId);
                sysRoleUserEntity.setUserId(account);
                sysRoleUserEntity.setCreateTime(new Timestamp(new Date().getTime()));
                sysRoleUserEntity.setCreateUser(userId);
                try {
                    sysRoleUserDao.saveSysRoleUser(sysRoleUserEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    errRoleSave.add(roleId);
                }
            } else {
                roleIdsAuthority.remove(index);
            }
        }

        if (roleIdsAuthority.size() > 0) {
            try {
                sysRoleUserDao.deleteSysRoleUserByRoleIds(account, roleIdsAuthority);
            } catch (Exception e) {
                e.printStackTrace();
                errRoleDelete = roleIdsAuthority;
            }

            if (errRoleSave.size() > 0 || errRoleDelete.size() > 0) {
                return Result.failure(400, "授权保存出错名单：" + errRoleSave.toString() + "\t" + "授权取消出错名单：" + errRoleDelete.toString(), "保存授权出现错误");
            }
        }

        return Result.success();
    }

    /**
     * modify by zhanwei_huang
     * 角色与权限代码
     */
    @Override
    public Result editRolePrivilege(AuthorizeUserPrivilegeVo authorizeUserPrivilegeVo, String roleId) {
        List errPrivilegeSave = new ArrayList();
        List errPrivilegeDelete = new ArrayList();
        List<String> privilegeIdsAuthority;

        // 获取已经授权的权限代码
        try {
            privilegeIdsAuthority = sysRolePrivilegeDao.getPrivilegeIdsByUserId(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        List<String> privilegeIds = authorizeUserPrivilegeVo.getPrivilegeIds();
        for (String privilegeId : privilegeIds) {
            int index = privilegeIdsAuthority.indexOf(privilegeId);
            if (index == -1) {
                SysRolePrivilegeEntity sysRolePrivilegeEntity = new SysRolePrivilegeEntity();
                sysRolePrivilegeEntity.setUuid(Toolbox.UUIDrandom());
                sysRolePrivilegeEntity.setPrivilegeCd(privilegeId);
                sysRolePrivilegeEntity.setRoleId(roleId);
                try {
                    sysRolePrivilegeDao.saveSysUserPrivilege(sysRolePrivilegeEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    errPrivilegeSave.add(privilegeId);
                }
            } else {
                privilegeIdsAuthority.remove(index);
            }
        }

        if (privilegeIdsAuthority.size() > 0) {
            try {
                sysRolePrivilegeDao.deleteSysRoleMenusByMenuIds(roleId, privilegeIdsAuthority);
            } catch (Exception e) {
                e.printStackTrace();
                errPrivilegeDelete = privilegeIdsAuthority;
            }

            if (errPrivilegeSave.size() > 0 || errPrivilegeDelete.size() > 0) {
                return Result.failure(400, "授权保存出错名单：" + errPrivilegeSave.toString() + "\t" + "授权取消出错名单：" + errPrivilegeDelete.toString(), "保存授权出现错误");
            }
        }

        return Result.success();
    }

    @Override
    public Result getRolePrivilege(String account) {
        Map result = new HashMap();
        List<SysPrivilegesInfoEntity> privilegeFind = new ArrayList<>();
        List userPrivilegeList = new ArrayList();

        try {
            privilegeFind = sysRolePrivilegeDao.getSysUserPrivilegeByUserId(account);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (SysPrivilegesInfoEntity privilege : privilegeFind) {
            Map privilegeMap = new HashMap();
            privilegeMap.put("id", privilege.getPrivilegeCd());
            privilegeMap.put("name", privilege.getPrivilegeName());

            userPrivilegeList.add(privilegeMap);
        }

        result.put("userPrivilegeList", userPrivilegeList);
        return Result.success(result);
    }
}
