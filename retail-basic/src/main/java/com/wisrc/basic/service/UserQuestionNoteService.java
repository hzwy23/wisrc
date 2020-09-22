package com.wisrc.basic.service;


import com.wisrc.basic.entity.UserQuestionNoteEntity;
import com.wisrc.basic.vo.UserQuestionNoteVO;

import java.util.List;

public interface UserQuestionNoteService {

    List<UserQuestionNoteVO> findAllNote(String questionId);

    void addQuestinNote(UserQuestionNoteEntity userQuestionNoteEntity);
}
