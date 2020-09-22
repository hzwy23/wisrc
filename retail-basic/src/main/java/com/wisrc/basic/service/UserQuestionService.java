package com.wisrc.basic.service;

import com.wisrc.basic.entity.UserQuestionEntity;
import com.wisrc.basic.vo.AddUserQuestionVO;

import java.util.LinkedHashMap;


public interface UserQuestionService {
    LinkedHashMap findAllQuestion(int pageNum, int pageSize);

    void addQuestion(AddUserQuestionVO addUserQuestionVO, String userId);

    void changeStatusCd(String questionId, int statusCd);

    void changeStarCnt(String questionId, int starCnt);

    byte[] downloadFile(String obsName, String uuid);

    UserQuestionEntity findById(String questionId);

}
