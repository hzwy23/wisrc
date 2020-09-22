package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.vo.ModifyPasswordVO;

public interface PasswordService {
    Result modifyPassword(ModifyPasswordVO modifyPasswordVO);
}
