package com.wisrc.zuul.services;

public interface ApiPassagewayService {
    /**
     * 根据路由地址和请求方法，校验是否有访问这个API的权限
     *
     * @param userId   用户ID
     * @param path     路由地址
     * @param methodCd 请求方法
     * @return boolean 能够访问返回true，否则返回false
     */
    boolean pass(String userId, String path, String methodCd);
}
