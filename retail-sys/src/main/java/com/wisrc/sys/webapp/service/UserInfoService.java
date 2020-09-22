package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.entity.SysUserInfoEntity;
import com.wisrc.sys.webapp.utils.Result;

public interface UserInfoService {
    Result getUserDetails(String userId);

    boolean bindWorktileId(SysUserInfoEntity ele);

    boolean bindWorktileId(String worktileId, String userId);

    boolean deleteUser(String userId);

    String getWorktileId(String userId);

    void updateName(String userId, String userName);

    void updatePhone(String userId, String phoneNumber);

    void updateQQ(String userId, String qq);

    void updateWeixin(String userId, String weixin);

    void updateEmail(String userId, String email);

    void updateTelephone(String userId, String telephone);

    Result getUserEmployeeRelation(String[] userIdList);
}
