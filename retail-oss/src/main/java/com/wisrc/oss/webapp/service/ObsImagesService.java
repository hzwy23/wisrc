package com.wisrc.oss.webapp.service;


import com.wisrc.oss.webapp.utils.Result;

public interface ObsImagesService {
    /**
     * 创建一个新的存储桶
     *
     * @param obsName 存储桶名称
     */
    Result createObs(String obsName);

    /**
     * 查询遍历桶内存储的内容
     *
     * @param obsName 桶名称
     */
    Result findAll(String obsName);

    /**
     * 删除某个指定的桶
     *
     * @param obsName 桶名称
     */
    Result deleteObs(String obsName);

    /**
     * 判断某个桶是否存在
     *
     * @param obsName
     */
    boolean isExists(String obsName);

}
