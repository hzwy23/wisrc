package com.wisrc.wms.webapp.dao;

import com.wisrc.wms.webapp.vo.LogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogDao {

    /**
     * 添加日志
     *
     * @param logEntity
     */
    @Insert("INSERT INTO inner_sync_wms_log(request_url, request_time, request_method, request_params, request_body_json, request_status, response_data, response_message) " +
            "VALUES (#{requestUrl},#{requestTime},#{requestMethod},#{requestParams},#{requestBodyJson},#{requestStatus},#{responseMessage},#{responseData})")
    void saveLog(LogEntity logEntity);
}
