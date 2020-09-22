package com.wisrc.code.webapp.service;


import java.util.List;
import java.util.Map;

public interface UserService {
    Map getUserMap(List<String> userIdList) throws Exception;
}
