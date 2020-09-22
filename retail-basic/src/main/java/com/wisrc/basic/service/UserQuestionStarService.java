package com.wisrc.basic.service;

import com.wisrc.basic.entity.UserQuestionStarEntity;
import com.wisrc.basic.utils.Result;


public interface UserQuestionStarService {
    Result addQusetionStar(UserQuestionStarEntity userQuestionStarEntity);

    int getStarCnt(String questionId);
}
