package com.wisrc.basic.service.impl;

import com.wisrc.basic.dao.UserQuestionStarDao;
import com.wisrc.basic.entity.UserQuestionStarEntity;
import com.wisrc.basic.service.UserQuestionStarService;
import com.wisrc.basic.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQuestionStarServiceImpl implements UserQuestionStarService {
    @Autowired
    private UserQuestionStarDao userQuestionStarDao;

    //添加点赞信息
    @Override
    public Result addQusetionStar(UserQuestionStarEntity userQuestionStarEntity) {

        String userId = userQuestionStarEntity.getUserId();
        String questionId = userQuestionStarEntity.getQuestionId();
        if (userId == null && userId.length() <= 0) {
            return Result.failure(390, "点赞失败", null);
        } else if (userId != null && userId.length() > 0) {
            int num = userQuestionStarDao.ifRepetition(userId, questionId);
            //判断点赞是是否重复
            if (num == 0) {
                userQuestionStarDao.addQusetionStar(userQuestionStarEntity);
                return Result.success();
            } else if (num == 1) {
                return Result.failure(390, "您已点赞过", null);
            } else {
                return Result.failure(390, "点赞失败", null);
            }
        } else {
            return Result.failure();
        }
    }

    //获取点赞总数量
    @Override
    public int getStarCnt(String questionId) {
        int num = userQuestionStarDao.getStarCnt(questionId);
        return num;
    }


}
