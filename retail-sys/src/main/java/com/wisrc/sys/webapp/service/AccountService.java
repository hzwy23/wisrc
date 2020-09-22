package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.vo.user.AccountSaveVo;
import com.wisrc.sys.webapp.vo.user.AccountVo;
import com.wisrc.sys.webapp.vo.user.SecUserVo;
import com.wisrc.sys.webapp.utils.Result;

public interface AccountService {
    Result getUsers(String userFindKey, Integer currentPage, Integer pageSize);

    Result getUserById(String userId);

    Result saveUser(AccountSaveVo userInfoVo, String accountId);

    Result editUser(AccountVo accountVo, String userId, String accountId);

    Result deleteUser(String roleId);

    Result checkUser(String userId);

    Result changePassword(String userId, SecUserVo secUserVo);
}
