package com.wisrc.basic.dao;

import com.wisrc.basic.entity.UserQuestionStarEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserQuestionStarDao {
    /**
     * 添加点赞人信息
     *
     * @param userQuestionStarEntity
     */
    @Insert("INSERT INTO user_question_star(uuid,question_id,user_id,create_time) VALUE(#{uuid},#{questionId},#{userId},#{createTime})")
    void addQusetionStar(UserQuestionStarEntity userQuestionStarEntity);

    /**
     * 获取点赞次数
     */
    @Select("select count(*) from user_question_star where question_id=#{questionId}")
    int getStarCnt(@Param("questionId") String questionId);

    /**
     * 判断点赞人是否重复
     *
     * @param userId
     * @return
     */
    @Select("select count(*) from user_question_star where user_id=#{userId} and question_id=#{questionId} ")
    int ifRepetition(@Param("userId") String userId, @Param("questionId") String questionId);

    /**
     * 判断当前用户是否点赞过
     *
     * @param userId
     * @param questionId
     * @return
     */
    @Select("select count(*) from user_question a inner join user_question_star b on a.question_id=b.question_id where a.user_id=#{userId} and a.question_id=#{questionId} and b.user_id=#{userId} and b.question_id=#{questionId} ")
    int iflike(@Param("userId") String userId, @Param("questionId") String questionId);
}
