package com.wisrc.basic.controller;


import com.wisrc.basic.entity.UserQuestionEntity;
import com.wisrc.basic.entity.UserQuestionNoteEntity;
import com.wisrc.basic.entity.UserQuestionStarEntity;
import com.wisrc.basic.service.UserQuestionNoteService;
import com.wisrc.basic.service.UserQuestionService;
import com.wisrc.basic.service.UserQuestionStarService;
import com.wisrc.basic.utils.Result;
import com.wisrc.basic.utils.Time;
import com.wisrc.basic.utils.Toolbox;
import com.wisrc.basic.vo.AddUserQuestionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;

;


@RestController
@Api(tags = "问题反馈")
@RequestMapping(value = "/basic")
public class UserQuestionController {
    @Autowired
    private UserQuestionService userQuestionService;
    @Autowired
    private UserQuestionStarService userQuestionStarService;
    @Autowired
    private UserQuestionNoteService userquestionNoteService;


    @ApiOperation(value = "新增问题反馈信息")
    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    public Result addQuestion(@RequestBody AddUserQuestionVO addUserQuestionVO,
                              @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {

        try {
            userQuestionService.addQuestion(addUserQuestionVO, userId);
            return Result.success("添加问题反馈成功");
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @ApiOperation(value = "修改问题反馈解决状态并添加备注信息", notes = "改变解决状态,1:已解决 2;为解决")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "问题ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "解决状态", required = true, dataType = "int", paramType = "query"),

    })
    @RequestMapping(value = "/question/status", method = RequestMethod.POST)
    public Result changeStatus(@RequestParam("questionId") String questionId, @RequestParam("message") String message,
                               @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {

        try {
            UserQuestionNoteEntity questionNoteEntity = new UserQuestionNoteEntity();
            questionNoteEntity.setMessage(message);
            questionNoteEntity.setQuestionId(questionId);
            questionNoteEntity.setUserId(userId);
            questionNoteEntity.setCreateTime(Time.getCurrentDateTime());
            questionNoteEntity.setUuid(Toolbox.UUIDrandom());
            userquestionNoteService.addQuestinNote(questionNoteEntity);
            userQuestionService.changeStatusCd(questionId, 1);
            return Result.success("解决问题成功");
        } catch (Exception e) {
            return Result.failure(500, "解决失败", null);
        }
    }


    @ApiOperation(value = "点赞")
    @RequestMapping(value = "/question/like", method = RequestMethod.POST)
    public Result like(@RequestParam("questionId") String questionId, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        try {
            UserQuestionStarEntity userQuestionStarEntity = new UserQuestionStarEntity();
            userQuestionStarEntity.setUserId(userId);
            userQuestionStarEntity.setUuid(Toolbox.UUIDrandom());
            userQuestionStarEntity.setQuestionId(questionId);
            userQuestionStarService.addQusetionStar(userQuestionStarEntity);
            int num = userQuestionStarService.getStarCnt(questionId);
            userQuestionService.changeStarCnt(questionId, num);
            return Result.success(num);
        } catch (Exception e) {
            return Result.failure();
        }

    }

    @ApiOperation(value = "获取所有问题反馈信息")
    @RequestMapping(value = "/question/list", method = RequestMethod.POST)
    public Result findAllQuestion(@RequestParam(value = "pageNum", required = false) String pageNum, @RequestParam(value = "pageSize", required = false) String pageSize) {
        int size, num;
        if (pageNum != null && pageSize != null) {
            size = Integer.valueOf(pageSize);
            num = Integer.valueOf(pageNum);
            LinkedHashMap list = userQuestionService.findAllQuestion(num, size);
            return Result.success(list);
        }
        return Result.failure();
    }

    @ApiOperation(value = "获取从存储桶地址返回的内容文件")
    @RequestMapping(value = "/question/getFile", method = RequestMethod.GET)
    public Result downloadFile(@RequestParam("questionId") String questionId) {
        try {
            UserQuestionEntity userQuestionEntity = userQuestionService.findById(questionId);
            String obsName = userQuestionEntity.getObsName();
            String uuid = userQuestionEntity.getQuestionUrl();
            byte[] file = userQuestionService.downloadFile(obsName, uuid);
            return Result.success(new String(file));
        } catch (Exception e) {
            return Result.failure();
        }
    }


}
