package com.wisrc.sys.webapp.service.impl;

import com.wisrc.sys.webapp.dao.SysUserInfoDao;
import com.wisrc.sys.webapp.entity.SysUserInfoEntity;
import com.wisrc.sys.webapp.service.UserInfoService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import com.wisrc.sys.webapp.vo.user.UserDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private SysUserInfoDao sysUserInfoDao;

    @Override
    public Result getUserDetails(String userId) {
        try {
            UserDetailsVO userInfo = sysUserInfoDao.findById(userId);
            if (userInfo == null || userInfo.getUserId() == null) {
                return Result.failure(ResultCode.UNKNOW_ERROR, "【" + userId + "】用户不存在");
            }
            return Result.success(userInfo);
        } catch (Exception e) {
            return Result.failure(ResultCode.UNKNOW_ERROR, "查询用户信息失败。【" + userId + "】");
        }
    }

    @Override
    public boolean bindWorktileId(SysUserInfoEntity ele) {
        sysUserInfoDao.bindWorktileId(ele);
        return true;
    }

    @Override
    public boolean bindWorktileId(String worktileId, String userId) {
        SysUserInfoEntity sysUserInfoEntity = new SysUserInfoEntity();
        sysUserInfoEntity.setWorktileId(worktileId);
        sysUserInfoEntity.setUserId(userId);
        sysUserInfoDao.bindWorktileId(sysUserInfoEntity);
        return true;
    }

    @Override
    public boolean deleteUser(String userId) {
        try {
            sysUserInfoDao.deleteSysUserInfoByUserId(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getWorktileId(String userId) {
        return sysUserInfoDao.getWorktileIdByUserId(userId);
    }

    @Override
    public void updateName(String userId, String userName) {
        sysUserInfoDao.updateName(userId, userName);
    }

    @Override
    public void updatePhone(String userId, String phoneNumber) {
        sysUserInfoDao.updatePhone(userId, phoneNumber);
    }

    @Override
    public void updateQQ(String userId, String qq) {
        sysUserInfoDao.updateQQ(userId, qq);
    }

    @Override
    public void updateWeixin(String userId, String weixin) {
        sysUserInfoDao.updateWeixin(userId, weixin);
    }

    @Override
    public void updateEmail(String userId, String email) {
        sysUserInfoDao.updateEmail(userId, email);
    }

    @Override
    public void updateTelephone(String userId, String telephone) {
        sysUserInfoDao.updateTelephone(userId, telephone);
    }

    @Override
    public Result getUserEmployeeRelation(String[] userIdList) {
        return Result.success(sysUserInfoDao.getUserEmployeeList(toArgs(userIdList)));
    }

    private String toArgs(String[] userIdList) {
        StringBuffer sb = new StringBuffer("(");
        for (String ele : userIdList) {
            sb.append("'").append(ele).append("',");
        }
        sb.setCharAt(sb.length() - 1, ')');
        if (sb.length() < 3) {
            return null;
        }
        return sb.toString();
    }

}
