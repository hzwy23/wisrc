package com.wisrc.sys.webapp.service.impl;

import com.wisrc.sys.webapp.dao.ModuleServiceDao;
import com.wisrc.sys.webapp.entity.SysResourceInfoEntity;
import com.wisrc.sys.webapp.service.ModuleService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleServiceImpl implements ModuleService {
    private final Logger logger = LoggerFactory.getLogger(ModuleServiceImpl.class);
    @Autowired
    private ModuleServiceDao moduleServiceDao;

    public static void main(String[] args) {
        String ss = "";
        System.out.println(ss);
    }

    /**
     * 通过角色编码查找哪些拥有 一级模块列表 信息
     */
    @Override
    public Result getModuleByRoleId(String roleId) {
        try {
            int menuType = 1;
            List<Map<String, Object>> moduleList = moduleServiceDao.getResourceByMenuType(menuType);
            Map<String, Object> para = new HashMap<>();
            para.put("menuType", menuType);
            para.put("roleId", roleId);
            List<SysResourceInfoEntity> list = moduleServiceDao.getModuleByRoleId(para);
            List<String> chosenList = new ArrayList<>();
            for (SysResourceInfoEntity obj : list) {
                chosenList.add(obj.getMenuId());
            }
            Map<String, Object> map = new HashMap();
            map.put("moduleList", moduleList);
            map.put("chosenList", chosenList);
            return Result.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("角色编码获取模块列表出错！", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }


}
