package cn.ipanel.questionnaireserver.service;

import cn.ipanel.questionnaireserver.pojo.Question;
import cn.ipanel.questionnaireserver.vo.R;

import java.util.List;
import java.util.Map;

public interface IQuestionnaireService {


    /**
     * 新建问卷（不含题目）
     *
     * @param title       标题
     * @param description 问卷描述
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return R
     */
    R addQuestionnaire(String title, String description, String startTime, String endTime);

    /**
     * 新建问卷（包括题目）
     *
     * @param title        标题
     * @param description  问卷描述
     * @param questionList 问题集合
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return R
     */
    R addQuestionnaire(String title, String description, List<Question> questionList, String startTime, String endTime);

    /**
     * 发布问卷
     *
     * @param questionnaireId 问卷id
     * @return R
     */
    R releaseQuestionnaire(Long questionnaireId);

    /**
     * 删除问卷，包括问卷的题目，答题记录，统计
     *
     * @param questionnaireId 问卷id
     * @return R
     */
    R deleteQuestionnaire(Long questionnaireId);

    /**
     * 查询问卷列表
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param status    状态 1:已创建未发布 2：已发布,null：查询所有状态
     * @param sortType  排序类型
     * @return R
     */
    R queryQuestionnaire(String startTime, String endTime, Integer status, Integer sortType);

    /**
     * 查询问卷试题
     *
     * @param questionnaireId 问卷id
     * @return R
     */
    R queryQuestion(Long questionnaireId);

    /**
     * 添加问题到问卷
     *
     * @param question        问题
     * @param questionnaireId 问卷id
     * @param order           排序
     * @return R
     */
    R addQuestionToQuestionnaire(Question question, Long questionnaireId, Integer order);

    /**
     * 批量添加题库的问题到问卷
     *
     * @param questionIds     问题id数组
     * @param questionnaireId 问卷id
     * @return
     */
    R addQuestionToQuestionnaire(Long[] questionIds, Long questionnaireId);

    /**
     * 提交试卷
     *
     * @param questionnaireId 问卷id
     * @param answers         提交的答案
     * @return R
     */
    R submitQuestionnaire(Long questionnaireId, Map<Long, String> answers);

    /**
     * 查询问卷结果
     *
     * @param questionnaireId 问卷id
     * @return R
     */
    R queryQuestionnaireResult(Long questionnaireId);

}
