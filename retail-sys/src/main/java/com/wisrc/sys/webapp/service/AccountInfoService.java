package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.vo.account.AccountVO;
import com.wisrc.sys.webapp.vo.account.SetAccountVO;
import com.wisrc.sys.webapp.entity.AccountEntity;
import com.wisrc.sys.webapp.utils.Result;

import java.util.LinkedHashMap;


public interface AccountInfoService {
    /**
     * 模糊查询所有的用户账号信息
     */
    LinkedHashMap getUserInfo(int pageNum, int pageSize, AccountVO accountVO);

    Result add(AccountEntity accountEntity);

    Result update(SetAccountVO setAccountVO);

    void restrict(AccountEntity accountEntity);

    Result getUserById(String userId);

}
