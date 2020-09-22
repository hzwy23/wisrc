package com.wisrc.replenishment.webapp.service;

import java.util.List;
import java.util.Map;

public interface ProductListService {
    Map getProductList(List<String> idlist) throws Exception;
}
