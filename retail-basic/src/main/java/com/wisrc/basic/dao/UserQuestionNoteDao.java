package com.wisrc.basic.dao;

import com.wisrc.basic.entity.UserQuestionNoteEntity;
import com.wisrc.basic.vo.UserQuestionNoteVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserQuestionNoteDao {
    /**
     * 通过反馈问题id获取当下问题下所有的备注人、备注时间、备注
     *
     * @param questionId
     * @return
     */
    @Select("select user_id,create_time,message from user_question_note where question_id=#{questionId} order by create_time Desc")
    List<UserQuestionNoteVO> findAllNote(@Param("questionId") String questionId);

    /**
     * 备注
     */
    @Insert("insert into user_question_note(uuid,question_id,user_id,create_time,message) values(#{uuid},#{questionId},#{userId},#{createTime},#{message})")
    void addQuestinNote(UserQuestionNoteEntity userQuestionNoteEntity);
}
