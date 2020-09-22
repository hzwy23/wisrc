package com.wisrc.basic.dao;

import com.wisrc.basic.entity.UserQuestionEntity;
import com.wisrc.basic.vo.UserQuestionVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface UserQuestionDao {
    /**
     * 获取问题反馈列表
     *
     * @return
     */
    @Select("select * from user_question")
    List<UserQuestionVO> findAllQuestion();

    /**
     * 添加问题反馈
     */
    @Insert("INSERT INTO user_question (question_id,create_time,user_id,obs_name,question_url,status_cd,title,star_cnt,anonymous)" +
            "VALUES(#{questionId},#{createTime},#{userId},#{obsName},#{questionUrl},#{statusCd},#{title},#{starCnt},#{anonymous})")
    void addQuestion(UserQuestionEntity userQuestionEntity);

    /**
     * 将未解决状态改为已解决
     */
    @Update("update user_question set status_cd=#{statusCd} where question_id=#{questionId}")
    void changeStatusCd(@Param("questionId") String questionId, @Param("statusCd") int statusCd);

    /**
     * 修改点赞次数
     */
    @Update("update user_question set star_cnt=#{starCnt} where question_id=#{questionId}")
    void changeStarCnt(@Param("questionId") String questionId, @Param("starCnt") int starCnt);

    /**
     * 通过问题id查询单条
     *
     * @param questionId
     * @return
     */
    @Select("select * from user_question where question_id=#{questionId}")
    UserQuestionEntity findById(@Param("questionId") String questionId);
}
