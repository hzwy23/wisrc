package com.wisrc.zuul.services.impl;


import com.google.gson.Gson;
import com.wisrc.zuul.dao.LoginIdentifyDao;
import com.wisrc.zuul.entity.UserLoginEntity;
import com.wisrc.zuul.services.UserIdentifyService;
import com.wisrc.zuul.vo.LoginResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * 负责向用户管理服务发起用户名和密码验证请求
 */
@Service
public class UserIdentifyServiceImpl implements UserIdentifyService {

    private final Logger logger = LoggerFactory.getLogger(UserIdentifyServiceImpl.class);

    @Autowired
    private LoginIdentifyDao loginIdentifyDao;

    @Override
    public LoginResultVO identify(UserLoginEntity ue) {
        LoginResultVO loginResultBO = new LoginResultVO();
        try {
            UserLoginEntity userLoginEntity = loginIdentifyDao.findByUserId(ue.getUsername());

            if (userLoginEntity == null) {
                loginResultBO.setCode(403);
                loginResultBO.setDetails("账号 【" + ue.getUsername() + "】不存在，请重新输入");
                loginResultBO.setUsername(ue.getUsername());
                return loginResultBO;
            }

            if (userLoginEntity.getStatusCd() != 1) {
                loginResultBO.setCode(403);
                loginResultBO.setDetails("账号 【" + ue.getUsername() + "】 已经被禁用，请联系管理员");
                loginResultBO.setUsername(ue.getUsername());
                return loginResultBO;
            }

            if (userLoginEntity.getUsername().equalsIgnoreCase(ue.getUsername()) &&
                    userLoginEntity.getPassword().equals(ue.getPassword())) {
                loginResultBO.setCode(200);
                loginResultBO.setDetails("OK");
                loginResultBO.setUsername(ue.getUsername());
            } else {
                loginResultBO.setCode(403);
                loginResultBO.setDetails("密码错误，请重新输入");
                loginResultBO.setUsername(ue.getUsername());
            }
            logger.debug("login successfully, details is:", loginResultBO.toString());
            return loginResultBO;
        } catch (Exception e) {
            logger.error("login request failed. error information is: {}. Application server response message is:{}", e.getMessage());
            loginResultBO.setCode(401);
            loginResultBO.setUsername(ue.getUsername());
            loginResultBO.setDetails("系统内部错误，请刷新后重新登录或联系管理员");
            logger.debug("login failed, details is:{}", loginResultBO.toString());
            return loginResultBO;
        }
    }

    /**
     * 通过远程请求API来校验用户名和密码的正确性
     * 在配置文件中必须设计 login.identify.type 值为api，login.identifyUrl 值为校验用户名和密码的API
     * 使用API校验用户名和密码时，将会向API发送两个参数，分别是username、password
     */
    @Override
    public LoginResultVO identifyByApi(UserLoginEntity ue) {
        logger.debug("identifyByApi user and password, username is:{}, identifyByApi api is: {}", ue.getUsername(), ue.getIdentifyUrl());
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 设置参数
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", ue.getUsername());
        params.add("password", ue.getPassword());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {

            ResponseEntity<LoginResultVO> response = restTemplate.postForEntity(ue.getIdentifyUrl(), requestEntity, LoginResultVO.class);
            LoginResultVO loginResultBO = response.getBody();
            loginResultBO.setCode(200);
            loginResultBO.setDetails("OK");
            loginResultBO.setUsername(ue.getUsername());

            logger.debug("login successfully, details is:", loginResultBO.toString());
            return loginResultBO;
        } catch (HttpClientErrorException e) {
            logger.error("login request failed. error information is: {}. Application server response message is:{}", e.getMessage(), e.getResponseBodyAsString());

            Gson gson = new Gson();
            LoginResultVO loginResultBO = gson.fromJson(e.getResponseBodyAsString(), LoginResultVO.class);

            logger.debug("login failed, details is:{}", loginResultBO.toString());
            return loginResultBO;
        }
    }
}