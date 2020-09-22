package com.wisrc.zuul.services;

import com.wisrc.zuul.entity.UserLoginEntity;
import com.wisrc.zuul.vo.LoginResultVO;

public interface UserIdentifyService {
    LoginResultVO identify(UserLoginEntity ue);

    LoginResultVO identifyByApi(UserLoginEntity ue);
}
