package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.vo.authInfo.AuthInfoVO;
import com.wisrc.sys.webapp.vo.authInfo.SetAuthInfoVO;
import com.wisrc.sys.webapp.entity.SysPrivilegesInfoEntity;
import com.wisrc.sys.webapp.utils.Result;

public interface AuthInfoInfoService {

    Result add(AuthInfoVO authInfoVO);

    Result delete(String authId);

    Result update(SetAuthInfoVO authInfoVO, String userId);

    void restrict(SysPrivilegesInfoEntity entity);

    Result getPrivilegesById(String authId);

    Result getAuthInfo(Integer pageNum, Integer pageSize, AuthInfoVO authInfoVO);

    Result findAll(String privilegeTypeAttr);
}
