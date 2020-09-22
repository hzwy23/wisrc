//package com.wisrc.system.webapp.controller;
//
//import com.wisrc.system.webapp.service.BindWorktileService;
//import com.wisrc.system.webapp.utils.Result;
//import com.wisrc.system.webapp.vo.WorktileBindVO;
//import io.swagger.annotations.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;
//
//@Controller
//@ApiIgnore
//@RequestMapping(value = "/sys")
//public class WorktileCallbackController {
//
//    private final Logger logger = LoggerFactory.getLogger(WorktileCallbackController.class);
//
//    @Value("${worktile.basePath:}")
//    private String WORKTILE_BASEPATH;
//
//    @Value("${smartdo.erp.web.basePath:}")
//    private String ERP_WEB_BASEPATH;
//
//    @Value("${smartdo.erp.api.basePath:}")
//    private String ERP_API_BASEPATH;
//
//    @Value("${smartdo.worktile.clientId:}")
//    private String CLIENT_ID;
//
//    @Autowired
//    private BindWorktileService bindWorktileService;
//
//    @ApiOperation(value = "worktile绑定后，回调地址")
//    @RequestMapping(value = "/worktile/callback", method = RequestMethod.GET)
//    public String callback() {
//        return "index";
//    }
//
//    @RequestMapping(value = "/worktile/bind", method = RequestMethod.POST)
//    public String bind(@RequestParam("access_token") String accessToken, @RequestParam("state") String state) throws GlobalException {
//        WorktileBindVO worktileBindVO = new WorktileBindVO();
//        worktileBindVO.setAccessToken(accessToken);
//        worktileBindVO.setState(state);
//        StringBuffer sb = new StringBuffer("redirect:");
//
//        try {
//            bindWorktileService.bindWorktileId(worktileBindVO);
//        } catch (Exception e) {
//            logger.info("绑定worktile账号信息失败");
//        }
//        return sb.append(ERP_WEB_BASEPATH).append("/#/sys/user/center").toString();
//    }
//
//    @RequestMapping(value = "/worktile/unbind", method = RequestMethod.DELETE)
//    @ResponseBody
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "取消绑定更成功"),
//            @ApiResponse(code = 401, message = "权限不足"),
//            @ApiResponse(code = 500, message = "内部错误")
//    })
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-ID", value = "用户账号", required = true, dataType = "string", paramType = "header")
//    })
//    @ApiOperation(value = "取消worktile账号绑定", notes = "取消账号绑定信息",
//            response = Result.class,
//            responseContainer = "List")
//    public Result unbind(@RequestHeader("X-AUTH-ID") String userId) {
//        return bindWorktileService.unbindWorktileId(userId);
//    }
//
//    @RequestMapping(value = "/api/oauth2/authorize", method = RequestMethod.GET)
//    public String authorize(@RequestHeader("X-AUTH-ID") String userId) {
//        // 存储用户请求信息
//        Result result = bindWorktileService.requestToken(userId);
//        // 发起跳转到worktile
//        StringBuffer sb = new StringBuffer();
//        return sb.append("redirect:").append(WORKTILE_BASEPATH).append("/api/oauth2/authorize?client_id=").append(CLIENT_ID).append("&redirect_uri=").append(ERP_API_BASEPATH).append("/sys/worktile/callback&state=").append(result.getData()).append("&response_type=token&scope=mission").toString();
//    }
//}
