package com.wisrc.product.webapp.service;


import java.util.List;
import java.util.Map;

public interface UserService {
    Map getUserMap(List<String> userIdList) throws Exception;
}
