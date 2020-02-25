package cn.ipanel.questionnaireserver.service;

import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.vo.R;

import java.util.List;

public interface IQuestionnaireService {


    /**
     * 新建问卷（不含题目）
     *
     * @param title       标题
     * @param description 问卷描述
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return
     */
    R addQuestionnaire(String title, String description, String startTime, String endTime);

    /**
     * 新建问卷（包括题目）
     *
     * @param title
     * @param description
     * @param questionList
     * @param startTime
     * @param endTime
     * @return
     */
    R addQuestionnaire(String title, String description, List<Question> questionList, String startTime, String endTime);

    /**
     * 发布问卷
     *
     * @param questionnaireId 问卷id
     * @return
     */
    R releaseQuestionnaire(Long questionnaireId);

    /**
     * 删除问卷，包括问卷的题目，答题记录，统计
     *
     * @param questionnaireId 问卷id
     * @return
     */
    R deleteQuestionnaire(Long questionnaireId);

    /**
     * 查询问卷列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param status    状态 1:已创建未发布 2：已发布,null：查询所有状态
     * @param sortType
     * @return
     */
    R queryQuestionnaire(String startTime, String endTime, Integer status, Integer sortType);

    /**
     * 查询问卷试题
     * @param questionnaireId
     * @return
     */
    R queryQuestion(Long questionnaireId);

    /**
     * 添加问题到问卷
     * @param question
     * @param questionnaireId
     * @param order
     * @return
     */
    R addQuestionToQuestionnaire(Question question,Long questionnaireId,Integer order);

    /**
     * 批量添加题库的问题到问卷
     * @param questionIds
     * @param questionnaireId
     * @return
     */
    R addQuestionToQuestionnaire(Long[] questionIds,Long questionnaireId);
}
