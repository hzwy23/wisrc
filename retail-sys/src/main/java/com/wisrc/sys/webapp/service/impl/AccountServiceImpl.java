package com.wisrc.sys.webapp.service.impl;

import com.wisrc.sys.webapp.dao.SysRoleUserDao;
import com.wisrc.sys.webapp.dao.SysSecUserDao;
import com.wisrc.sys.webapp.dao.SysUserInfoDao;
import com.wisrc.sys.webapp.entity.SysRoleUserEntity;
import com.wisrc.sys.webapp.entity.SysSecUserEntity;
import com.wisrc.sys.webapp.entity.SysUserInfoEntity;
import com.wisrc.sys.webapp.service.AccountService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.vo.user.AccountSaveVo;
import com.wisrc.sys.webapp.vo.user.AccountVo;
import com.wisrc.sys.webapp.vo.user.SecUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private SysUserInfoDao sysUserInfoDao;

    @Autowired
    private SysSecUserDao sysSecUserDao;

    @Autowired
    private SysRoleUserDao sysRoleUserDao;

    @Override
    public Result getUsers(String userFindKey, Integer currentPage, Integer pageSize) {
        Map result = new HashMap<>();
        List users = new ArrayList();
        List<SysUserInfoEntity> userList = null;
        Integer count = null;
        List roles = new ArrayList();
        List userIds = new ArrayList();

        try {
            userList = sysUserInfoDao.getSysUserInfoPage(userFindKey, (currentPage - 1) * pageSize, pageSize);
            count = sysUserInfoDao.getSysUserCount(userFindKey);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (SysUserInfoEntity sysUserInfoEntity : userList) {
            userIds.add(sysUserInfoEntity.getUserId());
        }

        try {
            roles = sysRoleUserDao.getSysRoleUserBatchByUserId(userIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (SysUserInfoEntity sysUserInfoEntity : userList) {
            Map user = new HashMap();
            user.put("userId", sysUserInfoEntity.getUserId());
            user.put("userName", sysUserInfoEntity.getUserName());
            user.put("roles", roles);
            users.add(user);
        }

        result.put("userList", users);
        result.put("totalNum", count);
        return Result.success(result);
    }

    @Override
    public Result getUserById(String userId) {
        Map result = new HashMap<>();

        try {
            SysUserInfoEntity userInfo = sysUserInfoDao.getSysUserInfoByUserId(userId);
            result.put("userId", userInfo.getUserId());
            result.put("userName", userInfo.getUserName());
            result.put("phoneNumber", userInfo.getPhoneNumber());
            result.put("qq", userInfo.getQq());
            result.put("weixin", userInfo.getWeixin());
            result.put("email", userInfo.getEmail());
            result.put("worktileId", userInfo.getWorktileId());
            result.put("telephoneNumber", userInfo.getTelephoneNumber());
            result.put("employeeId", userInfo.getEmployeeId());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success(result);
    }

    @Override
    public Result saveUser(AccountSaveVo userInfoVo, String accountId) {
        // 保存账号信息
        SysUserInfoEntity userInfo = new SysUserInfoEntity();
        userInfo.setUserId(userInfoVo.getUserId());
        userInfo.setUserName(userInfoVo.getUserName());
        userInfo.setEmployeeId(userInfoVo.getEmployeeId());
        userInfo.setCreateTime(new Timestamp(new Date().getTime()));
        userInfo.setCreateUser(accountId);

        try {
            sysUserInfoDao.saveSysUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // 保存密码
        SysSecUserEntity secUser = new SysSecUserEntity();
        secUser.setUserId(userInfoVo.getUserId());
        secUser.setPassword(userInfoVo.getPassword());
        secUser.setErrorCnt(0);

        try {
            sysSecUserDao.saveSysSecUser(secUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // 保存账号拥有角色
        for (String roleId : userInfoVo.getRoleIds()) {
            try {
                saveSysRoleUser(userInfoVo.getUserId(), roleId, accountId);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        return Result.success();
    }

    @Override
    public Result editUser(AccountVo accountVo, String userId, String accountId) {
        List<SysRoleUserEntity> sysRoleUserList = null;
        List<String> roleIds = new ArrayList();
        Map<String, String> roleId2uuid = new HashMap();

        // 保存账号信息
        SysUserInfoEntity userInfo = new SysUserInfoEntity();
        userInfo.setUserName(accountVo.getUserName());
        userInfo.setEmployeeId(accountVo.getEmployeeId());
        userInfo.setUserId(userId);

        try {
            sysUserInfoDao.editSysUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // 保存密码
        SysSecUserEntity secUser = new SysSecUserEntity();
        secUser.setUserId(userId);
        secUser.setPassword(accountVo.getPassword());
        secUser.setErrorCnt(0);

        try {
            sysSecUserDao.editSysSecUser(secUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // 获取账号已有角色
        try {
            sysRoleUserList = sysRoleUserDao.getSysRoleUserByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (SysRoleUserEntity sysRoleUser : sysRoleUserList) {
            roleIds.add(sysRoleUser.getRoleId());
            roleId2uuid.put(sysRoleUser.getRoleId(), sysRoleUser.getUuid());
        }

        // 如果角色不存在，则新增，然后没有处理到的角色全部删除
        for (String roleId : accountVo.getRoleIds()) {
            int index = roleIds.indexOf(roleId);
            if (index == -1) {
                try {
                    saveSysRoleUser(userId, roleId, accountId);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failure();
                }
            } else {
                SysRoleUserEntity sysRoleUser = new SysRoleUserEntity();
                sysRoleUser.setUuid(roleId2uuid.get(roleId));
                sysRoleUser.setUserId(userId);
                sysRoleUser.setRoleId(roleId);

                try {
                    sysRoleUserDao.editSysRoleUser(sysRoleUser);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failure();
                }
                roleIds.remove(index);
            }
        }

        // 删除
        if (roleIds.size() > 0) {
            try {
                sysRoleUserDao.deleteSysRoleUserByRoleIds(userId, roleIds);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        return Result.success();
    }

    @Override
    public Result deleteUser(String userId) {
        try {
            sysUserInfoDao.deleteSysUserInfoByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

    @Override
    public Result checkUser(String userId) {
        SysUserInfoEntity userInfo = null;
        Map result = new HashMap();
        String tip = "";

        if (userId == null) {
            tip = "请输入账号";
            result.put("tip", tip);
        } else {
            try {
                userInfo = sysUserInfoDao.getSysUserInfoByUserId(userId);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
            if (userInfo == null) {
                tip = "已通过";
                result.put("pass", true);
                result.put("tip", tip);
            } else {
                result.put("pass", false);
                tip = "账号已注册，请重新输入";
                result.put("tip", tip);
            }
        }

        return Result.success(result);
    }

    @Override
    public Result changePassword(String userId, SecUserVo secUserVo) {
        SysSecUserEntity secUser = new SysSecUserEntity();
        secUser.setUserId(userId);
        secUser.setPassword(secUserVo.getPassword());

        try {
            sysSecUserDao.editSysSecUser(secUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }


    // 保存账号角色
    public void saveSysRoleUser(String userId, String roleId, String creator) throws Exception {
        SysRoleUserEntity sysRoleUser = new SysRoleUserEntity();
        sysRoleUser.setUuid(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        sysRoleUser.setUserId(userId);
        sysRoleUser.setRoleId(roleId);
        sysRoleUser.setCreateTime(new Timestamp(new Date().getTime()));
        sysRoleUser.setCreateUser(creator);

        sysRoleUserDao.saveSysRoleUser(sysRoleUser);
    }
}
