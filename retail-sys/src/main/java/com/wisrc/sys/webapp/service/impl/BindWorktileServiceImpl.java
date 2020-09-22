//package com.wisrc.system.webapp.service.impl;
//
//import com.wisrc.system.webapp.dao.SysUserWorktileDao;
//import com.wisrc.system.webapp.entity.SysUserInfoEntity;
//import com.wisrc.system.webapp.entity.SysUserWorktileEntity;
//import com.wisrc.system.webapp.service.BindWorktileService;
//import com.wisrc.system.webapp.service.UserInfoService;
//import com.wisrc.system.webapp.utils.Result;
//import com.wisrc.system.webapp.vo.WorktileBindVO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.LinkedHashMap;
//import java.util.UUID;
//
//
//@Service
//public class BindWorktileServiceImpl implements BindWorktileService {
//
//    private final Logger logger = LoggerFactory.getLogger(BindWorktileServiceImpl.class);
//
//    @Autowired
//    private UserInfoService userInfoService;
//
//    @Autowired
//    private SysUserWorktileDao sysUserWorktileDao;
//
//    @Value("${worktile.basePath}")
//    private String WORKTILE_BASEPATH;
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    /**
//     * 将erp系统账号与worktile系统绑定
//     * 绑定流程分别是：
//     * 1、请求授权 -- 等待确认授权，将state参数中存入erp系统账号ID
//     * 2、等待回调后，获取state值为账号id，access_token为token值
//     * 3、将获取到的token存入到账号对应的worktileId字段中
//     */
//    @Override
//    public Result bindWorktileId(WorktileBindVO worktileBindVO) {
//        // 获取worktile信息
//        StringBuffer sb = new StringBuffer(WORKTILE_BASEPATH);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<LinkedHashMap> response = restTemplate.getForEntity(sb.append("/api/scim/users/me?access_token={access_token}").toString(), LinkedHashMap.class, worktileBindVO.getAccessToken());
//        LinkedHashMap result = response.getBody();
//        String userId = redisTemplate.opsForValue().get(worktileBindVO.getState());
//        // 更新账号对应的worktile账号
//        userInfoService.bindWorktileId(result.get("id").toString(), userId);
//        // 写入worktile到库中保存
//        SysUserWorktileEntity sysUserWorktileEntity = new SysUserWorktileEntity();
//        sysUserWorktileEntity.setUsername(result.get("userName").toString());
//        sysUserWorktileEntity.setAccessToken(worktileBindVO.getAccessToken());
//        sysUserWorktileEntity.setWorktileId(result.get("id").toString());
//        sysUserWorktileDao.insert(sysUserWorktileEntity);
//
//        return null;
//    }
//
//    @Transactional
//    @Override
//    public Result unbindWorktileId(String userId) {
//
//        String worktileId = userInfoService.getWorktileId(userId);
//
//        // 删除用户绑定的worktile信息
//        SysUserInfoEntity sysUserInfoEntity = new SysUserInfoEntity();
//        sysUserInfoEntity.setUserId(userId);
//        sysUserInfoEntity.setWorktileId("-");
//        userInfoService.bindWorktileId(sysUserInfoEntity);
//
//        // 删除worktile信息
//        sysUserWorktileDao.delete(worktileId);
//
//        return Result.success();
//    }
//
//    @Override
//    public Result requestToken(String userId) {
//        String uuid = UUID.nameUUIDFromBytes((userId + String.valueOf(System.currentTimeMillis())).getBytes()).toString();
//        redisTemplate.opsForValue().set(uuid, userId);
//        return Result.success(uuid);
//    }
//
//}
